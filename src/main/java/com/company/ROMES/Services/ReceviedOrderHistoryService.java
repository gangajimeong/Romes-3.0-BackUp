package com.company.ROMES.Services;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.swing.border.EtchedBorder;

import org.apache.poi.ss.usermodel.Row;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.company.ROMES.dao.ConstructionDAO;
import com.company.ROMES.dao.LotDAO;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.Brand;
import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.ConstructionLocation;
import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.ConstructionResult;
import com.company.ROMES.entity.Design;
import com.company.ROMES.entity.DesignMaster;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.Lot;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Notice;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.ShippingHistory;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.dao.ReceivedOrderDAOInterface;
import com.company.ROMES.interfaces.service.ReceivedOrderHistoryInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Error_code.ResultCode;

@Service
public class ReceviedOrderHistoryService implements ReceivedOrderHistoryInterface {

	@Autowired
	SessionFactory factory;

	@Autowired
	ConstructionDAO constructionDao;

	@Autowired
	ReceivedOrderDAOInterface dao;

	@Autowired
	LotDAO lotDao;

	@Autowired
	UserDAO us;

	@Autowired
	GeoCoderService getPosition;

	@Override
	public List<ConstructionPlan> ConstructionPlanList(Session session, int userId, boolean receviedProduct) {
		User user = session.find(User.class, userId);
		List<ConstructionPlan> plans = session
				.createQuery("Select this_ from ConstructionPlan this_ where this_.receivedOrderInfo.isShipment=" + true
						+ " And this_.isReceiveProduct=" + receviedProduct + " And this_.company.id="
						+ user.getConstructionCompany().getId() + " And this_.isComplete=" + false
						+ " and this_.receivedOrderInfo.isDisabled= false")
				.getResultList();
		return plans;
	}

// 재품 수령
//	@Override
	public JSONObject getReceiveProductData(int userId) {
		JSONObject ret = new JSONObject();
//		Session session = null;
//		try {
//			session = factory.openSession();
//			User user = session.find(User.class, userId);
//			List<ConstructionPlan> plans = constructionDao.getReceiveProductsList(session, user);
//			JSONArray arry = new JSONArray();
//			for (ConstructionPlan plan : plans) {
//				JSONObject object = new JSONObject();
//				object.put("id", plan.getId());
//				object.put("time", plan.getPlanDate() == null ? "정보 없음"
//						: plan.getPlanDate().format(DateTimeFormatter.ISO_DATE));
//				JSONArray urls = new JSONArray();
//				List<DesignMaster> ms = plan.getDesigns(session);
//				for (DesignMaster m : ms) {
//					JSONObject o = new JSONObject();
//					o.put("url", m.getUrl());
//					urls.add(o);
//				}
////				List<ShippingHistory> tmplist = session
////						.createQuery("Select this_ from ShippingHistory this_ where this_.plan.info.id="
////								+ plan.getReceivedOrderInfo().getId())
////						.getResultList();
////
////				JSONArray lotarry = new JSONArray();
////				for (ShippingHistory h : tmplist) {
////					for (Lot lot : h.getLots()) {
////						JSONObject j = new JSONObject();
////						j.put("lot", lot.getLot());
////						j.put("count", lot.getCount());
////						j.put("product", lotDao.getProductName(session, lot));
////						lotarry.add(j);
////					}
////				}
////				object.put("lots", lotarry);
//				object.put("urls", urls);
//				object.put("title", plan.get);
////				object.put("count", lots.size());
//				object.put("count", 0);
//				object.put("company", plan.getReceivedOrderInfo().getOrderCompany().getCompanyName());
//				arry.add(object);
//			}
//			ret.put("result", ResultCode.SUCCESS);
//			ret.put("data", arry);
//		} catch (NullPointerException e) {
//			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
//			e.printStackTrace();
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			ret.put("result", ResultCode.DB_CONNECT_ERROR);
//		} catch (Exception e) {
//			ret.put("result", ResultCode.UNKNOWN_ERROR);
//			e.printStackTrace();
//		} finally {
//			if (session != null)
//				if (session.isOpen())
//					session.close();
//		}
		return ret;
	}

	// 수령 완료쳐리
	@Override
	public JSONObject receiveProduct(int userId, int planId) {
		JSONObject ret = new JSONObject();
//		Session session = null;
//		Transaction transaction = null;
//		try {
//			session = factory.openSession();
//			transaction = session.beginTransaction();
//			User user = session.find(User.class, userId);
//			ConstructionPlan plan = session.find(ConstructionPlan.class, planId);
//			plan.setReceiveProduct(true);
//			plan.setReceiveProductTime(LocalDateTime.now());
//			session.update(plan);
//			transaction.commit();
//			ret.put("result", ResultCode.SUCCESS);
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			if (transaction != null)
//				if (transaction.isActive())
//					transaction.rollback();
//			ret.put("result", ResultCode.DB_CONNECT_ERROR);
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//			if (transaction != null)
//				if (transaction.isActive())
//					transaction.rollback();
//			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (transaction != null)
//				if (transaction.isActive())
//					transaction.rollback();
//			ret.put("result", ResultCode.UNKNOWN_ERROR);
//		} finally {
//			if (session != null)
//				if (session.isOpen())
//					session.close();
//		}
		return ret;
	}

