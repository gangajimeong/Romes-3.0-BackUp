package com.company.ROMES.Services.StandardInfo;

import java.time.LocalDateTime;
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
import com.company.ROMES.entity.ProcessMaster;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkReport;
import com.company.ROMES.functions.LoggingTool;
import com.company.ROMES.functions.SessionMethod;

@Service
public class ProcessMasterService {

	@Autowired
	SessionFactory factory;
	@Autowired
	UserDAO us;

	public JSONArray getProcessList() {
		JSONArray ret = new JSONArray();
		Session session = null;
		try {
			session = factory.openSession();
			List<ProcessMaster> PMdata = null;
			PMdata = session.createQuery("Select d from ProcessMaster d order by d.code").getResultList();
			for (ProcessMaster PM : PMdata) {
				JSONObject ob = new JSONObject();
				ob.put("id", PM.getId());
				ob.put("code", PM.getCode());
				ob.put("name", PM.getProcessName());
				ob.put("before", PM.getBeforeProcess() == null ? "" : PM.getBeforeProcess().getProcessName());
				ob.put("after", PM.getAfterProcess() == null ? "" : PM.getAfterProcess().getProcessName());
				ob.put("detail", PM.getDetailProcess() == null ? "" : PM.getDetailProcess());

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

	public void createProcess(ProcessMaster process) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			
			
			if(process.getAfterProcess().getId() == 0)
				process.setAfterProcess(null);
			
			if(process.getBeforeProcess().getId() == 0)
				process.setBeforeProcess(null);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "공정 등록: "+process.getProcessName());
			
			session.save(process);
			transaction.commit();

		} catch (Exception e) {
			// TODO: handle exception
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
	}

	public JSONArray selectProcessById(int id) {
		Session session = null;
		Transaction transaction = null;
		JSONArray ret = null;
		try {

			session = factory.openSession();
			transaction = session.beginTransaction();
			ret = new JSONArray();
			ProcessMaster pm = new ProcessMaster();
			pm = session.find(ProcessMaster.class, id);
			ret.add(pm.getId());
			ret.add(pm.getCode());
			ret.add(pm.getProcessName());
			ret.add(pm.getBeforeProcess() == null ? "0" : pm.getBeforeProcess().getId());
			ret.add(pm.getAfterProcess() == null ? "0" : pm.getAfterProcess().getId());
			ret.add(pm.getDetailProcess() == null ? "0" : pm.getDetailProcess());

		} catch (Exception e) {
			e.printStackTrace();
			ret.add(false);
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	public boolean updateProcess(ProcessMaster process) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ProcessMaster pm = new ProcessMaster();
			pm = session.find(ProcessMaster.class, process.getId());

			if(process.getAfterProcess().getId() == 0)
				process.setAfterProcess(null);
			
			if(process.getBeforeProcess().getId() == 0)
				process.setBeforeProcess(null);
			
			if (pm.getCode() != process.getCode())
				pm.setCode(process.getCode());
			if (pm.getProcessName() != process.getProcessName())
				pm.setProcessName(process.getProcessName());
			if (pm.getBeforeProcess() != process.getBeforeProcess())
				pm.setBeforeProcess(process.getBeforeProcess());
			if (pm.getAfterProcess() != process.getAfterProcess())
				pm.setAfterProcess(process.getAfterProcess());
			if (pm.getDetailProcess() != process.getDetailProcess())
				pm.setDetailProcess(process.getDetailProcess());

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "공정 업데이트: "+process.getProcessName());
			
			session.update(pm);
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

	public boolean deleteProcess(int id) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			ProcessMaster pm = (ProcessMaster) session.find(ProcessMaster.class, id);
			 
			List<ProcessMaster> children = new ArrayList<ProcessMaster>();
			children = findBeforeProcess(session, id);
			if(children != null) {
				for(ProcessMaster child : children) {
					child.setBeforeProcess(null);
					
					session.update(child);
				}
			}
			
			children = findAfterProcess(session, id);
			if(children != null) {
				for(ProcessMaster child : children) {
					child.setAfterProcess(null);
					
					session.update(child);
				}
			}
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();

			User user = new User();
			if (!userID.equals("anonymousUser")) {
				user = us.selectByIdForUser(session, userID);
			} else {
				user = null;
			}
			
			LoggingTool.createLog(session, user, "Manager", "공정 삭제: "+pm.getProcessName());
			
			session.delete(pm);
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
	
	public List<ProcessMaster> findBeforeProcess(Session session, int id) {
		List<ProcessMaster> PM = new ArrayList<ProcessMaster>();
		
		PM = session.createQuery("Select this_ from ProcessMaster this_ where this_.beforeProcess = " + id).getResultList();
		if(PM == null) {
			return null;
		}
		
		return PM;
	}
	
	public List<ProcessMaster> findAfterProcess(Session session, int id) {
		List<ProcessMaster> PM = new ArrayList<ProcessMaster>();
		
		PM = session.createQuery("Select this_ from ProcessMaster this_ where this_.afterProcess = " + id).getResultList();
		if(PM == null) {
			return null;
		}
		
		return PM;
	}
}
