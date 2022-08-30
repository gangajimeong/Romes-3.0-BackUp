package com.company.ROMES.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.BrandDAO;
import com.company.ROMES.dao.LogDAO;
import com.company.ROMES.entity.Authorities;
import com.company.ROMES.entity.Brand;
import com.company.ROMES.entity.BrandUser;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.entity.ConstructionResult;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.dao.BrandUserDAOInterFace;
import com.company.ROMES.interfaces.service.BrandUserInterface;

import Error_code.ResultCode;

@Service
public class BrandUserService implements BrandUserInterface{
	@Autowired
	SessionFactory factory;
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	BrandDAO brandDao;
	@Autowired
	LogDAO logDao;
	
	@Override
	public BrandUser getBrandUserByIdPw(String id, String pw) {
		Session session = null;
		Transaction transaction = null;
		BrandUser brandUser = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			brandUser = brandDao.selectByIdPw(session, id, pw);
			if(brandUser != null) {
				LoggingTool.createLog(session, null, id, pw);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return brandUser;
	}

	@Override
	public void getConstructData() {
		Session session = null;
		Transaction transaction = null;
		BrandUser brandUser = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			SessionMethod.closeSession(session,transaction);
		}
	}
	
	@Override
	public JSONObject checkId(String id) {
		JSONObject ret = new JSONObject();
		Session session= null;
		try {
			session = factory.openSession();
			BrandUser user = (BrandUser) session.createQuery("Select this_ from BrandUser this_ where this_.LoginId='"+id+"'").getSingleResult();
			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		}catch(NoResultException e) {
			ret.put("result", ResultCode.SUCCESS);
		}catch(Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	@Override
	public JSONObject joinMember(String id, String pw, String name, int divisionId, String phoneNum,
			String email) {
		JSONObject ret = new JSONObject();
		Session session= null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			BrandUser user = null;
			try {
				user = (BrandUser) session.createQuery("Select this_ from BrandUser this_ where this_.LoginId='"+id+"'").getSingleResult();
				ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
			}catch(NoResultException e) {
				BCryptPasswordEncoder  encoder = new BCryptPasswordEncoder();
				String securePw = encoder.encode(pw);
				user = new BrandUser();
				user.setLoginId(id);
				user.setLoginPw(securePw);
				user.setEmail(email);
				user.setName(name);
				user.setPhone(phoneNum);
				user.setBrand(session.find(Brand.class, divisionId));
				session.save(user);
				ret.put("result", ResultCode.SUCCESS);	
			}
			transaction.commit();
		}catch(Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	@Override
	public JSONObject findUser(String name, String phoneNum, String email) {
		Session session = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			BrandUser user = (BrandUser) session.createQuery("Select this_ from BrandUser this_ where this_.name='"+name+"' and this_.email='"+email+"' and this_.phone='"+phoneNum+"'").getSingleResult();
			ret.put("id", user.getId());
			ret.put("loginid", user.getLoginId());
			ret.put("result", ResultCode.SUCCESS);
		}catch(NoResultException e) {
			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		}catch(NonUniqueResultException e){
			ret.put("result", ResultCode.DATA_ALERT_ERROR);
		}catch(Exception e) {			
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	@Override
	public JSONObject findUser(String name, String phoneNum, String email, String id) {
		Session session = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			BrandUser user = (BrandUser) session.createQuery("Select this_ from BrandUser this_ where this_.name='"+name+"' and this_.email='"+email+"' and this_.phone='"+phoneNum+"' and this_.LoginId='"+id+"'").getSingleResult();
			ret.put("id", user.getId());
			ret.put("result", ResultCode.SUCCESS);
		}catch(NoResultException e) {
			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		}catch(NonUniqueResultException e){
			ret.put("result", ResultCode.DATA_ALERT_ERROR);
		}catch(Exception e) {			
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	@Override
	public JSONObject getBrandList() {
		Session session = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			List<Brand> brand = session.createQuery("Select this_ from Brand this_").getResultList(); 
			JSONArray arry = new JSONArray();
			for(Brand brands: brand) {
				JSONObject o = new JSONObject();
				o.put("id", brands.getId());
				o.put("division", brands.getCompanyName());
				arry.add(o);
			}
			ret.put("data", arry);
			ret.put("result", ResultCode.SUCCESS);
		}catch(Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	@Override
	public JSONObject resetPassword(int userId,String newPw) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();
		try {
			session = factory.openSession();
			transaction= session.beginTransaction();
			BrandUser user = session.find(BrandUser.class, userId);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setLoginPw(encoder.encode(newPw));
			session.update(user);
			transaction.commit();
			ret.put("result", ResultCode.SUCCESS);
		}catch(Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(transaction != null)
				if(transaction.isActive())
					transaction.rollback();
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
//	@Override
//	public JSONObject getCompleteConstructionResult(int userId, int year, int month) {
//		JSONObject ret = new JSONObject();
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		Session session = null;
//		try {
//			session = factory.openSession();
//			List<ConstructionResult> results = null;
//			BrandUser user = session.find(BrandUser.class, userId);
////			user.getBrand().getId();
//			Calendar cal = Calendar.getInstance();
//			cal.set(year, month - 1, 1);
//			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//			Query query = null;
//			query = session.createQuery("Select this_ from ConstructionResult this_ where this_.branduser.id="
//						+ user.getId() + " and this_.time between :start and :end order by this_.time desc");
//			results = query.setParameter("start", LocalDate.of(year, month, 1).atStartOfDay())
//					.setParameter("end", LocalDate.of(year, month, lastDay).atTime(23, 59, 59)).getResultList();
//			System.out.println(query);
////			System.out.println(results);
//			JSONArray arry = new JSONArray();
//			List<Brand> brandList = new ArrayList<>();
//			for (ConstructionResult r : results) {
//				JSONObject o = new JSONObject();
//				o.put("id", r.getId());
//
////				o.put("title", r.getLocation().getPlan().getWorkOrderInfo().getOrderInfo().getTitle());
//				o.put("time", r.getTime().format(format));
//				o.put("user", r.getUser().getName());
//				o.put("company", r.getLocation().getLocation().getBrand().getCompany().getCompanyName());
//				o.put("brand", r.getLocation().getLocation().getBrand().getCompanyName());
//				o.put("brand_id", r.getLocation().getLocation().getBrand().getId());
//				o.put("company_branch", r.getLocation().getLocation().getTitle());
//				o.put("address", r.getLocation().getLocation().getAddress());
//				o.put("construction_division", r.getUser().getConstructionCompany().getCompanyName());
//				o.put("construction_user", r.getUser().getName());
//				
//				List<String> urls = new ArrayList<>();
//				for (String s : r.getResultImageUrls()) {
//					urls.add(s);
//				}
//				o.put("urls", urls);
//				List<String> designs = new ArrayList<>();
//				for (String s : r.getResultImageUrls()) {
//					designs.add(s);
//				}
//				o.put("designs", designs);
//				arry.add(o);
//				if (!brandList.contains(r.getLocation().getLocation().getBrand())) {
//					brandList.add(r.getLocation().getLocation().getBrand());
//				}
//			}
//			JSONArray brands = new JSONArray();
//			for (Brand brand : brandList) {
//				JSONObject o = new JSONObject();
//				o.put("id", brand.getId());
//				o.put("brand", brand.getCompanyName());
//				brands.add(o);
//			}
//			ret.put("data", arry);
//			ret.put("brand", brands);
//			ret.put("result", ResultCode.SUCCESS);
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret.put("result", ResultCode.UNKNOWN_ERROR);
//		} finally {
//			if (session != null)
//				if (session.isOpen())
//					session.close();
//		}
//		return ret;
//	}
	
	@Override
	public JSONObject approve(int id) {
		Session session = null;
		Transaction transaction = null;
		ConstructionResult consturctionResult = null;
		JSONObject ret = new JSONObject();
		
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			consturctionResult = session.find(ConstructionResult.class, id);
//			consturctionResult.setApprove(true);
			session.update(consturctionResult);
			ret.put("result", ResultCode.SUCCESS);
		}catch(NoResultException e) {
			ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
		}catch(NonUniqueResultException e){
			ret.put("result", ResultCode.DATA_ALERT_ERROR);
		}catch(Exception e) {			
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	
	@Override
	public JSONObject getCompleteConstructionResult(int userId, int year, int month) {
		JSONObject ret = new JSONObject();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Session session = null;
		try {
			session = factory.openSession();
			List<ConstructionResult> results = null;
			BrandUser user = session.find(BrandUser.class, userId);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, 1);
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			Query query = null;
			query = session.createQuery("Select this_ from ConstructionResult this_ where this_.plan.workOrderInfo.orderInfo.brand.id="
						+ user.getBrand().getId() + " and this_.time between :start and :end order by this_.time desc");
			results = query.setParameter("start", LocalDate.of(year, month, 1).atStartOfDay())
					.setParameter("end", LocalDate.of(year, month, lastDay).atTime(23, 59, 59)).getResultList();
			

			
			System.out.println(query);
//			System.out.println(results);
			JSONArray arry = new JSONArray();
			List<Brand> brandList = new ArrayList<>();
			for (ConstructionResult r : results) {
				JSONObject o = new JSONObject();
				o.put("id", r.getId());

//				o.put("title", r.getLocation().getPlan().getWorkOrderInfo().getOrderInfo().getTitle());
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
				List<String> designs = new ArrayList<>();
				for (String s : r.getResultImageUrls()) {
					designs.add(s);
				}
				o.put("designs", designs);
				arry.add(o);
				if (!brandList.contains(r.getLocation().getLocation().getBrand())) {
					brandList.add(r.getLocation().getLocation().getBrand());
				}
			}
			JSONArray brands = new JSONArray();
//			for (Brand brand : brandList) {
//				JSONObject o = new JSONObject();
//				o.put("id", brand.getId());
//				o.put("brand", brand.getCompanyName());
//				brands.add(o);
//			}
			ret.put("data", arry);
//			ret.put("brand", brands);
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
	public JSONObject getApproveList(int userId, int year, int month) {
		JSONObject ret = new JSONObject();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Session session = null;
		try {
			session = factory.openSession();
			List<ConstructionResult> results = null;
			BrandUser user = session.find(BrandUser.class, userId);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, 1);
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			Query query = null;
			query = session.createQuery("Select this_ from ConstructionResult this_ where this_.plan.workOrderInfo.orderInfo.brand.id="
						+ user.getBrand().getId() + " and this_.time between :start and :end order by this_.time desc");
			results = query.setParameter("start", LocalDate.of(year, month, 1).atStartOfDay())
					.setParameter("end", LocalDate.of(year, month, lastDay).atTime(23, 59, 59)).getResultList();
			

			
			System.out.println(query);
//			System.out.println(results);
			JSONArray arry = new JSONArray();
			List<Brand> brandList = new ArrayList<>();
			for (ConstructionResult r : results) {
				JSONObject o = new JSONObject();
				o.put("id", r.getId());

//				o.put("title", r.getLocation().getPlan().getWorkOrderInfo().getOrderInfo().getTitle());
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
				List<String> designs = new ArrayList<>();
				for (String s : r.getResultImageUrls()) {
					designs.add(s);
				}
				o.put("designs", designs);
				arry.add(o);
				if (!brandList.contains(r.getLocation().getLocation().getBrand())) {
					brandList.add(r.getLocation().getLocation().getBrand());
				}
			}
			JSONArray brands = new JSONArray();
//			for (Brand brand : brandList) {
//				JSONObject o = new JSONObject();
//				o.put("id", brand.getId());
//				o.put("brand", brand.getCompanyName());
//				brands.add(o);
//			}
			ret.put("data", arry);
//			ret.put("brand", brands);
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
}
