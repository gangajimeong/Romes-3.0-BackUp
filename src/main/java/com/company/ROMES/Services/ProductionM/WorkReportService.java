package com.company.ROMES.Services.ProductionM;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkReport;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;

@Service
public class WorkReportService {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	SessionFactory factory;

	@Autowired
	UserDAO us;

	public JSONArray getWorkReport() {
		JSONArray ret = new JSONArray();
		Session session = null;

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
			List<WorkReport> report = new ArrayList<>();
			if (user != null) {
				if (user.getAuthority().getAuthority().equals("ROLE_ADMIN")) {
					report = session.createQuery("Select this_ from WorkReport this_").getResultList();
				} else
					report = session
							.createQuery("Select this_ from WorkReport this_ where this_.user = " + user.getId() + "")
							.getResultList();
			} else {
				report = session.createQuery("Select this_ from WorkReport this_ where this_.user = " + null + "")
						.getResultList();
			}
			for (WorkReport reports : report) {
				JSONObject ob = new JSONObject();
				ob.put("id", reports.getId());
				ob.put("report", reports.getReport());
				ob.put("time", reports.getTime());
				ob.put("title", reports.getTitle());
				ob.put("name", reports.getUser() == null ? "정보 없음" : reports.getUser().getName());
				ob.put("type", reports.getRepoType());
				ret.add(ob);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	public boolean createReportWork(WorkReport report) {
		Session session = null;
		boolean state = false;
		Transaction transaction = null;
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
			
			report.setUser(user);
			LoggingTool.createLog(session, user, "Manager", "작업보고서 등록 :"+report.getRepoType());
			session.save(report);	
			state = true;
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste);
			}
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}

	public WorkReport getWorkReport(int id) {
		Session session = null;
		WorkReport ret = null;
		try {
			session = factory.openSession();
			ret = session.find(WorkReport.class, id);
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

	public JSONArray selectWorkReportById(int id) {
		Session session = null;
		Transaction transaction = null;
		JSONArray ret = null;
		try {

			session = factory.openSession();
			transaction = session.beginTransaction();
			ret = new JSONArray();
			WorkReport report = new WorkReport();
			report = session.find(WorkReport.class, id);
			ret.add(report.getId());
			ret.add(report.getRepoType());
			ret.add(report.getTitle());
			ret.add(report.getReport());
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

	public JSONArray selectWorkReportByIdForShow(int id) {
		Session session = null;
		Transaction transaction = null;
		JSONArray ret = null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {

			session = factory.openSession();
			transaction = session.beginTransaction();
			ret = new JSONArray();
			WorkReport report = new WorkReport();
			report = session.find(WorkReport.class, id);
			ret.add(report.getId());
			ret.add(report.getRepoType());
			ret.add(report.getTime().format(format));
			ret.add(report.getUser().getName());
			ret.add(report.getTitle());
			ret.add(report.getReport());
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
	
	public boolean updateWorkReport(WorkReport report) {
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
			WorkReport wr = new WorkReport();
			wr = session.find(WorkReport.class, report.getId());

			if (wr.getRepoType() != report.getRepoType())
				wr.setRepoType(report.getRepoType());
			if (wr.getTitle() != report.getTitle())
				wr.setTitle(report.getTitle());
			if (wr.getReport() != report.getReport())
				wr.setReport(report.getReport());
			wr.setTime(LocalDateTime.now());

			LoggingTool.createLog(session, user, "Manager", "작업보고서 수정");
			session.update(wr);
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

	public boolean deleteWorkReport(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			WorkReport report = session.find(WorkReport.class, id);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "작업보고서 삭제: "+report.getTitle());
			

			session.delete(report);
			transaction.commit();
			state = true;

		} catch (Exception e) {
			e.printStackTrace();
			;
		} finally {
			SessionMethod.closeSession(session, transaction);
		}
		return state;
	}
}
