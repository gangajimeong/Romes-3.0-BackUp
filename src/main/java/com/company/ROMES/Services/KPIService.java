package com.company.ROMES.Services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.xmlbeans.impl.values.XmlDurationImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.entity.WorkOrderChangeHistory;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.functions.SessionMethod;

@Service
public class KPIService implements com.company.ROMES.interfaces.service.KPIServiceInterface {
	@Autowired
	SessionFactory factory;

	@Override
	public JSONObject getCapaData(String preTime, String lastTime, int lineid) {
		JSONObject object = new JSONObject();
		Session session = null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			session = factory.openSession();
			LocalDateTime preDate = LocalDate.parse(preTime, format).atTime(0, 0, 0);
			LocalDateTime lastDate = LocalDate.parse(lastTime, format).atTime(23, 59, 59);
			String query = "Select this_ from WorkOrderInfo this_ where this_.isFinished=true and (this_.endTime between :start and :end ) ";
			System.out.println(lineid);
			if (lineid != 0) {
				query = query + "and this_.printer.id=" + lineid + " order by this_.endTime";
			} else {
				query = query + "order by this_.endTime";
			}
			List<WorkOrderInfo> infos = session.createQuery(query).setParameter("start", preDate)
					.setParameter("end", lastDate).getResultList();

			List<String> designers = new ArrayList<>();
			JSONArray chartDate = new JSONArray();
			JSONArray workInfo = new JSONArray();
			
			LinkedHashMap<String, JSONObject> map = new LinkedHashMap<>();
			for (WorkOrderInfo info : infos) {
				String key = info.getEndTime().getYear() + "-"
						+ String.format("%02d", info.getEndTime().getMonthValue());
				try {
//					String designer = info.getDesigner() == null?"정보 없음":info.getDesigner().getName();
					String designer = info.getManager() == null?"정보 없음":info.getManager().getName();
					if (!designers.contains(designer))
						designers.add(designer);
					JSONObject o = new JSONObject();
					o.put("id", info.getId());
					o.put("title", info.getOrderInfo().getTitle());
					o.put("designer", designer);
					o.put("brand", info.getOrderInfo().getBrand().getCompanyName());
					o.put("branch", info.getBranch().getTitle());
					o.put("product", info.getProduct());
					o.put("time", info.getEndTime().toLocalDate().format(format));
					workInfo.add(o);
					if (map.containsKey(key)) {
						JSONObject ob = map.get(key);
						if (ob.containsKey(info.getManager().getName())) {
							ob.put(designer, Integer.parseInt(ob.get(designer).toString()) + 1);
						} else {
							ob.put(designer, 1);
						}
						ob.put("total", Integer.parseInt(ob.get("total").toString()) + 1);
						map.put(key, ob);
					} else {
						JSONObject ob = new JSONObject();
						ob.put(designer, 1);
						ob.put("total", 1);
						map.put(key, ob);
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
					continue;
				}
			}
			Iterator<String> keys = map.keySet().iterator();
			while (keys.hasNext()) {
				String date = keys.next();
				JSONObject o = map.get(date);
				JSONObject d = new JSONObject();
				d.put("year", date.substring(0, date.indexOf("-")));
				d.put("month", date.substring(date.indexOf("-") + 1));
				o.put("date", d);
				for (String name : designers) {
					if (!o.containsKey(name))
						o.put(name, 0);
				}
				chartDate.add(o);
			}
			object.put("designers", designers);
			object.put("chartRows", chartDate);
			object.put("data", workInfo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return object;
	}

	@Override
	public JSONObject getKPIData(String preTime, String lastTime, int lineId) {
		JSONObject object = new JSONObject();
		Session session = null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			session = factory.openSession();
			LocalDateTime preDate = LocalDate.parse(preTime, format).atTime(0, 0, 0);
			LocalDateTime lastDate = LocalDate.parse(lastTime, format).atTime(23, 59, 59);
			String query = "Select this_ from WorkOrderInfo this_ where this_.isFinished=true and (this_.endTime between :start and :end ) ";
			if (lineId != 0) {
				query = query + "and this_.printer.id=" + lineId + " order by this_.endTime";
			} else {
				query = query + "order by this_.endTime";
			}
			List<WorkOrderInfo> infos = session.createQuery(query).setParameter("start", preDate)
					.setParameter("end", lastDate).getResultList();
			LinkedHashMap<String, JSONObject> errorMap = new LinkedHashMap<>();
			LinkedHashMap<String, JSONObject> printerMap = new LinkedHashMap<>();
			List<String> lines = new ArrayList<>();
			List<String> codes = new ArrayList<>();
			JSONArray errorData = new JSONArray();
			JSONArray printerData = new JSONArray();
			JSONArray data = new JSONArray();
			int totalCount = infos.size();
			int errorCount = 0;
			double everageTotal = 0;
			for (WorkOrderInfo info : infos) {
				try {
					String key = info.getEndTime().getYear() + "-"
							+ String.format("%02d", info.getEndTime().getMonthValue());
					JSONObject o = new JSONObject();
					if (!lines.contains(info.getPrinter().getLine()))
						lines.add(info.getPrinter().getLine());
					List<WorkOrderChangeHistory> historys = session.createQuery(
							"Select this_ from WorkOrderChangeHistory this_ where this_.workInfo.id=" + info.getId())
							.getResultList();
					totalCount = totalCount + historys.size();
					errorCount = errorCount + historys.size();
					Duration lead = Duration.between(info.getOrderInfo().getReceivedOrderDate(), info.getEndTime());
					double leadTime = (double)lead.toDays() + ((double)lead.toHoursPart() / 24);
					everageTotal = everageTotal+leadTime;
					o.put("id", info.getId());
					o.put("title", info.getOrderInfo().getTitle());
					o.put("brand", info.getOrderInfo().getBrand().getCompanyName());
					o.put("branch", info.getBranch().getTitle());
					o.put("product", info.getProduct());
					o.put("printer", info.getPrinter().getLine());
					o.put("errors", historys.size());
					o.put("completeDate", info.getEndTime().format(format));
					o.put("orderDate", info.getOrderInfo().getReceivedOrderDate().format(format));
					o.put("lead", String.format("%.2f", leadTime));
					data.add(o);
					if (printerMap.containsKey(key)) {
						JSONObject ob = printerMap.get(key);
						if (ob.containsKey(info.getPrinter().getLine())) {
							int preData = Integer.parseInt(ob.get(info.getPrinter().getLine()).toString());
							ob.put(info.getPrinter().getLine(), preData + historys.size());
						} else {
							ob.put(info.getPrinter().getLine(), historys.size());
						}
						ob.put("total", Integer.parseInt(ob.get("total").toString()) + historys.size());
						printerMap.put(key, ob);
					} else {
						JSONObject ob = new JSONObject();
						ob.put(info.getPrinter().getLine(), historys.size());
						ob.put("total", historys.size());
						printerMap.put(key, ob);
					}
					for (WorkOrderChangeHistory history : historys) {
						if (!codes.contains(history.getError().getErrorCode()))
							codes.add(history.getError().getErrorCode());
						if (errorMap.containsKey(key)) {
							JSONObject ob = errorMap.get(key);
							if (ob.containsKey(history.getError().getErrorCode())) {
								int preData = Integer.parseInt(ob.get(history.getError().getErrorCode()).toString());
								ob.put(history.getError().getErrorCode(), preData + 1);
							} else {
								ob.put(history.getError().getErrorCode(), 1);
							}
							ob.put("total", Integer.parseInt(ob.get("total").toString()) + 1);
							errorMap.put(key, ob);
						} else {
							JSONObject ob = new JSONObject();
							ob.put(history.getError().getErrorCode(), 1);
							ob.put("total",1);
							errorMap.put(key, ob);
						}
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
			Iterator<String> keys = errorMap.keySet().iterator();
			while (keys.hasNext()) {
				String date = keys.next();
				JSONObject ob = errorMap.get(date);
				JSONObject d = new JSONObject();
				d.put("year", date.substring(0, date.indexOf("-")));
				d.put("month", date.substring(date.indexOf("-") + 1));
				ob.put("date", d);
				for (String code : codes) {
					if (!ob.containsKey(code))
						ob.put(code, 0);
				}
				errorData.add(ob);
			}
			keys = printerMap.keySet().iterator();
			while (keys.hasNext()) {
				String date = keys.next();
				JSONObject ob = printerMap.get(date);
				JSONObject d = new JSONObject();
				d.put("year", date.substring(0, date.indexOf("-")));
				d.put("month", date.substring(date.indexOf("-") + 1));
				ob.put("date", d);
				for (String printer : lines) {
					if (!ob.containsKey(printer))
						ob.put(printer, 0);
				}
				printerData.add(ob);
			}
			object.put("printerData", printerData);
			object.put("errorData", errorData);
			object.put("data", data);
			object.put("printers", lines);
			object.put("errors", codes);
			object.put("totalCount", totalCount);
			object.put("errorCount", errorCount);
			object.put("average", String.format("%.2f", ((double)errorCount / (double)totalCount) * 100));
			object.put("averageLead", String.format("%.2f", (everageTotal/infos.size())));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return object;
	}

}
