package com.company.ROMES.Services.StandardInfo;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.LoggerUtil;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.Brand;
import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.dao.ConstructionCompanyDAOInterface;
import com.company.ROMES.interfaces.service.StandardInfo.ConstructionCompanyInterface;
import com.google.gson.JsonObject;

@Service
public class ConstructionCompanyService implements ConstructionCompanyInterface {

	@Autowired
	SessionFactory factory;

	@Autowired
	UserDAO us;

	@Autowired
	ConstructionCompanyDAOInterface dao;

	@Override
	public JSONArray companyInfo() {
		Session session = null;

		JSONArray rets = new JSONArray();
		try {
			session = factory.openSession();
			List<ConstructionCompany> companys = new ArrayList<ConstructionCompany>();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			companys = dao.selectData(session);
			for (ConstructionCompany company : companys) {
				JSONObject ret = new JSONObject();
//				List<User> user= session.createQuery("Select this_ from User this_ where this_.constructionCompany.id =" + company.getId() + "and this_.enabled = true order by this_.id").getResultList();

				ret.put("id", company.getId());
				ret.put("name", company.getCompanyName());
				ret.put("date", company.getRegistDate().format(format));
//				ret.put("user", user == null?"no info":user.get(0).getName());
//				ret.put("phone",user==null?"no info":user.get(0).getTel());
//				ret.put("user", company.getUser());
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
	public JSONObject createCompany(ConstructionCompany com) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ConstructionCompany company = new ConstructionCompany();
			company.setCompanyName(com.getCompanyName());
			company.setRemark(com.getRemark());
			company.setRegistDate(LocalDateTime.now());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			LoggingTool.createLog(session, user, "Manager", "시공사 등록: " + com.getCompanyName());
			LoggerUtil.info("constructionCompany: " + com.getId(), userID, "create");

			session.save(company);
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}

	@Override
	public JSONObject updateCompany(ConstructionCompany com) {
		Session session = null;
		Transaction transaction = null;
		JSONObject ret = new JSONObject();

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			ConstructionCompany company = session.find(ConstructionCompany.class, com.getId());

			if (company.getCompanyName() != com.getCompanyName())
				company.setCompanyName(com.getCompanyName());
			if (company.getRemark() != com.getRemark())
				company.setRemark(com.getRemark());
			
			company.setRegistDate(LocalDateTime.now());

			LoggingTool.createLog(session, user, "Manager", "시공사 업데이트: " + com.getCompanyName());
			LoggerUtil.info("constructionCompany: " + com.getId(), userID, "update");

			session.save(company);
			transaction.commit();

		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return ret;
	}

	@Override
	public ConstructionCompany getConstruct(int id) {
		Session session = null;
		ConstructionCompany ret = null;
		try {
			session = factory.openSession();
			ret = session.find(ConstructionCompany.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			ret = null;
			// TODO: handle exception
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public boolean deleteCompany(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			ConstructionCompany company = new ConstructionCompany();
			company = session.find(ConstructionCompany.class, id);

			company.setDisabled(true);
			session.update(company);

			LoggingTool.createLog(session, user, "Manager", "시공사 삭제: " + company.getCompanyName());
			LoggerUtil.info("constructionCompany: " + id, userID, "delete");

			transaction.commit();
			result = true;

		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return result;
	}

	@Override
	public boolean importExcel(JsonObject datas) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			String con = datas.get("Construction").getAsString();
			String remark = datas.get("Remark") == null ? "No info" : datas.get("Remark").getAsString();

			List<ConstructionCompany> company = session.createQuery("Select this_ from ConstructionCompany this_")
					.getResultList();
			for (ConstructionCompany com : company) {
				if (com.getCompanyName().equals(con)) {
					state = true;
					return state;
				}
			}

			if (state == false) {
				ConstructionCompany com = new ConstructionCompany();
				com.setCompanyName(con);
				com.setRemark(remark);

				session.save(com);
			}

			LoggerUtil.info("constructionCompany", con, "ExcelRegist");

			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}

	public JSONArray getEmployees(int construction) {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<User> user = session.createQuery("select a from User a where constructioncompany_id=" + construction)
					.getResultList();
			for (User c : user) {
				JSONObject ob = new JSONObject();
//				ob.put("id", p.getId());
				ob.put("name", c.getName());
				ob.put("position", c.getPosition());
				ob.put("tel", c.getTel());

				ret.add(ob);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}

		return ret;

	}
}
