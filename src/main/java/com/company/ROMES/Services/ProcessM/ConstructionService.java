package com.company.ROMES.Services.ProcessM;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.entity.ConstructionLocation;
import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.ConstructionResult;
import com.company.ROMES.entity.DesignMaster;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.JSONParsers;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.service.ProcessM.ConstructionServiceInterface;

@Service
public class ConstructionService implements ConstructionServiceInterface {
	@Autowired
	SessionFactory factory;

	@Autowired
	UserDAO us;
	
	@Override
	public JSONArray getConstructionResult(LocalDate date1, LocalDate date2, int brandId, int constructCompanyId) {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ConstructionResult> query = builder.createQuery(ConstructionResult.class);
			Root<ConstructionResult> root = query.from(ConstructionResult.class);
			List<Predicate> wheres = new ArrayList<>();
			query.select(root);
			if (date1 != null && date2 != null) {
				wheres.add(builder.between(root.get("time"), date1, date2));
			}
			if (brandId != 0) {
				wheres.add(builder.equal(root.get("location.plan.workOrderInfo.orderInfo.brand.id"), brandId));
			}
			if (constructCompanyId != 0) {
				wheres.add(builder.equal(root.get("location.plan.company.id"), constructCompanyId));
			}
			Predicate[] arr = wheres.toArray(new Predicate[wheres.size()]);
			query.where(arr);
			query.orderBy(builder.desc(root.get("time")));
			List<ConstructionResult> results = session.createQuery(query).getResultList();
			for (ConstructionResult result : results) {
				JSONObject object = new JSONObject();
				object.put("id", result.getId());
				object.put("title", result.getLocation().getPlan().getWorkOrderInfo().getOrderInfo().getTitle());
				object.put("team", result.getUser().getDivision().getDivision());
				object.put("company", result.getLocation().getPlan().getWorkOrderInfo().getOrderInfo().getBrand()
						.getCompany().getCompanyName());
				object.put("brand",
						result.getLocation().getPlan().getWorkOrderInfo().getOrderInfo().getBrand().getCompanyName());
				object.put("location", result.getLocation().getLocation().getTitle());
				object.put("address", result.getLocation().getLocation().getAddress());
				object.put("time", result.getTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));
				object.put("user", result.getUser().getName());
				List<String> imgs = new ArrayList<>();
				for (String path : result.getResultImageUrls()) {
					imgs.add(path);
				}
				object.put("resultImg", imgs);
				ret.add(object);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	@Override
	public JSONArray getConstructionPlan() {
		Session session = null;
		JSONArray ret = new JSONArray();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			session = factory.openSession();
//			List<ReceivedOrderInfo> infos = session.createQuery(
//					"Select this_ from ReceivedOrderInfo this_ where this_.isShipment=true order by this_.receivedOrderDate desc")
//					.getResultList();
			// "Select this_ from ConstructionPlan this_, in (this_.locations) t where
			// this_.isComplete= true or t.isComplete= true order by this_.planDate desc"
			List<ConstructionPlan> plans = session.createQuery(
					"Select this_ from ConstructionPlan this_, in (this_.locations) t where this_.isComplete= true or t.isComplete= true order by this_.planDate desc")
					.getResultList();

			for (ConstructionPlan info : plans) {
				JSONObject object = new JSONObject();
				object.put("id", info.getId());
				object.put("title", info.getWorkOrderInfo().getOrderInfo().getTitle());
				object.put("branch", info.getWorkOrderInfo().getBranch().getTitle());
				object.put("date", info.getPlanDate() == null ? "정보없음" : info.getPlanDate().format(format));
				object.put("company", info.getWorkOrderInfo().getBranch().getTitle());
				object.put("brand", info.getWorkOrderInfo().getOrderInfo().getBrand().getCompanyName());
				object.put("user", info.getUser() == null ? "정보없음" : info.getUser().getName());
				object.put("state", info.isComplete() ? "완료" : "시공진행중");
				object.put("product", info.getWorkOrderInfo().getProduct());
				String brand = object.get("brand").toString();
				String company = object.get("company").toString();
				List<ConstructionLocation> locations = info.getLocations();
				JSONArray arry = new JSONArray();
				if (locations.size() == 0)
					continue;
				for (ConstructionLocation location : locations) {
					JSONObject i = new JSONObject();
					List<String> urls = new ArrayList<>();
					if (location.isComplete()) {
						try {
							ConstructionResult result = (ConstructionResult) session
									.createQuery("Select this_ from ConstructionResult this_ where this_.location.id="
											+ location.getId())
									.getResultList().get(0);
							i.put("id", result.getId());
							i.put("company", result.getLocation().getPlan().getCompany()== null?"정보없음":result.getLocation().getPlan().getCompany().getCompanyName());
							i.put("location", result.getLocation().getLocation().getTitle());
							i.put("brand", brand);
							i.put("branch",result.getLocation().getLocation().getTitle());
							i.put("user", result.getUser().getName());
							i.put("date", result.getTime().format(time));
							for (String u : result.getResultImageUrls()) {
								urls.add(u);
							}
						} catch (IndexOutOfBoundsException e) {
							e.printStackTrace();
							System.out.println("시공 데이터 오류 : 완료처리는 되어있는데 결과 데이터 없음 - " + location.getId());
						}
					} else {
						i.put("id", 0);
						i.put("company", company);
						i.put("brand", brand);
						i.put("location", "-");
						i.put("user", "시공 미완료");
						i.put("date", "-");
						urls.add("noimage.png");
					}
					i.put("url", urls);
					arry.add(i);
				}
				object.put("result", arry);
				ret.add(object);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

//	@Override
//	public JSONArray getConstructionRes() {
//		Session session = null;
//		JSONArray ret = new JSONArray();
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		try {
//			session = factory.openSession();
//			List<ConstructionPlan> plans = session.createQuery("Select this_ from ConstructionPlan this_, in (this_.locations) t where this_.isComplete= true or t.isComplete= true order by this_.planDate desc").getResultList();
//					
//			for (ConstructionPlan info : plans) {
//				JSONObject object = new JSONObject();
//				object.put("id", info.getId());
//				object.put("title", info.getWorkOrderInfo().getOrderInfo().getTitle());
//				object.put("date", info.getPlanDate()==null?"정보없음":info.getPlanDate().format(format));
//				object.put("company", info.getCompany().getCompanyName());
//				object.put("brand", info.getWorkOrderInfo().getOrderInfo().getBrand().getCompanyName());
//				object.put("user", info.getUser()==null?"정보없음":info.getUser().getName());
//				object.put("state", info.isComplete() ? "완료" : "시공진행중");
//				String brand = object.get("brand").toString();
//				String company = object.get("company").toString();
//				List<ConstructionLocation> locations = info.getLocations();
//				JSONArray arry = new JSONArray();
//				if(locations.size() == 0)
//					continue;
//				for (ConstructionLocation location : locations) {
//					JSONObject i = new JSONObject();
//					List<String> urls = new ArrayList<>();
//					if (location.isComplete()) {
//						try {
//							ConstructionResult result = (ConstructionResult) session
//									.createQuery("Select this_ from ConstructionResult this_ where this_.location.id="
//											+ location.getId())
//									.getResultList().get(0);
//							i.put("id", result.getId());
//							i.put("company", company);
//							i.put("location", result.getLocation().getLocation().getTitle());
//							i.put("brand", brand);
//							i.put("user", result.getUser().getName());
//							i.put("date", result.getTime().format(time));
//							for (String u : result.getResultImageUrls()) {
//								urls.add(u);
//							}
//						} catch (IndexOutOfBoundsException e) {
//							e.printStackTrace();
//							System.out.println("시공 데이터 오류 : 완료처리는 되어있는데 결과 데이터 없음 - " + location.getId());
//						}
//					} else {
//						i.put("id", 0);
//						i.put("company", company);
//						i.put("brand", brand);
//						i.put("location","-");
//						i.put("user", "시공 미완료");
//						i.put("date", "-");
//						urls.add("noimage.png");
//					}
//					i.put("url", urls);
//					arry.add(i);
//				}
//				object.put("result", arry);
//				ret.add(object);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			SessionMethod.closeSession(session);
//		}
//		return ret;
//	}

	@Override
	public JSONArray ConstructionPlan() {
		Session session = null;
		JSONArray ret = new JSONArray();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			session = factory.openSession();
			List<ConstructionPlan> plans = session.createQuery(
					"Select this_ from ConstructionPlan this_ where this_.isComplete = false and this_.isDisabled = false and this_.isConfirm = false")
					.getResultList();

			for (ConstructionPlan info : plans) {
				JSONObject object = new JSONObject();
				object.put("id", info.getId());
				object.put("title", info.getWorkOrderInfo() == null ? "No info"
						: info.getWorkOrderInfo().getOrderInfo().getTitle());
				object.put("branch",
						info.getWorkOrderInfo() == null ? "No info" : info.getWorkOrderInfo().getBranch().getTitle());
				object.put("date", info.getPlanDate() == null ? "정보없음" : info.getPlanDate().format(format));
				object.put("company", info.getWorkOrderInfo()==null?"정보 없음":info.getWorkOrderInfo().getBranch().getCompany() == null ? "no info"
						: info.getWorkOrderInfo().getBranch().getCompany().getCompanyName());
				object.put("brand", info.getWorkOrderInfo() == null ? "No info"
						: info.getWorkOrderInfo().getOrderInfo().getBrand().getCompanyName());
				object.put("user", info.getUser() == null ? "정보없음" : info.getUser().getName());
				object.put("state", info.isComplete() ? "완료" : "시공진행중");
				object.put("product",
						info.getWorkOrderInfo() == null ? "No info" : info.getWorkOrderInfo().getProduct());
				object.put("order",
						info.getWorkOrderInfo() == null ? "정보 없음" : info.getWorkOrderInfo().getOrderInfo().getTitle());
				object.put("brand", info.getWorkOrderInfo() == null ? "정보 없음"
						: info.getWorkOrderInfo().getOrderInfo().getBrand().getCompanyName());
				object.put("construction",
						info.getWorkOrderInfo() == null ? "정보 없음"
								: info.getWorkOrderInfo().getBranch().getCompany() == null ? "정보없음"
										: info.getWorkOrderInfo().getBranch().getCompany().getCompanyName());
				object.put("design", info.getWorkOrderInfo() ==null?"정보 없음":info.getWorkOrderInfo().getDesign() == null ? "no_image.png"
						: info.getWorkOrderInfo().getDesign().getUrl());
				object.put("cId", info.getWorkOrderInfo().getBranch().getCompany() == null?0:info.getWorkOrderInfo().getBranch().getCompany().getId());
				
				ret.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	@Override
	public JSONArray getConstructionRes() {
		Session session = null;
		JSONArray ret = new JSONArray();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			session = factory.openSession();
			List<ConstructionPlan> plans = session.createQuery(
					"Select this_ from ConstructionPlan this_ where this_.isComplete = false and this_.isDisabled = false and this_.isConfirm = true order by plandate")
					.getResultList();

			for (ConstructionPlan info : plans) {
				JSONObject object = new JSONObject();
				object.put("id", info.getId());
				object.put("title", info.getWorkOrderInfo() == null ? "No info"
						: info.getWorkOrderInfo().getOrderInfo().getTitle());
				object.put("branch",
						info.getWorkOrderInfo() == null ? "No info" : info.getWorkOrderInfo().getBranch().getTitle());
				object.put("date", info.getPlanDate() == null ? "정보없음" : info.getPlanDate().format(format));
				object.put("company", info.getCompany() == null ? "No info" : info.getCompany().getCompanyName());
				object.put("brand", info.getWorkOrderInfo() == null ? "No info"
						: info.getWorkOrderInfo().getOrderInfo().getBrand().getCompanyName());
				object.put("user", info.getUser() == null ? "정보없음" : info.getUser().getName());
				object.put("state", info.isComplete() ? "완료" : "시공진행중");
				object.put("product",
						info.getWorkOrderInfo() == null ? "No info" : info.getWorkOrderInfo().getProduct());
				object.put("order",
						info.getWorkOrderInfo() == null ? "정보 없음" : info.getWorkOrderInfo().getOrderInfo().getTitle());
				object.put("brand", info.getWorkOrderInfo() == null ? "정보 없음"
						: info.getWorkOrderInfo().getOrderInfo().getBrand().getCompanyName());
				object.put("construction",
						info.getWorkOrderInfo() == null ? "정보 없음"
								: info.getWorkOrderInfo().getBranch().getCompany() == null ? "정보없음"
										: info.getWorkOrderInfo().getBranch().getCompany().getCompanyName());
				object.put("design", info.getWorkOrderInfo().getDesign() == null ? "no_image.png"
						: info.getWorkOrderInfo().getDesign().getUrl());
				object.put("construction", info.getCompany().getCompanyName());
				ret.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	@Override
	public JSONArray getConstructionCompany() {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();

			List<ConstructionCompany> company = session
					.createQuery("Select this_ from ConstructionCompany this_ where this_.disabled = false")
					.getResultList();
			for (ConstructionCompany com : company) {
				JSONObject ob = new JSONObject();
				ob.put("id", com.getId());
				ob.put("company", com.getCompanyName());

				ret.add(ob);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}
	
	@Override
	public boolean ConstructionConfirm(int id, int company) {
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
			
			ConstructionPlan plan = session.find(ConstructionPlan.class, id);
			plan.setCompany(session.find(ConstructionCompany.class, company));
			plan.setConfirm(true);
			
			session.update(plan);
			transaction.commit();
			
			LoggingTool.createLog(session, user, "Manager", "시공지시 등록: " +plan.getWorkOrderInfo().getOrderInfo().getTitle()+ "//" + plan.getWorkOrderInfo().getBranch().getTitle());
			state = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
		
	}
}
