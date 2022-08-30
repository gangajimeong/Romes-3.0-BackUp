package com.company.ROMES.Services.ProcessM;

import java.io.Console;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
import com.company.ROMES.entity.ConstructionResult;
import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.entity.ErrorProductHistory;
import com.company.ROMES.entity.ProcessMaster;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;
import com.google.gson.JsonArray;

@Service
public class ProductDefectService {
	@Autowired
	SessionFactory factory;
	@Autowired
	UserDAO us;

	public JSONArray getPDList() {
		JSONArray ret = new JSONArray();
		Session session = null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			session = factory.openSession();
			List<ErrorProductHistory> EPdata = null;
			EPdata = session.createQuery("select d from ErrorProductHistory d where d.order != null").getResultList();
			for (ErrorProductHistory ep : EPdata) {
				JSONObject ob = new JSONObject();
				String Time = ep.getTime().format(format);
				ob.put("id", ep.getId());
				ob.put("product", ep.getOrder().getProduct() == null?"No info":ep.getOrder().getProduct());
				ob.put("info", ep.getErrorCode().getErrorType());
				ob.put("line", ep.getOrder().getPrinter() == null? "No info":ep.getOrder().getPrinter());
				ob.put("time", Time);
				ob.put("user", ep.getUser() == null? "정보없음":ep.getUser().getName());

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
	
	public JSONArray getErrorcodeList() {
		Session session = null;
		JSONArray rets = null;

		try {
			rets = new JSONArray();
			session = factory.openSession();
			List<ErrorCode> lists = new ArrayList<ErrorCode>();
			lists = session.createQuery("Select d from ErrorCode d").getResultList();
			for (ErrorCode ec : lists) {
				JSONObject ret = new JSONObject();
				ret.put("id", ec.getId());
				ret.put("type", ec.getErrorType());
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

	public JSONArray selectPDById(int id) {
		Session session = null;
		Transaction transaction = null;
		JSONArray ret = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ret = new JSONArray();
			ErrorProductHistory ep = new ErrorProductHistory();
			ep = session.find(ErrorProductHistory.class, id);
			
			ret.add(ep.getId());
			ret.add(ep.getOrder().getProduct());
			ret.add(ep.getErrorCode().getId());
		} catch (Exception e) {
			e.printStackTrace();
			ret.add(false);
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	public boolean updatePD(ErrorProductHistory errorpd) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ErrorProductHistory ep = new ErrorProductHistory();
			ep = session.find(ErrorProductHistory.class, errorpd.getId());

			if (ep.getType() != errorpd.getType())
				ep.setType(errorpd.getType());
			if (ep.getErrorCode().getId() != errorpd.getErrorCode().getId())
				ep.setErrorCode(errorpd.getErrorCode());
			
			session.update(ep);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "제품불량 업데이트: "+ep.getOrder().getProduct());
			
			transaction.commit();
			state = true;
		} catch (Exception e) {
			// TODO: handle exception
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return state;
	}
	
	public boolean deletePD(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ErrorProductHistory ep = session.find(ErrorProductHistory.class, id);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "제품불량 삭제: "+ep.getLot().getLot());
			
			session.delete(ep);
			transaction.commit();
			state = true;

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			SessionMethod.closeSession(session);
		}
		return state;
	}
	
	public JSONArray getCDList() {
		JSONArray retd = null;
		Session session = null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			retd = new JSONArray();
			session = factory.openSession();
			List<ConstructionResult> CDdata = null;
			CDdata = session.createQuery("select d from ConstructionResult d where d.failed = true").getResultList();
			for (ConstructionResult cd : CDdata) {
				JSONObject ob = new JSONObject();
				String Time = cd.getTime().format(format);
				ob.put("id", cd.getId());
				ob.put("brand", cd.getBrand()==null?"No Info":cd.getBrand().getCompanyName());
				ob.put("location", cd.getLocation().getLocation().getTitle());
				ob.put("time", Time);
				ob.put("remark", cd.getRemark());

				retd.add(ob);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return retd;
	}
	
	public JSONArray selectCDById(int id) {
		Session session = null;
		Transaction transaction = null;
		JSONArray ret = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ret = new JSONArray();
			ConstructionResult cr = new ConstructionResult();
			cr = session.find(ConstructionResult.class, id);
			ret.add(cr.getId());
			ret.add(cr.getBrand()==null?"No Info":cr.getBrand().getCompanyName());
			ret.add(cr.getLocation().getLocation().getTitle());
			ret.add(cr.getRemark());
		} catch (Exception e) {
			e.printStackTrace();
			ret.add(false);
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}
	
	public boolean updateCD(ConstructionResult cResult) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ConstructionResult cr = new ConstructionResult();
			cr = session.find(ConstructionResult.class, cResult.getId());

			if (cr.getRemark() != cResult.getRemark())
				cr.setRemark(cResult.getRemark());
			
			session.update(cr);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "시공불량 업데이트: "+cr.getLocation().getLocation().getTitle());
			
			transaction.commit();
			state = true;
		} catch (Exception e) {
			// TODO: handle exception
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return state;
	}
	
	public boolean deleteCD(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ConstructionResult cr = session.find(ConstructionResult.class, id);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "시공불량 삭제: "+cr.getLocation().getLocation().getTitle());
			
			session.delete(cr);
			transaction.commit();
			state = true;

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				if (transaction.isActive())
					transaction.rollback();
		} finally {
			SessionMethod.closeSession(session);
		}
		return state;
	}
	

}
