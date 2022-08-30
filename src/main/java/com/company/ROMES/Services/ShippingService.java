package com.company.ROMES.Services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.build.AllowSysOut;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.DesignMaster;
import com.company.ROMES.entity.DocumentForApproaval;
import com.company.ROMES.entity.LabelPrinter;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.Lot;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.SRHistory;
import com.company.ROMES.entity.ShippingHistory;
import com.company.ROMES.entity.ShippingPlan;
import com.company.ROMES.entity.ShippingProduct;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.functions.JSONParsers;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.PrintingObject;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.ShippingServiceInterface;

import Error_code.ResultCode;
import hibernate.LotFactory;

@Service
public class ShippingService implements ShippingServiceInterface {

	@Autowired
	SessionFactory factory;

	@Override
	public JSONObject getShippingList(boolean isShipping,HttpServletRequest request) {
		Session session = null;
		JSONObject ret = new JSONObject();
//		JSONArray data = new JSONArray();
//		String url = request.getRequestURL().toString().replace(request.getRequestURI(), "")+"/ROMES";
//
//		
//		try {
//			String ip = InetAddress.getLocalHost().getHostAddress();
//			
//			session = factory.openSession();
//			List<WorkOrderInfo> historys = session.createQuery("Select this_ from WorkOrderInfo this_ where this_.isShipped=false and this_.isFinished=true order by this_.endTime desc").getResultList();
//			JSONObject ar = new JSONObject();
//			JSONArray arry = new JSONArray();
//			LocalDate tmp = null;
//			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//			for(WorkOrderInfo info : historys) {
//				JSONObject o = new JSONObject();
//				o.put("id", info.getId());
//				o.put("name", info.getPlan().getMakeProduct().getName());
//				o.put("complete", info.getEndTime().toLocalDate().format(format));
//				o.put("count", info.getPlanCount());
//				o.put("brand", info.getPlan().getMakeProduct().getBrand().getCompanyName());
//				o.put("company",info.getPlan().getMakeProduct().getBrand().getCompany().getCompanyName());
//				List<String> urls = new JSONArray();
//
//				for(DesignMaster m : info.getPlan().getMakeProduct().getSampleDesignPath()) {
//					urls.add(m.getUrl());
//				}
//				if(urls.size() == 0) {
//					urls.add("no_image.png");
//				}
//				o.put("urls", urls);
//				data.add(o);
//			}
//			ret.put("data", data);
//			ret.put("result", ResultCode.SUCCESS);
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//			ret.put("result", ResultCode.DB_CONNECT_ERROR);
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret.put("result", ResultCode.UNKNOWN_ERROR);
//		}
		// TODO Auto-generated method stub
		return ret;
	}

