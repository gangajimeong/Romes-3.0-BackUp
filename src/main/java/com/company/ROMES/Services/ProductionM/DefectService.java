package com.company.ROMES.Services.ProductionM;

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
import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.entity.ErrorProductHistory;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;

@Service
public class DefectService {
	@Autowired
	SessionFactory factory;
	@Autowired
	UserDAO us;
	
	public JSONArray ProductError() {
		Session session = null;
		JSONArray ret = new JSONArray();
		
		try {
			session = factory.openSession();
			List<ErrorProductHistory> EProduct = session.createQuery("Select this_ from ErrorProductHistory this_ order by this_.id").getResultList();
			for(ErrorProductHistory product : EProduct) {
				JSONObject ob = new JSONObject();
				ob.put("id", product.getId());
				ob.put("lineId", product.getOrder()==null?"":product.getOrder().getPrinter().getId());
				ob.put("line", product.getOrder()==null?"정보없음":product.getOrder().getPrinter() == null?"정보 없음":product.getOrder().getPrinter().getLine());
				ob.put("errorId", product.getErrorCode().getId());
				ob.put("errorType", product.getErrorCode().getErrorType());
				ob.put("errorCount", product.getErrorCount());
				ob.put("user", product.getUser() == null? "정보 없음":product.getUser().getName());
				ob.put("time", product.getTime());
				ob.put("lot", product.getLot() == null?"Lot정보 없음":product.getLot().getLot());
				
				ret.add(ob);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}
	public JSONArray getErrorCode() {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<ErrorCode> error = session.createQuery("Select this_ from ErrorCode this_").getResultList();
			for(ErrorCode ec : error) {
				JSONObject ob = new JSONObject();
				ob.put("id", ec.getId());
				ob.put("type",ec.getErrorType());
				
				ret.add(ob);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	public JSONArray getLine() {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<WorkingLine> wl = session.createQuery("Select this_ from WorkingLine this_ order by line").getResultList();
			for(WorkingLine wo : wl) {
				JSONObject ob = new JSONObject();
				ob.put("id", wo.getId());
				ob.put("line",wo.getLine());
				
				ret.add(ob);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}
	
	public JSONArray selectEDById(int id) {
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
			ret.add(ep.getOrder().getPrinter().getId());
			ret.add(ep.getErrorCode().getId());
			ret.add(ep.getErrorCode().getErrorType());
			

		} catch (Exception e) {
			e.printStackTrace();
			ret.add(false);
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	public boolean updateED(ErrorProductHistory eph) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ErrorProductHistory wl = new ErrorProductHistory();
			wl = session.find(ErrorProductHistory.class, eph.getId());
			
			if (wl.getOrder().getPrinter().getId() != eph.getOrder().getPrinter().getId())
				wl.getOrder().setPrinter(eph.getOrder().getPrinter());
			if (wl.getErrorCode().getErrorType() != eph.getErrorCode().getErrorType())
				wl.setErrorCode(eph.getErrorCode());

			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "설비별불량 업데이트");
			
			session.update(wl);
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

	public boolean deleteED(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ErrorProductHistory ep = (ErrorProductHistory) session.find(ErrorProductHistory.class, id);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "설비별불량 삭제: "+ep.getOrder().getPrinter().getLine());
			 
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
}