	// 개인 시공 실적 조회
	@Override
	public JSONObject getCompleteConstructionResult(int userId, int year, int month) {
		JSONObject ret = new JSONObject();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Session session = null;
		try {
			session = factory.openSession();
			List<ConstructionResult> results = null;
			User user = session.find(User.class, userId);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, 1);
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			System.out.println("userId = " + userId);
			System.out.println("year = " + year);
			System.out.println("month = " + month);
			Query query = null;
			if (user.getAuthority().equals("ROLE_CTT_USER"))
				query = session.createQuery("Select this_ from ConstructionResult this_ where this_.user.id="
						+ user.getId() + " and this_.time between :start and :end order by this_.time desc");
			else
				query = session
						.createQuery("Select this_ from ConstructionResult this_ where this_.location.plan.company.id="
								+ user.getConstructionCompany().getId()
								+ " and this_.time between :start and :end order by this_.time desc");
			results = query.setParameter("start", LocalDate.of(year, month, 1).atStartOfDay())
					.setParameter("end", LocalDate.of(year, month, lastDay).atTime(23, 59, 59)).getResultList();
//			System.out.println("this is results: "+results);
			JSONArray arry = new JSONArray();
			List<Brand> brandList = new ArrayList<>();
			for (ConstructionResult r : results) {
				JSONObject o = new JSONObject();
				o.put("id", r.getId());
				o.put("title", r.getLocation().getPlan().getWorkOrderInfo().getOrderInfo().getTitle());
				o.put("time", r.getTime().format(format));
				o.put("user", r.getUser().getName());
				o.put("company", r.getLocation().getLocation().getBrand().getCompany().getCompanyName());
				o.put("brand", r.getLocation().getLocation().getBrand().getCompanyName());
				o.put("brand_id", r.getLocation().getLocation().getBrand().getId());
				o.put("company_branch", r.getLocation().getLocation().getTitle());
				o.put("address", r.getLocation().getLocation().getAddress());
				o.put("construction_division", r.getUser().getConstructionCompany().getCompanyName());
				o.put("construction_user", r.getUser().getName());
				List<String> urls = new ArrayList<>();
				for (String s : r.getResultImageUrls()) {
					urls.add(s);
				}
				o.put("urls", urls);
//				List<String> designs = new ArrayList<>();
//				for (String s : r.getResultImageUrls()) {
//					designs.add(s);
//				}
				List<String> designs = new ArrayList<>();
				designs.add(
						r.getPlan() == null ? "/no_image.png" : r.getPlan().getWorkOrderInfo().getDesign()==null?"/no_image.png":r.getPlan().getWorkOrderInfo().getDesign().getUrl());

				o.put("designs", designs);
				arry.add(o);
				if (!brandList.contains(r.getLocation().getLocation().getBrand())) {
					brandList.add(r.getLocation().getLocation().getBrand());
				}
				System.out.println(o);
			}
			JSONArray brands = new JSONArray();
			for (Brand brand : brandList) {
				JSONObject o = new JSONObject();
				o.put("id", brand.getId());
				o.put("brand", brand.getCompanyName());
				brands.add(o);
			}
			ret.put("data", arry);
			ret.put("brand", brands);
			ret.put("result", ResultCode.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("result", ResultCode.UNKNOWN_ERROR);
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONObject getDashboardData(int userId) {
		Session session = null;
		JSONObject ret = new JSONObject();
		Calendar cal = Calendar.getInstance();
		int month_now = LocalDate.now().getMonthValue();
		JSONParser parser = new JSONParser();
		cal.set(LocalDate.now().getYear(), month_now - 1, 1);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		try {
			session = factory.openSession();
			User user = session.find(User.class, userId);
//			JSONArray receiveData = (JSONArray) parser.parse(getReceiveProductData(userId).get("data").toString());

			List<ConstructionLocation> constructions = session
					.createQuery("Select this_ from ConstructionLocation this_ where this_.isComplete=" + false
							+ " and this_.plan.company.id=" + user.getConstructionCompany().getId())
					.getResultList();
			int needSubmitCount = Integer.parseInt(session.createQuery(
					"Select count(this_) from ConstructionLocation this_ where this_.isComplete=true and this_.isBrandConfirm=false")
					.getSingleResult().toString());
			ret.put("need_receive_product", needSubmitCount);
			ret.put("need_construction", constructions.size());
			ret.put("complete_construction", session.createQuery(
					"Select Count(this_) from ConstructionResult this_ where this_.failed=false and this_.user.id="
							+ user.getId() + " and this_.time between :start and :end")
					.setParameter("start", LocalDate.of(cal.get(Calendar.YEAR), month_now, 1).atStartOfDay())
					.setParameter("end", LocalDate.of(cal.get(Calendar.YEAR), month_now, maxDay).atTime(23, 59, 59))
					.getSingleResult().toString());
			JSONArray barChar = new JSONArray();
			int c = 0;
			for (int i = 1; i < 13; i++) {
				JSONObject o = new JSONObject();
				o.put("label", i + "월");
				o.put("count", getNumberOfResult(session, i, userId));
				if (i == month_now) {
					c = Integer.parseInt(o.get("count").toString());
				}
				barChar.add(o);
			}
			ret.put("construction_result_data", barChar);
			JSONArray constData = new JSONArray();
			for (ConstructionLocation l : constructions) {
				JSONObject j = new JSONObject();
				j.put("planid", l.getPlan().getId());
				j.put("brand_id", l.getLocation().getBrand().getId());
				j.put("brand", l.getLocation()==null?"정보 업음":l.getLocation().getBrand().getCompanyName());
				j.put("branch", l.getLocation() == null ? "정보 없음" : l.getLocation().getTitle());
				j.put("branch_id", l.getLocation() == null ? 0 : l.getLocation().getId());
				j.put("lat", l.getLocation() == null ? 0 : l.getLocation().getLat());
				j.put("lng", l.getLocation() == null ? 0 : l.getLocation().getLng());
				j.put("isNull",
						(l.getLocation() == null ? true : (l.getLocation().getTitle().equals("") ? true : false)));
				j.put("isNull", l.getLocation().getAddress() == null);
				j.put("isConstruction", l.isComplete());
				j.put("id", l.getId());
				j.put("address", l.getLocation() == null ? "정보 없음" : l.getLocation().getAddress());
				j.put("detail", l.getLocation() == null ? "정보 없음" : l.getLocation().getDetail());
				List<String> urls = new ArrayList<String>();
				try {
					ConstructionResult result = (ConstructionResult) session
							.createQuery(
									"Select this_ from ConstructionResult this_ where this_.location.id=" + l.getId())
							.getResultList().get(0);

					for (String s : result.getResultImageUrls()) {
						urls.add(s);
					}
				} catch (IndexOutOfBoundsException e) {

				}
				j.put("urls", urls);
				List<String> sample = new ArrayList<String>();
				for (DesignMaster m : l.getPlan().getWorkOrderInfo().getSampleDesigns()) {
					sample.add(m.getUrl());
				}
				if (sample.size() == 0)
					sample.add("no_image.png");
				j.put("sample", sample);

				constData.add(j);
			}
			ret.put("const_data", constData);
//			ret.put("recevie_product", receiveData);
			JSONArray notice = new JSONArray();
			List<Notice> notices = new ArrayList<>();
			if(user.getDivision() != null) {
			notices = session
					.createQuery("Select this_ from Notice this_ where this_.user.division.id="
							+ user.getDivision().getId() + " order by this_.time desc")
					.setMaxResults(100).getResultList();
			}else {
				notices = session
						.createQuery("Select this_ from Notice this_ where this_.user.constructionCompany.id="
								+ user.getConstructionCompany().getId() + " order by this_.time desc")
						.setMaxResults(100).getResultList();
			}
			for (Notice n : notices) {
				JSONObject object = new JSONObject();
				object.put("id", n.getId());
				object.put("uploader", n.getUser().getName());
				object.put("time", n.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				object.put("title", n.getTitle());
				object.put("msg", n.getMsg());
				List<String> urls = new ArrayList<>();
				for (String u : n.getImages()) {
					urls.add(u);
				}
				object.put("urls", urls);
				notice.add(object);
			}
			ret.put("notice", notice);
			ret.put("result", ResultCode.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		System.out.println(ret);
		return ret;
	}

	private int getNumberOfResult(Session session, int month, int userId) {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), month - 1, 1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return Integer.parseInt(session.createQuery(
				"Select Count(this_) from ConstructionResult this_ where this_.time between :start and :end and this_.user.id="
						+ userId)
				.setParameter("start", LocalDate.of(cal.get(Calendar.YEAR), month, 1).atStartOfDay())
				.setParameter("end", LocalDate.of(cal.get(Calendar.YEAR), month, lastDay).atTime(23, 59, 59))
				.getSingleResult().toString());
	}

	private int getResultOfTeam(Session session, int month, int teamId) {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), month - 1, 1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return Integer.parseInt(session.createQuery(
				"Select Count(this_) from ConstructionResult this_ where this_.time between :start and :end and this_.user.division.id="
						+ teamId)
				.setParameter("start", LocalDate.of(cal.get(Calendar.YEAR), month, 1).atStartOfDay())
				.setParameter("end", LocalDate.of(cal.get(Calendar.YEAR), month, lastDay).atTime(23, 59, 59))
				.getSingleResult().toString());
	}

	@Override
	public int saveReceivedOrder(JsonObject data) {
		Session session = null;
		Transaction transaction = null;
		Gson gson = new Gson();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ReceivedOrderInfo info = new ReceivedOrderInfo();
			User user = gson.fromJson(data.get("user").getAsJsonObject(), User.class);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return 0;
	}

	@Override
	public JSONObject createReceivedOrderInfo(HashMap<Object, Object> info, String products, List<String> urls) {
//		Session session = null;
//		Transaction transaction = null;
		JSONObject ret = new JSONObject();
//		boolean state = false;
//
//		try {
//			session = factory.openSession();
//			transaction = session.beginTransaction();
//			JSONParser parser = new JSONParser();
//			JSONArray productArr = (JSONArray) parser.parse(products);
//			ReceivedOrderInfo ro = new ReceivedOrderInfo();
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			String userID = auth.getName();
//			User user = new User();
//			if (!userID.equals("anonymousUser")) {
//				user = us.selectByIdForUser(session, userID);
//			} else {
//				user = null;
//			}
//			ro.setTitle(info.get("title").toString());
////			ro.setReceivedOrderDate(
////					LocalDate.parse((CharSequence) info.get("receivedOrderDate"), DateTimeFormatter.ISO_DATE));
//			ro.setReceivedOrderDate(LocalDateTime.now());
////			ro.setDeadLine(LocalDate.parse((CharSequence) info.get("deadLine"), DateTimeFormatter.ISO_DATE));
////			ro.setPrice(Integer.parseInt(info.get("price").toString()));
//			Company cp = session.find(Company.class, Integer.parseInt(info.get("company.id").toString()));
////			ro.setOrderCompany(cp);
//
//			for (Object product : productArr) {
//				JSONObject productData = new JSONObject();
//				ManufactureProduct m = new ManufactureProduct();
//				ret = (JSONObject) parser.parse(product.toString());
//				m= session.find(ManufactureProduct.class, Integer.parseInt(ret.get("product_id").toString()));
////				ro.addOrReplaceOrderProduct(m, Integer.parseInt(ret.get("cnt").toString()));
//			}
//			ro.setUser(user);
//
//			dao.saveOrUpdate(session, ro);
////			ro.setSampleDesignPath(makeDesignMaster(session, ro, urls, user));
//			transaction.commit();
//			state = true;
//			ret.put("result", state);
//			ret.put("id", ro.getId());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (transaction.isActive())
//				transaction.rollback();
//		} finally {
//			if (session != null)
//				if (session.isOpen())
//					session.close();
//		}
		return ret;
	}

	public List<DesignMaster> makeDesignMaster(Session session, ReceivedOrderInfo ro, List<String> urls, User user) {
		List<DesignMaster> samples = new ArrayList<DesignMaster>();
		try {

			for (String url : urls) {
				DesignMaster designMaster = new DesignMaster();
//				designMaster.setInfo(ro);
				designMaster.setRegistrationDate(LocalDateTime.now());
				designMaster.setRegistUser(user);
				designMaster.setUrl(url);
				session.saveOrUpdate(designMaster);
				samples.add(designMaster);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return samples;

	}

	@Override
	public JSONArray receivedOrderInfos() {
		Session session = null;

		JSONArray rets = new JSONArray();
		try {
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime preMonth = today.minusMonths(1);

			session = factory.openSession();
			List<ReceivedOrderInfo> lists = new ArrayList<ReceivedOrderInfo>();
			lists = dao.selectData(session, today, preMonth);

			for (ReceivedOrderInfo r : lists) {
				JSONObject ret = new JSONObject();
				ret.put("id", r.getId());
				ret.put("title", r.getTitle());
				ret.put("receivedOrderDate", r.getReceivedOrderDate().toLocalDate());
				ret.put("company", r.getOrderCompany().getCompanyName());
				ret.put("writter", r.getUser() != null ? r.getUser().getName() : "정보 없음");
				ret.put("writtenDate", r.getWrittenDate().toLocalDate());
				ret.put("update", r.getUpdateDate() != null ? r.getUpdateDate().toLocalDate() : "");
				ret.put("brand", r.getBrand().getCompanyName());
				ret.put("bId", r.getBrand().getId());

				rets.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	// TODO : this
	public String stateCheck(boolean[] state) {
		String states = "No info";

		if (state[0])
			states = "결제 완료";
		else if (state[0] && state[1])
			states = "생산 완료";
		else if (state[0] && state[1] && state[2])
			states = "출하 완료";
		else if (state[0] && state[1] && state[2] && state[3])
			states = "수주 완료";
		else if (!state[0] && !state[1] && !state[2] && !state[3] && state[4] == false) {
			states = "수주 작성 완료";
		} else if (state[4] == true) {
			states = "시공 계획 작성 완료";
		} else
			states = "진행 오류";
		return states;
	}

	@Override
	public JSONObject getProductsAndImages(int info_id) {
		Session session = null;
		JSONObject datas = new JSONObject();

		try {
			ReceivedOrderInfo info = new ReceivedOrderInfo();
			JSONArray rets = new JSONArray();
			JSONArray urls = new JSONArray();
			JSONArray urlsID = new JSONArray();
			session = factory.openSession();
			info = session.find(ReceivedOrderInfo.class, info_id);
//			List<ManufactureProduct> products = info.getProducts();
//			for (ManufactureProduct m : products) {
//				JSONObject ret = new JSONObject();
//				ret.put("id", m.getId());
//				ret.put("code", m.getProductCode());
//				ret.put("name", m.getName());
//				ret.put("cnt", info.getOrderProductCount(m));
//				ret.put("Ep", m.getDefaultSalePrice());
//				ret.put("Total", info.getOrderProductCount(m) * m.getDefaultSalePrice());
//				rets.add(ret);

//			}
			datas.put("products", rets);

//			for (DesignMaster d : info.getSampleDesignPath()) {
//				urls.add(d.getUrl());
//				urlsID.add(d.getId());
//			}
			datas.put("urls", urls);
			datas.put("urlsID", urlsID);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return datas;
	}

	@Override
	public List<Division> lists() {
		Session session = null;
		List<Division> lists = new ArrayList<Division>();
		try {
			session = factory.openSession();
			lists = session.createQuery(
					"Select this_ from Division this_ where this_.disabled = false and this_.isConstruction = true order by this_.id desc ")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return lists;
	}

	@Override
	public LatLng createLatLng(LatLng data) {
		LatLng Latdata = new LatLng();
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Latdata = getPosition.addressToLocation(session, data.getAddress());
			System.out.println(Latdata.toString());
			Latdata.setDetail(data.getDetail());
//			Latdata.setCompany(data.getCompany());
			Latdata.setTitle(data.getTitle());
			Latdata.setPostCode(data.getPostCode());
			if (Latdata != null)
				session.update(Latdata);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return Latdata;
	}

	@Override
	public JSONArray selectLatLng() {
		Session session = null;
		JSONArray rets = new JSONArray();
		List<LatLng> lists = new ArrayList<LatLng>();
		try {
			session = factory.openSession();
			lists = session.createQuery("select this_ from LatLng this_ ORDER BY this_.id DESC").getResultList();
			for (LatLng l : lists) {
				JSONObject ret = new JSONObject();
				ret.put("id", l.getId());
				ret.put("address", l.getAddress());
				ret.put("address2", l.getAddress2());
//				ret.put("company", l.getCompany().getCompanyName());
				ret.put("detail", l.getDetail());
				ret.put("title", l.getTitle());
				rets.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@Override
	public JSONObject createConstructionPlan(int sujuId, int divisionID, LocalDate plan, List<Integer> latlng) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			ConstructionPlan con = new ConstructionPlan();
			List<ConstructionLocation> locationLists = new ArrayList<ConstructionLocation>();

			ReceivedOrderInfo ro = session.find(ReceivedOrderInfo.class, sujuId);
			Division dv = session.find(Division.class, divisionID);

//			con.setConstructionDivision(dv);
//			con.setReceivedOrderInfo(ro);
//			con.setPlanConstructionDate(plan);
			session.save(con);
			System.out.println(latlng.size());
			for (int id : latlng) {
				LatLng lat = session.find(LatLng.class, id);
				ConstructionLocation cl = new ConstructionLocation();
				cl.setLocation(lat);
//				cl.setPlan(con);
				session.save(cl);
				locationLists.add(cl);
			}

			con.setLocations(locationLists);
			session.update(con);

			ret.put("id", con.getId());
//			ret.put("team", con.getConstructionDivision().getTeam());
			ret.put("place", con.getLocations().size());
//			ret.put("plan", con.getPlanConstructionDate());

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONArray selectConstructionPlan(int id) {
		Session session = null;
		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();
			List<ConstructionPlan> lists = new ArrayList<ConstructionPlan>();
			lists = session.createQuery("Select this_ from ConstructionPlan this_ where this_.receivedOrderInfo.id ="
					+ id + " AND this_.isDisabled = false order by this_.planConstructionDate desc").getResultList();
			for (ConstructionPlan c : lists) {
				JSONObject ret = new JSONObject();
				ret.put("id", c.getId());
//				ret.put("teamId", c.getConstructionDivision().getId());
//				ret.put("team", c.getConstructionDivision().getTeam());
//				ret.put("plan", c.getPlanConstructionDate());
				ret.put("place", c.getLocations().size());
				rets.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@Override
	public boolean updateReceivedOrderInfo(HashMap<Object, Object> info, String products, List<Integer> urls) {
		boolean state = false;
		Session session = null;
		Transaction transaction = null;
		ReceivedOrderInfo ro = new ReceivedOrderInfo();
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			JSONParser parser = new JSONParser();
			JSONArray rets = (JSONArray) parser.parse(products);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}

			int companyId = Integer.parseInt(info.get("company.id").toString());
			ro = session.find(ReceivedOrderInfo.class, Integer.parseInt(info.get("id").toString()));

			if (!ro.getTitle().equals(info.get("title"))) {
				ro.setTitle(info.get("title").toString());
			}

//			if (ro.getOrderCompany().getId() != companyId) {
//				Company company = new Company();
//				company = session.find(Company.class, companyId);
//				ro.setOrderCompany(company);
//			}
//
//			if (urls.size() != ro.getSampleDesignPath().size()) {
//				List<DesignMaster> update = new ArrayList<DesignMaster>();
//
//				for (DesignMaster dm : ro.getSampleDesignPath()) {
//					if (!urls.contains(dm.getId())) {
//						session.delete(dm);
//					} else {
//						update.add(dm);
//					}
//				}
//				ro.setSampleDesignPath(update);
//			}
//			if (ro.getReceivedOrderDate() != info.get("receivedOrderDate")) {
//				
//				ro.setReceivedOrderDate(LocalDate.parse(info.get("receivedOrderDate").toString()));
//			}
//			if (ro.getReceivedOrderDate() != info.get("deadLine")) {
//				ro.setReceivedOrderDate(LocalDate.parse(info.get("deadLine").toString()));
//			}
			ro.setReceivedOrderDate(LocalDateTime.now());
//			ro.setPrice(Integer.parseInt(info.get("price").toString()));
			if (rets.size() != 0) {
				HashMap<ManufactureProduct, Integer> productInfo = new HashMap<>();
				for (Object product : rets) {
					JSONObject ret = new JSONObject();
					ManufactureProduct mp = new ManufactureProduct();
					ret = (JSONObject) parser.parse(product.toString());
					mp = session.find(ManufactureProduct.class, Integer.parseInt(ret.get("product_id").toString()));
					productInfo.put(mp, Integer.parseInt(ret.get("cnt").toString()));
				}
//				ro.setOrderProduct(productInfo);
			}
			if (user != null) {
				ro.setUser(user);
			}
			session.update(ro);
			transaction.commit();
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public boolean deleteReceivedOrderInfo(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ReceivedOrderInfo info = session.find(ReceivedOrderInfo.class, id);
			info.setDisabled(true);
			session.update(info);

			List<ConstructionPlan> lists = session
					.createQuery("Select this_ from ConstructionPlan this_ where this_.receivedOrderInfo.id =" + id)
					.getResultList();
			for (ConstructionPlan cp : lists) {
				cp.setDisabled(true);
				session.update(cp);
			}
			transaction.commit();
			state = true;

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public JSONArray planLocationList(int id) { // id: constructionPlan ID
		Session session = null;
		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();
			ConstructionPlan con = session.find(ConstructionPlan.class, id);
			for (ConstructionLocation cl : con.getLocations()) {
				JSONObject ret = new JSONObject();
				ret.put("id", cl.getId());
				ret.put("Lat_id", cl.getLocation().getId());
				ret.put("title", cl.getLocation().getTitle());
				ret.put("address", cl.getLocation().getAddress());
				ret.put("detail", cl.getLocation().getDetail());
				rets.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@Override
	public boolean updateConstructionPlan(HashMap<Object, Object> plan, List<Integer> latIds,
			List<Integer> conslocationList) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		int id = 0;
		int division_id = 0;
		LocalDate date = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			id = Integer.parseInt(plan.get("id").toString());
			if (id != 0) {

				division_id = Integer.parseInt(plan.get("division").toString());
				date = LocalDate.parse(plan.get("date").toString());
				List<ConstructionLocation> locations = new ArrayList<ConstructionLocation>();

				ConstructionPlan cp = new ConstructionPlan();
				cp = session.find(ConstructionPlan.class, id);
//				if (cp.getConstructionDivision().getId() != division_id) {
//					Division div = session.find(Division.class, division_id);
//					cp.setConstructionDivision(div);
//				}
//				if (cp.getPlanConstructionDate() != date) {
//					cp.setPlanConstructionDate(date);
//				}
				if (conslocationList.size() != 0) {
					for (ConstructionLocation c : cp.getLocations()) {
						if (!conslocationList.contains(c.getId())) {
							session.delete(c);
						} else {
							locations.add(c);
						}
					}
				}

				if (latIds.size() != 0) {
					for (int i = 0; i < latIds.size(); i++) {
						LatLng location = new LatLng();
						location = session.find(LatLng.class, latIds.get(i));
						ConstructionLocation create = new ConstructionLocation();
						create.setLocation(location);
//						create.setPlan(cp);
						session.save(create);
						locations.add(create);
					}

				}
				cp.setLocations(locations);
				session.update(cp);
				transaction.commit();
				state = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public boolean deleteConstructionPlan(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ConstructionPlan cp = new ConstructionPlan();
			cp = session.find(ConstructionPlan.class, id);
			cp.setDisabled(true);
			if (cp.getLocations().size() > 0) {
				List<ConstructionLocation> locations = cp.getLocations();
				for (ConstructionLocation cl : locations) {
					session.delete(cl);
				}
				locations.clear();
				cp.setLocations(locations);
			}

			session.update(cp);
			transaction.commit();
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();

		}
		return state;
	}

	@Override
	public JSONArray getCompanyList() {
		Session session = null;
		JSONArray rets = null;

		try {
			rets = new JSONArray();
			session = factory.openSession();
			List<Company> lists = new ArrayList<Company>();
			lists = session
					.createQuery("Select this_ from Company this_ where this_.disabled = false order by this_.id")
					.getResultList();
			for (Company company : lists) {
				JSONObject ret = new JSONObject();
				ret.put("id", company.getId());
				ret.put("name", company.getCompanyName());
				rets.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rets.add("error");
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@Override
	public JSONArray getUserByDivision() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getBrandList() {
		Session session = null;
		JSONArray rets = null;

		try {
			rets = new JSONArray();
			session = factory.openSession();
			List<Brand> lists = new ArrayList<Brand>();
			lists = session.createQuery("Select this_ from Brand this_ where this_.disabled = false order by this_.id")
					.getResultList();
			for (Brand brand : lists) {
				JSONObject ret = new JSONObject();
				ret.put("id", brand.getId());
				ret.put("name", brand.getCompanyName());
				rets.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rets.add("error");
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	@Override
	public boolean createOrderInfo(ReceivedOrderInfo info) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.getName();
		
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			User user = us.selectByIdForUser(session, userID);
			info.setUser(user);
			info.setWrittenDate(LocalDateTime.now());
			session.save(info);
			LoggingTool.createLog(session, user, "Manager", "수주정보 등록: "+info.getTitle());
			transaction.commit();
			state = true;
		} catch (NoResultException e) {
			System.out.println("Need Login");
		} 
		catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public int updateOrderInfo(ReceivedOrderInfo info) {
		Session session = null;
		Transaction transaction = null;
		int state = ResultCode.FAIL_RESULT;
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
			
			ReceivedOrderInfo ro = new ReceivedOrderInfo();
			ro = session.find(ReceivedOrderInfo.class, info.getId());
			// 청구지 내용 변경 확인
			if (ro.getOrderCompany().getId() != info.getOrderCompany().getId()) {
				ro.setOrderCompany(info.getOrderCompany());
			}
			// 브랜드 내용 변경 확인
			if (ro.getBrand().getId() != info.getBrand().getId()) {
				ro.setBrand(info.getBrand());
			}
			// 수주일 내용 변경 확인
			if (ro.getReceivedOrderDate() != info.getReceivedOrderDate()) {
				ro.setReceivedOrderDate(info.getReceivedOrderDate());
			}

			if (ro.getTitle() != info.getTitle()) {
				ro.setTitle(info.getTitle());
			}
			if (user != null) {
				ro.setLastUpdateUser(user);
			}
			ro.setUpdateDate(LocalDateTime.now());
			LoggingTool.createLog(session, user, "Manager", "수주정보 업데이트: "+info.getTitle());
			session.update(ro);
			transaction.commit();
			state = ResultCode.SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public JSONObject createProductData(HashMap<Object, Object> info, List<String> urls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteOrderInfo(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			
			List<WorkOrderInfo> wo = session.createQuery("Select this_ from WorkOrderInfo this_ where this_.orderInfo.id =" + id).getResultList();
			if(wo.size() > 0) {
				return false;
			}
			
			
			ReceivedOrderInfo info = (ReceivedOrderInfo) session.find(ReceivedOrderInfo.class, id);
			info.setDisabled(true);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "수주정보 삭제: "+info.getTitle());
			
			session.delete(info);
			transaction.commit();
			state = true;

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public JSONArray selectOrderInfoById(int id) {
		Session session = null;
		Transaction transaction = null;
		JSONArray ret = null;
		try {

			session = factory.openSession();
			transaction = session.beginTransaction();
			ret = new JSONArray();
			ReceivedOrderInfo info = new ReceivedOrderInfo();
			info = session.find(ReceivedOrderInfo.class, id);
			ret.add(info.getId());
			ret.add(info.getTitle());
			ret.add(info.getReceivedOrderDate().toLocalDate());
			ret.add(info.getOrderCompany().getId());
			ret.add(info.getBrand().getId());
		} catch (Exception e) {
			e.printStackTrace();
			ret.add(false);
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONArray selectOrderInfoByDate(LocalDateTime start, LocalDateTime end) {
		Session session = null;
		JSONArray rets = null;
		JSONArray ret = null;
		List<ReceivedOrderInfo> lists = null;
		try {
			rets = new JSONArray();
			lists = new ArrayList<ReceivedOrderInfo>();
			lists = dao.selectData(session, start, end);
			for (ReceivedOrderInfo info : lists) {
				ret = new JSONArray();
				ret.add(info.getId());
				ret.add(info.getTitle());
				ret.add(info.getReceivedOrderDate().toLocalDate());
				ret.add(info.getOrderCompany().getCompanyName());
				ret.add(info.getUser() != null ? info.getUser().getName() : "정보 없음");
				ret.add(info.getWrittenDate().toLocalDate());
				ret.add(info.getUpdateDate() != null ? info.getUpdateDate().toLocalDate() : "");
				rets.add(ret);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ret.add(false);
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;
	}

	public JSONArray workOrderInfos() {
		Session session = null;

		JSONArray rets = new JSONArray();
		try {
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime preMonth = today.minusMonths(1);

			session = factory.openSession();
			List<WorkOrderInfo> lists = new ArrayList<WorkOrderInfo>();
//			lists = dao.selectData2(session,today,preMonth);
			lists = session.createQuery("Select this_ from WorkOrderInfo this_ where this_.isFinished=false order by starttime")
					.getResultList();
			for (WorkOrderInfo r : lists) {
				JSONObject ret = new JSONObject();
				ret.put("id", r.getId());
				ret.put("locate", r.getBranch().getTitle());
//				ret.put("brand", r.getTitle());
				ret.put("product", r.getProduct());
				ret.put("size", r.getSize());
				ret.put("count", r.getPlanCount());
				ret.put("url", r.getDesign() == null ? "/Design/no_image.png"
						: r.getDesign().getUrl() == null ? "/Design/no_image.png" : r.getDesign().getUrl());
				rets.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return rets;

	}

	@Override
	public int createWorkOrderInfo(JsonArray ret, JsonArray data) {
		Session session = null;
		Transaction transaction = null;
		boolean result;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.getName();
		User user = new User();
		if (!userID.equals("anonymousUser")) {
			user = us.selectByIdForUser(session, userID);
		} else {
			user = null;
		}

		int orderid = 0;
		try {
			LocalDateTime today = LocalDateTime.now();
			session = factory.openSession();

			transaction = session.beginTransaction();

			ReceivedOrderInfo order = session.find(ReceivedOrderInfo.class, data.get(3).getAsInt());
			LatLng latlng = session.find(LatLng.class, ret.get(1).getAsInt());
			WorkOrderInfo info = new WorkOrderInfo();

			info.setBranch(latlng);
			info.setProduct(ret.get(2).getAsString());
			info.setCoating(ret.get(3).getAsBoolean());
			info.setBack(ret.get(4).getAsBoolean());
			info.setSize(ret.get(5).getAsString());
			info.setPlanCount(ret.get(6).getAsInt());
			info.setManufacturing(ret.get(7).getAsString());
			info.setConstruction(ret.get(11).getAsBoolean());
			info.setStartTime(today);
//			info.setEndTime(today);
			info.setOrderInfo(order);

			session.save(info);

			WorkOrderInfo findid = session.find(WorkOrderInfo.class, info.getId());
			LoggingTool.createLog(session, user, "Manager", "작업지시 등록: "+info.getProduct());
			transaction.commit();

			orderid = findid.getId();
			result = true;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			if (transaction != null)
				transaction.isActive();
			transaction.rollback();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();

		}
		return orderid;
	}

	@Override
	public boolean createConstructionPlan(int id) {
		Session session = null;
		Transaction transaction = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.getName();
		User user = new User();
		if (!userID.equals("anonymousUser")) {
			user = us.selectByIdForUser(session, userID);
		} else {
			user = null;
		}

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			LocalDateTime today = LocalDateTime.now();

			WorkOrderInfo info = session.find(WorkOrderInfo.class, id);

			ConstructionPlan plan = new ConstructionPlan();
			plan.setWorkOrderInfo(info);
			plan.setPlanDate(today);

			// 시공사 추가 conpany_id

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}

		return true;
	}

	@Override
	public JSONArray getlatlng() {
		Session session = null;

		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();
			List<LatLng> latlng = new ArrayList<LatLng>();
			latlng = session.createQuery("Select this_ from LatLng this_ order by this_.id").getResultList();

			for (LatLng branch : latlng) {
				JSONObject ret = new JSONObject();
				ret.put("id", branch.getId());
				ret.put("address", branch.getAddress());
				ret.put("branch", branch.getTitle());
				ret.put("brand", branch.getBrand());

				rets.add(ret);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rets;
	}

	@Override
	public JSONArray period(String preTime, String lastTime) {
		Session session = null;
		JSONArray ret = new JSONArray();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String[] keys = { "id", "writtenDate", "brand", "name", "orderDate" };

		try {
			session = factory.openSession();
			LocalDateTime preDate = LocalDate.parse(preTime, format).atTime(0, 0, 0);
			LocalDateTime lastDate = LocalDate.parse(lastTime, format).atTime(23, 59, 59);
			String query = ("Select this_ from ReceivedOrderInfo this_ where this_.isComplete = false and (this_.writtenDate between :start and :end )");
			List<ReceivedOrderInfo> info = session.createQuery(query).setParameter("start", preDate)
					.setParameter("end", lastDate).getResultList();
			System.out.println(preDate);
			System.out.println(lastDate);
//			info.stream().forEach((data) -> {
//				JSONObject ob = new JSONObject();
//				ob.put(keys[0],data.getId());
//				ob.put(keys[1], data.getWrittenDate().format(format));
//				ob.put(keys[2], data.getBrand().getCompanyName());
//				ob.put(keys[3], data.getUser() == null ? "정보 없음": data.getUser().getName());
//				ob.put(keys[4], data.getReceivedOrderDate().format(format));
//				
//				object.put("data", ob);
//			});

			for (ReceivedOrderInfo data : info) {
				JSONObject ob = new JSONObject();
				ob.put(keys[0], data.getId());
				ob.put(keys[1], data.getWrittenDate().format(format));
				ob.put(keys[2], data.getBrand().getCompanyName());
				ob.put(keys[3], data.getUser() == null ? "정보 없음" : data.getUser().getName());
				ob.put(keys[4], data.getReceivedOrderDate().format(format));

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
	public String SaveSampleImg(MultipartFile file, int id) {
		String url = "/Design/";
		String saveDir = System.getProperty("user.home") + "/uploadImages" + url;
		Session session = null;
		System.out.println(saveDir);

		String result = null;
		try {

			session = factory.openSession();
			WorkOrderInfo order = session.find(WorkOrderInfo.class, id);
			String Brand = order.getOrderInfo().getBrand().getCompanyName() + "/";

			File BFolder = new File(saveDir);
			if (!BFolder.exists()) {
				BFolder.mkdir();
			}

			saveDir = saveDir + Brand;

			File Dir = new File(saveDir);
			if (!Dir.exists()) {
				Dir.mkdir();
			}
			file.transferTo(new File(saveDir + file.getOriginalFilename()));

			result = url + Brand + file.getOriginalFilename();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return result;
	}

	@Override
	public boolean registDesign(String filename, int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			WorkOrderInfo order = session.find(WorkOrderInfo.class, id);
			Design DM = new Design();

			DM.setTitle(order.getBranch().getTitle());
			DM.setUrl(filename);
			DM.setTime(LocalDateTime.now());

			order.setDesign(DM);
			order.setStartTime(LocalDateTime.now());

			session.save(DM);
			session.save(order);
			transaction.commit();
			state = true;
			System.out.println("hihihihihi");

		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public boolean importExcel(JsonObject datas) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			String CompanyName = datas.get("companyName").getAsString();
			String CEO_Name = datas.get("CEO_Name").getAsString();
			String businessNumber = datas.get("businessNumber").getAsString();
			String realAddress = datas.get("realAddress").getAsString();

			System.out.println(CompanyName);

			List<Company> com = session.createQuery("Select this_ from Company this_").getResultList();
			for (Company company : com) {
				if (company.getCompanyName().equals(CompanyName)) {
					state = true;
					break;
				}
			}

			System.out.println(state);
			if (state == false) {
				System.out.println("회사 없음 / 회사 등록");
				Company company = new Company();
				company.setCompanyName(CompanyName);
				company.setCEO_Name(CEO_Name);
				company.setBusinessNumber(businessNumber);
				company.setRealAddress(realAddress);
				session.save(company);
			} else {
				System.out.println("회사 있음 / break");
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}

	public JSONArray setWorkorderBrand(int id) {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();

			Brand brand = session.find(Brand.class, id);

			for (LatLng latlng : brand.getBranch()) {
				JSONObject ob = new JSONObject();
				ob.put("id", latlng.getId());
				ob.put("branch", latlng.getTitle());

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
	public JSONArray orderFindBrand(int id) {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<Brand> brand = session.createQuery("Select this_ from Brand this_ where this_.company.id = " + id)
					.getResultList();

			for (Brand br : brand) {
				JSONObject ob = new JSONObject();
				ob.put("id", br.getId());
				ob.put("name", br.getCompanyName());

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
	public JSONArray updateOrderFindBrand(int id) {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			ReceivedOrderInfo info = session.find(ReceivedOrderInfo.class, id);
			Company company = info.getOrderCompany();
			
			List<Brand> brand = session.createQuery("Select this_ from Brand this_ where this_.company.id = " + company.getId())
					.getResultList();

			for (Brand br : brand) {
				JSONObject ob = new JSONObject();
				ob.put("id", br.getId());
				ob.put("name", br.getCompanyName());

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
	public boolean deleteWorkOrder(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			WorkOrderInfo info = session.find(WorkOrderInfo.class, id);
			Design d = new Design();
			if (info.getDesign() != null) {
				d = session.find(Design.class, info.getDesign().getId());
				info.setDesign(null);
				
				session.update(info);
				session.delete(d);
			}

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "작업지시 삭제: "+info.getProduct());
			
			session.delete(info);
			

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
