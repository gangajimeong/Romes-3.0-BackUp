package com.company.ROMES.Services.ProductionM;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.ConstructionLocation;
import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.DesignMaster;
import com.company.ROMES.entity.DocumentForApproaval;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.ProductionPlan;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.ShippingPlan;
import com.company.ROMES.entity.ShippingProduct;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.ProductionM.ProductionPlanServiceInterface;

@Service
public class ProductionPlanService implements ProductionPlanServiceInterface {
	@Autowired
	SessionFactory factory;

	@Autowired
	UserDAO us;

	@Override
	public JSONArray getProductionPlanList() {
		Session session = null;
		JSONArray ret = new JSONArray();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dayformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		try {
			session = factory.openSession();
			List<ProductionPlan> plans = session
					.createQuery("Select this_ from ProductionPlan this_ where this_.isFinish=" + false
							+ " order by this_.isEmergency desc")
					.getResultList();
			for (ProductionPlan plan : plans) {
				JSONObject o = new JSONObject();
				o.put("id", plan.getId());
				o.put("title", plan.getMakeProduct().getName());
				o.put("user", plan.getUser() == null ? "No Info" : plan.getUser().getName());
				o.put("brand", plan.getMakeProduct().getBrand().getCompanyName());
				o.put("company", plan.getMakeProduct().getBrand().getCompany().getCompanyName());
				try {
					ShippingPlan splan = (ShippingPlan) session
							.createQuery("Select this_ from ShippingPlan this_ where this_.plan.id=" + plan.getId())
							.getSingleResult();
					o.put("releaseDay", splan.getPlannedReleaseDate().format(dayformat));
					o.put("address", splan.getLocation().getAddress());
					o.put("direction", splan.getLocation().getTitle());
				} catch (NoResultException e) {
					o.put("releaseDay", "No Info");
					o.put("address", "No Info");
					o.put("direction", "No Info");
				}
				o.put("workingLine", plan.getLine().getLine());
				o.put("product", plan.getMakeProduct().getName());
				o.put("count", plan.getPlanCount());
				o.put("coating", plan.isCoating() ? "사용" : "미사용");
				o.put("backprint", plan.isBack() ? "예" : "아니오");
//				o.put("size", plan.getMakeProduct().getStandard());
				o.put("startTime", plan.getPredictStartTime().format(format));
				o.put("endTime", plan.getPredictEndTime().format(format));
				o.put("remark", plan.getRemark());
				o.put("isEmergency", plan.isEmergency());
				ret.add(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	@Override
	public JSONArray getUnresistedOrderInfoList() {
		Session session = null;
		JSONArray ret = new JSONArray();
//		try {
//			session = factory.openSession();
//			List<ReceivedOrderInfo> infos = session.createQuery("Select this_ from ReceivedOrderInfo this_ where this_.isConfirm=true and this_.isProduction=false and this_.isComplete=false order by this_.title").getResultList();
//			for(ReceivedOrderInfo info : infos) {
//				JSONObject o = new JSONObject();
//				o.put("id", info.getId());
//				o.put("title", info.getTitle());
//				o.put("company", info.getOrderCompany().getCompanyName());
//				List<ManufactureProduct> products = info.getProducts();
//				JSONArray arry = new JSONArray();
//				for(ManufactureProduct p : products) {
//					JSONObject pr = new JSONObject();
//					pr.put("id", p.getId());
//					pr.put("product", p.getName());
//					pr.put("count", p.getOrderCount());
////					pr.put("size", p.getStandard());
//					arry.add(pr);
//				}
//				o.put("products", arry);
//				List<DocumentForApproaval> approavals = session.createQuery("Select this_ from DocumentForApproaval this_ where this_.info.id="+info.getId()+" and this_.isContinue=true and this_.isDisabeld=false").getResultList();
//				List<String> urls = new ArrayList<>();
//				for(DocumentForApproaval a : approavals) {
//					for(DesignMaster m : a.getDesigns()) {
//						urls.add(m.getUrl());
//					}
//				}
//				o.put("urls", urls);
//				List<LatLng> locations = session.createQuery("Select this_ from LatLng this_ where this_.company.id="+info.getOrderCompany().getId()).getResultList();
//				JSONArray lo = new JSONArray();
//				for(LatLng l : locations) {
//					JSONObject los = new JSONObject();
//					los.put("id", l.getId());
//					los.put("title",l.getTitle());
//					lo.add(los);
//				}
//				o.put("locations",lo);
//				ret.add(o);
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
//		}finally {
//			SessionMethod.closeSession(session);
//		}
		return ret;
	}

	@Override
	public JSONArray getWorkingLineList() {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<WorkingLine> lines = session
					.createQuery("Select this_ from WorkingLine this_ where this_.isDisabled=false").getResultList();
			for (WorkingLine line : lines) {
				JSONObject o = new JSONObject();
				o.put("id", line.getId());
				o.put("line", line.getLine());
				ret.add(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	@Override
	public int registProductionPlan(int orderId, String userId, int latlngId, int workingLineId, int productId,
			int count, LocalDateTime startTime, LocalDateTime endTime, boolean coating, boolean backprint,
			boolean isEmergency, LocalDate releaseDay, String remark) {
		Session session = null;
		Transaction transaction = null;
		int resultCode = -1;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ReceivedOrderInfo rinfo = session.find(ReceivedOrderInfo.class, orderId);
			User user = us.selectByIdForUser(session, userId);
			LatLng location = session.find(LatLng.class, latlngId);
			WorkingLine line = session.find(WorkingLine.class, workingLineId);
			ManufactureProduct product = session.find(ManufactureProduct.class, productId);

			ProductionPlan plan = new ProductionPlan();
			plan.setBack(backprint);
			plan.setEmergency(isEmergency);
			plan.setFinish(false);
			plan.setLine(line);
			plan.setMakeProduct(product);
//			plan.setPlanCount(count);
//			plan.setOrderInfo(rinfo);
			plan.setPredictStartTime(startTime);
			plan.setPredictEndTime(endTime);
			plan.setUser(user);
			plan.setReleaseDate(releaseDay);
			plan.setDirection(location.getTitle());
			session.save(plan);

			WorkOrderInfo winfo = new WorkOrderInfo();
//			winfo.setPlan(plan);
			winfo.setRemark(remark);
			session.save(winfo);

			plan.setWorkOrder(winfo);
			session.update(plan);

			ShippingPlan splan = new ShippingPlan();
			splan.setLocation(location);
			splan.setPlannedReleaseDate(releaseDay);
			splan.setShipping(false);
			session.save(splan);

			ShippingProduct sproduct = new ShippingProduct();
			sproduct.setPlanCount(count);
			sproduct.setProduct(product);
			sproduct.setShippingPlan(splan);
			session.save(sproduct);
			splan.addShippingProduct(sproduct);
			session.update(splan);

			transaction.commit();
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return 0;
	}

	@Override
	public JSONObject selectAboutProductionPlan(int id) {
		Session session = null;
		JSONObject o = new JSONObject();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dayformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		try {
			session = factory.openSession();
			ProductionPlan plan = (ProductionPlan) session
					.createQuery("Select this_ from ProductionPlan this_ where this_.isFinish=" + false
							+ " and this_.id=" + id + " order by this_.isEmergency desc")
					.getSingleResult();

			o.put("orderInfoId", plan.getMakeProduct().getOrderInfo().getId());
			o.put("user", plan.getUser() == null ? "No Info" : plan.getUser().getName());
			o.put("brand", plan.getMakeProduct().getBrand().getCompanyName());
			o.put("company", plan.getMakeProduct().getBrand().getCompany().getCompanyName());
			try {
				ShippingPlan splan = (ShippingPlan) session
						.createQuery("Select this_ from ShippingPlan this_ where this_.plan.id=" + plan.getId())
						.getSingleResult();
				o.put("releaseDay", splan.getPlannedReleaseDate().format(dayformat));
				o.put("address", splan.getLocation().getAddress());
				o.put("direction", splan.getLocation().getId());
			} catch (NoResultException e) {
				o.put("releaseDay", "No Info");
				o.put("address", "No Info");
				o.put("direction", "No Info");
			}
			o.put("workingLine", plan.getLine().getId());
			o.put("product", plan.getMakeProduct().getId());
			o.put("count", plan.getPlanCount());
			o.put("coating", plan.isCoating() ? "사용" : "미사용");
			o.put("backprint", plan.isBack() ? "예" : "아니오");
//				o.put("size", plan.getMakeProduct().getStandard());
			o.put("startTime", plan.getPredictStartTime().format(format));
			o.put("endTime", plan.getPredictEndTime().format(format));
			o.put("remark", plan.getRemark());
			o.put("isEmergency", plan.isEmergency());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return o;
	}

	@Override
	public boolean updateProductionPlan() {
		Session session = null;
		Transaction transaction = null;
		int resultCode = -1;
		try {

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return false;
	}

	@Override
	public JSONArray getWorkOrder() {
		Session session = null;
		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();

			List<WorkOrderInfo> list = new ArrayList<WorkOrderInfo>();
			list = session.createQuery("Select this_ from WorkOrderInfo this_ where this_.isFinished = " + false
					+ " order by isEmergency desc, starttime").getResultList();

			for (WorkOrderInfo info : list) {
				JSONObject ob = new JSONObject();
				ob.put("id", info.getId());
				ob.put("makecount", info.getMakeCount());
				ob.put("branch", info.getBranch() == null? "없음": info.getBranch().getTitle());
				ob.put("plancount", info.getPlanCount());
				ob.put("size", info.getSize());
				ob.put("coating", info.isCoating() ? "사용" : "미사용");
				ob.put("isback", info.isBack() ? "사용" : "미사용");
				ob.put("emergency", info.isEmergency());
				ob.put("product", info.getProduct());
				ob.put("title", info.getOrderInfo().getTitle());
				ob.put("brand", info.getBranch() == null? "정보 없음":
						info.getBranch().getBrand() == null ? "정보 없음" : info.getBranch().getBrand().getCompanyName());
				ob.put("startTime", info.getStartTime());
				ob.put("remark", info.getRemark());
				ob.put("workingLine", info.getPrinter() == null ? 0 : info.getPrinter().getId());
				ob.put("company", info.getOrderInfo().getOrderCompany() == null ? "-"
						: info.getOrderInfo().getOrderCompany().getCompanyName());
				ob.put("url", info.getDesign() == null ? "/no_image.png"
						: info.getDesign().getUrl() == null ? "/no_image.png" : info.getDesign().getUrl());
				ob.put("user",
						info.getOrderInfo().getUser() == null ? "no info" : info.getOrderInfo().getUser().getName());
				rets.add(ob);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rets;
	}

	@Override
	public boolean switchEmergency(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			WorkOrderInfo info = session.find(WorkOrderInfo.class, id);
			info.setEmergency(!info.isEmergency());

			session.update(info);
			transaction.commit();

			state = true;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return state;
	}

	@Override
	public boolean selectPrinter(int id, int printer) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			WorkOrderInfo info = session.find(WorkOrderInfo.class, id);
			WorkingLine line = session.find(WorkingLine.class, printer);
			info.setPrinter(line);

			session.update(info);
			transaction.commit();

			state = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}

	@Override
	public boolean completeProduction(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}

			ConstructionPlan plan = new ConstructionPlan();
			ConstructionLocation location = new ConstructionLocation();
			List<ConstructionLocation> locate = new ArrayList<ConstructionLocation>();
			WorkOrderInfo info = session.find(WorkOrderInfo.class, id);
			
			if(info.getPrinter() == null) {
				return false;
			}
			
			info.setEndTime(LocalDateTime.now());
			info.setFinished(true);
			info.setManager(user);
			info.setMakeCount(info.getPlanCount());

			

			if (info.isConstruction() == true) {

				// 시공지시 생성
				plan.setPlanDate(LocalDateTime.now());
				plan.setCompany(info.getBranch().getCompany());
				plan.setWorkOrderInfo(info);

				// 로케이션 생성
				location.setLocation(plan.getWorkOrderInfo().getBranch());
				location.setPlan(plan);

				session.save(location);
				session.save(plan);

				locate = session.createQuery(
						"Select this_ from ConstructionLocation this_ where this_.id = " + location.getId() + "")
						.getResultList();
				plan.setLocations(locate);
				session.update(plan);
			}

			LoggingTool.createLog(session, user, "Manager", "작업완료 :" + info.getOrderInfo().getTitle() + "/" + info.getProduct());
			session.update(info);
			transaction.commit();
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;

	}
	
	
}
