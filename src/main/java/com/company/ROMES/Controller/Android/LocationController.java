package com.company.ROMES.Controller.Android;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.LocationService;
import com.company.ROMES.Services.LotService;
import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.entity.LabelPrinter;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.Lot;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.functions.SessionMethod;

import Error_code.ResultCode;

@Controller
public class LocationController {
	@Autowired
	LocationService locationService;

	@Autowired
	LotService lotService;

	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "/Android/Location/LocationData", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String LocationTreeData() {
		JSONObject ret = new JSONObject();
		JSONArray data = new JSONArray();
		Session session = null;
		try {
			session = factory.openSession();
			for(int i = 0; i <locationService.getTopLocation(session).size(); i ++) {
				LocationMaster top = locationService.getTopLocation(session).get(i);
			if (top == null) {
				ret.put("result", ResultCode.NO_RESULT);
			} else {
				data.add(makeTreeData(top, session));
				ret.put("data", data);
				ret.put("result", ResultCode.SUCCESS);
			}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			ret.put("result", ResultCode.DB_CONNECT_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			// TODO: handle exception
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret.toJSONString();
	}

	@RequestMapping(value = "/Android/updatePrinterInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String updatePrinterInfo(@RequestParam(name="printerid")int printerid,@RequestParam(name="margin_w")int margin_w,@RequestParam(name="margin_h")int margin_h,@RequestParam(name="fontsize")int fontsize) {
		JSONObject ret =new JSONObject();
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			LabelPrinter printer = session.find(LabelPrinter.class, printerid);
			printer.setFontSize(fontsize);
			printer.setMargin_h(margin_h);
			printer.setMargin_w(margin_w);
			session.update(printer);
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		}finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret.toJSONString();
	}
	
	
	@RequestMapping(value = "/Android/CommonData", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getCommonData() {
		JSONObject ret = new JSONObject();
		Session session = null;
		try {
			session = factory.openSession();
			List<ManufactureProduct> manus = session
					.createQuery(
							"Select this_ from ManufactureProduct this_ where this_.isDisable=false order by this_.id")
					.getResultList();
			List<Material> materials = session
					.createQuery("Select this_ from Material this_ where this_.isDisable=false order by this_.id")
					.getResultList();
			List<ErrorCode> codes = session.createQuery("Select this_ from ErrorCode this_ order by this_.id")
					.getResultList();
			List<LocationMaster> locations = session
					.createQuery("Select this_ from LocationMaster this_ where this_.disabeld=false").getResultList();
			List<LabelPrinter> printers = session.createQuery("Select this_ from LabelPrinter this_ order by this_.id").getResultList();
			JSONArray pri = new JSONArray();
			JSONObject none = new JSONObject();
			none.put("id", 0);
			none.put("name", "프린터 선택");
			none.put("margin_w", 0);
			none.put("margin_h",0);
			none.put("fontsize", 0);
			pri.add(none);
			for(LabelPrinter p : printers) {
				JSONObject o = new JSONObject();
				o.put("id", p.getId());
				o.put("name", p.getPrinterName());
				o.put("margin_w", p.getMargin_w());
				o.put("margin_h",p.getMargin_h());
				o.put("fontsize", p.getFontSize());
				pri.add(o);
			}
			JSONArray products = new JSONArray();
			JSONArray Mater = new JSONArray();
			JSONArray errors = new JSONArray();
			JSONArray loca = new JSONArray();
			for (ManufactureProduct p : manus) {
				JSONObject o = new JSONObject();
				o.put("id", p.getId());
				o.put("name", p.getName());
				products.add(o);
			}
			for (Material p : materials) {
				JSONObject b = new JSONObject();
				b.put("id", p.getId());
				b.put("name", p.getName());
				Mater.add(b);
			}
			for(ErrorCode c : codes) {
				JSONObject j = new JSONObject();
				j.put("id", c.getId());
				j.put("type", c.getErrorType());
				j.put("code", c.getErrorCode());
				errors.add(j);
			}
			for(LocationMaster m : locations) {
				JSONObject t = new JSONObject();
				t.put("id",m.getId());
				t.put("name", m.getName());
				loca.add(t);
			}
			ret.put("printers", pri);
			ret.put("manufacture",products);
			ret.put("material", Mater);
			ret.put("error", errors);
			ret.put("location", loca);
			ret.put("result", ResultCode.SUCCESS);
		} catch (NullPointerException e) {
			e.printStackTrace();
			ret.put("result", ResultCode.DB_CONNECT_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			// TODO: handle exception
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret.toJSONString();
	}

	public JSONObject makeTreeData(LocationMaster m, Session session) {
		JSONObject o = new JSONObject();
		JSONArray arry = new JSONArray();
		o.put("id", m.getId());
		o.put("name", m.getName());
		List<LocationMaster> set = m.getSubLocationsAsList();
		for (LocationMaster s : set) {
			JSONObject ob = makeTreeData(s, session);
			arry.add(ob);
		}
		List<Lot> lots = m.getLots();
		JSONArray names = new JSONArray();
		for (Lot lot : lots) {
			if (lot.getCount() < 1)
				continue;
			JSONObject name = new JSONObject();
			name.put("id", lot.getId());
			name.put("product", lotService.getProductName(session, lot));
			name.put("count", lot.getCount());
			name.put("lot", lot.getLot());
			names.add(name);
		}
		if (arry.size() != 0)
			o.put("children", arry);
		o.put("products", names);
		return o;
	}
}
