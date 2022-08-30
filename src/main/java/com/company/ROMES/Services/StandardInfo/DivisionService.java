package com.company.ROMES.Services.StandardInfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.ROMES.LoggerUtil;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.company.ROMES.interfaces.dao.StandardInfo.DivisionDAOInterface;
import com.company.ROMES.interfaces.service.StandardInfo.DivisionServiceInterface;
import com.google.gson.JsonArray;

@Service
public class DivisionService implements DivisionServiceInterface {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	DivisionDAOInterface DD;
	@Autowired
	SessionFactory factory;
	@Autowired
	UserDAO us;

	@Override
	public List<Division> SelectAll() {
		Session session = null;
		List<Division> lists = new ArrayList<Division>();
		try {
			session = factory.openSession();
			lists = DD.SelectAll(session);
		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste);
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return lists;
	}

	@Override
	public List<Division> SelectAllByFalse() {
		Session session = null;
		List<Division> lists = new ArrayList<Division>();
		try {
			session = factory.openSession();
			lists = DD.SelectAllByFalse(session);
		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste);
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return lists;
	}

	@Override
	public List<User> SelectEmployee(int DivId) {
		Session session = null;
		boolean state = false;
		List<User> users = new ArrayList<User>();
		try {
			session = factory.openSession();
			users = DD.SelectEmployee(session, DivId);
			state = true;
		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste);
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return users;
	}

	@Override
	public Division SelectDivision(int id) {
		Session session = null;
		Division division = new Division();
		try {
			session = factory.openSession();
			division = DD.SelectDivision(session, id);

		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste);
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return division;
	}

	@Override
	public boolean createDivision(Division division) {
		Session session = null;
		boolean state = false;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			if (division.getDivision().contains("시공")) {
				division.setConstruction(true);
			}
			state = DD.createDivision(session, division);

			LoggingTool.createLog(session, user, "Manager", "부서등록: " + division.getDivision());
			LoggerUtil.info("division: " + division.getId(), userID, "create");
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste);
			}
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}

	@Override
	public boolean updateDivision(Division division) {
		Session session = null;
		boolean state = false;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			state = DD.updateDivision(session, division);

			LoggingTool.createLog(session, user, "Manager", "부서 업데이트:" + division.getDivision());
			LoggerUtil.info("division: " + division.getId(), userID, "update");
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste);
			}
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}

	@Override
	public boolean deleteDivision(int id) {
		Session session = null;
		boolean state = false;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			Division dv = session.find(Division.class, id);
			state = DD.deleteDivision(session, id);

			LoggingTool.createLog(session, user, "Manager", "부서 삭제: " + dv.getDivision());
			LoggerUtil.info("division: " + id, userID, "delete");
			transaction.commit();
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste);
			}
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}

	@Override
	public JSONArray getEmployee(int division) {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<User> user = session.createQuery("select a from User a where a.division.id=" + division)
					.getResultList();
			for (User p : user) {
				JSONObject ob = new JSONObject();
//				ob.put("id", p.getId());
				ob.put("name", p.getName());
				ob.put("position", p.getPosition());
				ob.put("tel", p.getTel());

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
