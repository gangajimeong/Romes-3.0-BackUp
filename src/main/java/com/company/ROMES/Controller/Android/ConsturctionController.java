package com.company.ROMES.Controller.Android;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.build.AllowSysOut;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.ReceviedOrderHistoryService;
import com.company.ROMES.entity.ConstructionLocation;
import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.ConstructionResult;
import com.company.ROMES.entity.DesignMaster;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.SessionMethod;
import com.google.gson.JsonArray;

import Error_code.ResultCode;

@Controller
public class ConsturctionController {

	@Autowired
	SessionFactory factory;

	@Autowired
	ReceviedOrderHistoryService roService;

	@RequestMapping(value = "/Android/Construction/mapData", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getConstructionInfo(@RequestParam(name = "userid") int userId,
			@RequestParam(name = "receivedproduct") boolean isReceviedProduct) {
		JSONObject ret = new JSONObject();
		Session session = null;
		try {
			session = factory.openSession();
			List<ConstructionPlan> list = roService.ConstructionPlanList(session, userId, isReceviedProduct);
			System.out.println(list.size());
			JSONArray arry = new JSONArray();
			for (ConstructionPlan plan : list) {
				JSONArray designs = new JSONArray();
				try {
					List<DesignMaster> masters = plan.getWorkOrderInfo().getSampleDesigns();
					for (DesignMaster m : masters) {
						JSONObject object = new JSONObject();
						object.put("url", m.getUrl());
						designs.add(object);
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				for (ConstructionLocation l : plan.getLocations()) {
					if (!l.isComplete()) {
						JSONObject o = new JSONObject();
						LatLng lat = l.getLocation();
						o.put("title", plan.getWorkOrderInfo().getOrderInfo().getTitle());
						o.put("branch", lat.getTitle());
						o.put("id", l.getId());
						o.put("address", lat.getAddress());
						o.put("lat", lat.getLat());
						o.put("lng", lat.getLng());
						JSONArray products = new JSONArray();
						o.put("urls", designs);
						arry.add(o);
					}
				}
				// TODO : 출하 Lot 추가할것
			}
			ret.put("result", Error_code.ResultCode.SUCCESS);
			ret.put("data", arry);

		} catch (Exception e) {
			ret.put("result", Error_code.ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		System.out.println(ret);
		return ret.toJSONString();
	}

	@RequestMapping(value = "/Android/ConstructionList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getConstructionList(@RequestParam(name = "userid") int userId) {
		JSONObject ret = new JSONObject();
		Session session = null;
		JSONArray history = new JSONArray();
		try {
			session = factory.openSession();
			User user = session.find(User.class, userId);
			JSONArray arry = new JSONArray();
//			List<ConstructionPlan> plans = session.createQuery("Select distinct this_ from ConstructionPlan this_ join this_.locations l where l.location.company.id="+user.getConstructionCompany().getId()+" and this_.isComplete="+false).getResultList();
			List<ConstructionPlan> plans = session.createQuery("Select this_ from ConstructionPlan this_ where this_.isComplete = false and this_.isConfirm = true and this_.company.id ="+user.getConstructionCompany().getId()).getResultList();
//			List<ManufactureProduct> products = session.createQuery(
//					"Select this_ from ManufactureProduct this_ where this_.isComplete= false and this_.constructionTeam.id="
//							+ user.getDivision().getId()+" and this_.plan.workOrder.isShipped=true order by this_.id")
//					.getResultList();
			for(ConstructionPlan plan : plans) {
				JSONObject ob = new JSONObject();
				ob.put("id", plan.getId());
				ob.put("title",plan.getWorkOrderInfo().getProduct());
				ob.put("brand", plan.getWorkOrderInfo().getOrderInfo().getBrand().getCompanyName());
				
				
//				List<String> samples = new ArrayList<String>();
//				for(DesignMaster m : plan.getWorkOrderInfo().getSampleDesigns()) {
//					samples.add(m.getUrl());
//				}
//				if(samples.size() == 0)
//					samples.add("no_image.png");
//				ob.put("smaples", samples);
				
				ob.put("date", plan.getPlanDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				
				
//				List<String> sample = new ArrayList<String>();
//				for (DesignMaster s : plan.getWorkOrderInfo().getSampleDesigns()) {
//					sample.add(s.getUrl());
//				}
//				if(sample.size() == 0) {
//					sample.add("/no_image.png");
//				}
//				ob.put("samples",sample);
				
				List<String> sample = new ArrayList<String>();
				sample.add(plan.getWorkOrderInfo().getDesign()==null?"/no_image.png":plan.getWorkOrderInfo().getDesign().getUrl());
				ob.put("samples", sample);
				
				
				JSONArray locations = new JSONArray();
				for(ConstructionLocation location:plan.getLocations()) {
					if(plan.getCompany().getId() != user.getConstructionCompany().getId())
						continue;
					JSONObject j = new JSONObject();
					j.put("planid",plan.getId());
					j.put("brand", ob.get("brand").toString());
					j.put("brand_id", location.getLocation().getBrand().getId());
					j.put("branch", location.getLocation()==null?"정보 없음":location.getLocation().getTitle());
					j.put("branch_id", location.getLocation()==null?0:location.getLocation().getId());
					j.put("lat", location.getLocation()== null?0:location.getLocation().getLat());
					j.put("lng", location.getLocation()== null?0:location.getLocation().getLng());
					j.put("isNull", location.getLocation().getAddress()==null);
					j.put("isConstruction", location.isComplete());
					j.put("id", location.getId());
					j.put("address", location.getLocation()== null?"정보 없음":location.getLocation().getAddress());
					j.put("detail", location.getLocation()== null?"정보 없음":location.getLocation().getDetail());
					j.put("sample", sample);
					List<String> urls = new ArrayList<String>();
					try {
						ConstructionResult result = (ConstructionResult) session
								.createQuery("Select this_ from ConstructionResult this_ where this_.location.id="
										+ location.getId())
								.getResultList().get(0);
						
						for (String s : result.getResultImageUrls()) {
							urls.add(s);
						}
					} catch (IndexOutOfBoundsException e) {

					}
					j.put("urls", urls);
					locations.add(j);
				}
				ob.put("locations",locations);
				arry.add(ob);
			}

			ret.put("result", Error_code.ResultCode.SUCCESS);
			ret.put("data", arry);
		} catch (Exception e) {
			ret.put("result", Error_code.ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		System.out.println(ret);
		return ret.toJSONString();
	}

	@RequestMapping(value = "/Android/BranchList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getBranchList(@RequestParam(name = "brandid") int brandId) {
		Session session = null;
		JSONObject o = new JSONObject();
		try {
			session = factory.openSession();
//			ManufactureProduct p = session.find(ManufactureProduct.class, manufactureId);
			
			List<LatLng> list = session.createQuery("Select this_ from LatLng this_ where this_.brand.id="
					+ brandId + "order by this_.title").getResultList();
			JSONArray arry = new JSONArray();
			for (LatLng l : list) {
				JSONObject ob = new JSONObject();
				ob.put("id", l.getId());
				ob.put("title", l.getTitle());
				ob.put("address", l.getAddress());
				ob.put("detail", l.getDetail());
				arry.add(ob);
			}
			o.put("data", arry);
			o.put("result", ResultCode.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			o.put("result", ResultCode.UNKNOWN_ERROR);
		} finally {
			SessionMethod.closeSession(session);
		}
		System.out.println(o);
		return o.toJSONString();
	}

	@RequestMapping(value = "/Android/Construction/receiveData", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getReceiveDataList(@RequestParam(name = "userid") int userId) {
		JSONObject o = roService.getReceiveProductData(userId);
		System.out.println(o);
		return o.toJSONString();
	}

	@RequestMapping(value = "/Android/Construction/receiveProduct", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String ReceiveProduct(@RequestParam(name = "userid") int userId, @RequestParam(name = "planid") int planId) {
		JSONObject o = roService.receiveProduct(userId, planId);
		System.out.println(o);
		return o.toJSONString();
	}

	@RequestMapping(value = "/Android/Construction/history", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getConstructionHistorys(@RequestParam(name = "userid") int userId,
			@RequestParam(name = "year") int year, @RequestParam(name = "month") int month) {
		JSONObject o = roService.getCompleteConstructionResult(userId, year, month);
//		System.out.println(o);
		return o.toJSONString();
	}

	@RequestMapping(value = "/Android/Construction/dashboard", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getConstructionDashboard(@RequestParam(name = "userid") int userId) {
		JSONObject o = roService.getDashboardData(userId);
		System.out.println(o);
		return o.toJSONString();
	}
}