	private static String getServerIp() {

		InetAddress local = null;
		try {
			local = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		if (local == null) {
			return "";
		} else {
			String ip = local.getHostAddress();
			return ip;
		}

	}

	@Override
	public JSONObject completeShipping(JSONArray data, int userId,int printingCount, int printerId) {
		JSONObject ret = new JSONObject();
//		Session session = null;
//		Transaction transaction = null;
//		List<PrintingObject> printings = new ArrayList<PrintingObject>();
//		String printName;
//		try {
//			session = factory.openSession();
//			transaction = session.beginTransaction();
//			User user = session.find(User.class, userId);
//			LocalDateTime time = LocalDateTime.now();
//			LabelPrinter printer = session.find(LabelPrinter.class, printerId);
//			ShippingHistory history = new ShippingHistory();
//			for(int i = 0; i < data.size();i++) {
//				JSONObject o = JSONParsers.parseStringtoJSONObject(data.get(i).toString());
//				WorkOrderInfo info = session.find(WorkOrderInfo.class, Integer.parseInt(o.get("id").toString()));
//				history.addWorkInfo(info);
//				if(!info.isShipped()) {
//					info.setShipped(true);
//					session.update(info);
//				}
//			}
//			for(int i = 0; i < printingCount;i++) {
//				Lot lot = new Lot();
//				lot.setLot(LotFactory.getGeneratedLot(session));
//				lot.setCount(0);
//				lot.setProductId(0);
//				lot.setLocation(null);
//				session.save(lot);
//				history.addLot(lot);
//				PrintingObject print = new PrintingObject();
//				print.setBarcode(lot.getLot());
//				print.setPrintLabel(printer);
//				printings.add(print);
//			}
//			transaction.commit();
//			ret.put("result", ResultCode.SUCCESS);
//			for(PrintingObject o : printings) {
//				o.print();
//			}
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//			ret.put("result", ResultCode.NO_RESULT);
//			if (transaction != null)
//				transaction.rollback();
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret.put("result", ResultCode.UNKNOWN_ERROR);
//			if (transaction != null)
//				transaction.rollback();
//		} finally {
//			if (transaction != null)
//				if (transaction.isActive())
//					transaction.rollback();
//			if (session != null)
//				if (session.isOpen())
//					session.close();
//		}
		return ret;
	}

	@Override
	public JSONObject receiveProductData(int userId) {
		Session session = null;
		JSONObject ret = new JSONObject();
//		try {
//			session = factory.openSession();
//			User user = session.find(User.class, userId);
//			List<ConstructionPlan> plans = session.createQuery("Select this_ from ConstructionPlan this_ where this_.manager.orderInfo.isShipment="+true+ " And this.isReceiveProduct="+false + " And this_.constructionDivision.id=" + user.getDivision().getId()).getResultList();
//			JSONArray arry = new JSONArray();
//			for(ConstructionPlan plan : plans) {
//				JSONObject object = new JSONObject();
//				object.put("title", plan.getReceivedOrderInfo().getTitle());
//				object.put("id", plan.getId());
//				object.put("company", plan.getReceivedOrderInfo().getOrderCompany().getCompanyName());
//				
//				JSONArray lots = new JSONArray();
////				for(Lot lot : manager.getLots()) {
////					JSONObject o = new JSONObject();
////					o.put("lot", lot.getLot());
////					o.put("count", lot.getCount());
////					String productName;
////					ManufactureProduct p = session.find(ManufactureProduct.class, lot.getProductId());
////					if(p == null) {
////						Material Material = session.find(Material.class, lot.getProductId());
////						productName = Material.getName();
////					}else {
////						productName = p.getName();
////					}
////					o.put("product", productName);
////					lots.add(o);
////				}
//				object.put("lots", lots);
//				JSONArray urls = new JSONArray();
//				for(DesignMaster m : plan.getDesigns(session)) {
//					JSONObject o = new JSONObject();
//					o.put("url", m.getUrl());
//					urls.add(o);
//				}
//				object.put("urls", urls);
//				arry.add(object);
//			}
//			ret.put("data", arry);
//			ret.put("result",ResultCode.SUCCESS);
//			
//		}catch(NullPointerException e) {
//			ret.put("result", ResultCode.DATA_ALERT_ERROR);
//			e.printStackTrace();
//		}catch(Exception e) {
//			ret.put("result", ResultCode.UNKNOWN_ERROR);
//			e.printStackTrace();
//		}
		return ret;
	}

	@Override
	public JSONObject releaseProduct(int userId, String lot) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			System.out.println(lot);
			Lot l = (Lot) session.createQuery("Select this_ from Lot this_ where this_.lot='"+lot+"'").getSingleResult();
			User user = session.find(User.class, userId);;
			LocationMaster m = l.getLocation();
			SRHistory sr = new SRHistory();
			sr.setProductId(l.getProductId());
			sr.setLocation(m);
			sr.setCount(l.getCount());
			sr.setTime(LocalDateTime.now());
			sr.setUser(user);
			sr.setType(SRHistory.RELEASE);
			sr.addLot(l);
			session.save(sr);
			l.setLocation(null);
			l.setCount(0);
			session.update(l);
			m.removeLot(l);
			session.update(m);
			ManufactureProduct product = session.find(ManufactureProduct.class, l.getProductId());
			if(product == null) {
				Material material = session.find(Material.class, l.getProductId());
				material.setStockCount(material.getStockCount()-sr.getCount());
				session.update(material);
			}else {
				//product.setStockCount(product.getStockCount()-sr.getCount());			
				session.update(product);
			}
			LoggingTool.createLog(session, user,"Android" ,l.getLot()+" : 사용 처리");
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		}catch(NoResultException e) {
			e.printStackTrace();
			ret.put("result",ResultCode.NO_RESULT);
		}catch(Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		}finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}

	@Override
	public JSONObject releaseProduct(int userId, int lotId) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Lot l = session.find(Lot.class, lotId);
			User user = session.find(User.class, userId);;
			LocationMaster m = l.getLocation();
			SRHistory sr = new SRHistory();
			sr.setProductId(l.getProductId());
			sr.setLocation(m);
			sr.setCount(l.getCount());
			sr.setTime(LocalDateTime.now());
			sr.setUser(user);
			sr.setType(SRHistory.RELEASE);
			sr.addLot(l);
			session.save(sr);
			l.setLocation(null);
			l.setCount(0);
			session.update(l);
			m.removeLot(l);
			session.update(m);
			ManufactureProduct product = session.find(ManufactureProduct.class, l.getProductId());
			if(product == null) {
				Material material = session.find(Material.class, l.getProductId());
				material.setStockCount(material.getStockCount()-sr.getCount());
				session.update(material);
			}else {
				//product.setStockCount(product.getStockCount()-sr.getCount());			
				session.update(product);
			}
			LoggingTool.createLog(session, user,"Android" ,l.getLot()+" : 사용 처리");
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		}catch(NoResultException e) {
			e.printStackTrace();
			ret.put("result",ResultCode.NO_RESULT);
		}catch(Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		}finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}

	

}
