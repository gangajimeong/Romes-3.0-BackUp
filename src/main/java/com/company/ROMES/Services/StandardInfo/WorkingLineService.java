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
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.ProcessMaster;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.interfaces.dao.StandardInfo.WorkingLineDAOInterface;
import com.company.ROMES.interfaces.service.StandardInfo.WorkingLineServiceInterface;
import com.google.protobuf.DescriptorProtos.SourceCodeInfo.Location;

import Error_code.ResultCode;

import com.company.ROMES.functions.*;

@Service
public class WorkingLineService implements WorkingLineServiceInterface {
	@Autowired
	WorkingLineDAOInterface wd;
	@Autowired
	SessionFactory factory;
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	UserDAO us;

	@Override
	public List<WorkingLine> SelectAll() {
		List<WorkingLine> lists = null;
		Session session = null;

		try {
			session = factory.openSession();
			lists = wd.SelectAll(session);
		} catch (Exception e) {
			for (StackTraceElement se : e.getStackTrace()) {
				logger.error(se);
			}
		}
		return lists;
	}

	@Override
	public List<WorkingLine> SelectAllByFalse() {
		List<WorkingLine> lists = null;
		Session session = null;

		try {
			session = factory.openSession();
			lists = wd.SelectAllByFalse(session);
		} catch (Exception e) {
			for (StackTraceElement se : e.getStackTrace()) {
				logger.error(se);
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return lists;
	}

	@Override
	public WorkingLine SelectById(int id) {
		WorkingLine workingline = null;
		Session session = null;

		try {
			session = factory.openSession();
			workingline = wd.SelectWorkingLine(session, id);
		} catch (Exception e) {
			for (StackTraceElement se : e.getStackTrace()) {
				logger.error(se);
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return workingline;
	}

	@Override
	public boolean createWorkingLine(WorkingLine workingLine) {
		boolean state = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			state = wd.createWorkingLine(session, workingLine);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			for (StackTraceElement se : e.getStackTrace()) {
				logger.error(se);
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public boolean updateWorkingLine(WorkingLine workingLine) {
		boolean state = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			state = wd.updateWorkingLine(session, workingLine);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			for (StackTraceElement se : e.getStackTrace()) {
				logger.error(se);
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public boolean deleteWorkingLine(int id) {
		boolean state = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			state = wd.deleteWorkingLine(session, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			for (StackTraceElement se : e.getStackTrace()) {
				logger.error(se);
			}
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return state;
	}

	@Override
	public JSONArray getLocation() {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<LocationMaster> locate = session.createQuery("Select this_ from LocationMaster this_").getResultList();

			for (LocationMaster lo : locate) {
				JSONObject ob = new JSONObject();
				ob.put("id", lo.getId());
				ob.put("name", lo.getName());

				ret.add(ob);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public JSONArray getWorkingLine() {
		Session session = null;
		JSONArray ret = new JSONArray();
		try {
			session = factory.openSession();
			List<WorkingLine> line = session
					.createQuery("Select this_ from WorkingLine this_ where this_.isDisabled = false").getResultList();

			for (WorkingLine lo : line) {
				JSONObject ob = new JSONObject();
				ob.put("id", lo.getId());
				ob.put("type", lo.getType());
				ob.put("line", lo.getLine());
				ob.put("remark", lo.getRemarks());
				ob.put("workload", lo.getWorkload());
				ob.put("mRequirement", lo.getMRequirement());
				ob.put("locate", lo.getLocation() == null ? "" : lo.getLocation().getName());
				ob.put("locaId", lo.getLocation() == null ? "" : lo.getLocation().getId());
				ret.add(ob);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				if (session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public boolean updateWorkingLine(List list) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			int LineId = Integer.parseInt(list.get(1).toString());
			int LocationId = Integer.parseInt(list.get(6).toString());

			WorkingLine line = session.find(WorkingLine.class, LineId);
			LocationMaster location = session.find(LocationMaster.class, LocationId);
			line.setLine(list.get(2).toString());
			line.setRemarks(list.get(3).toString());
			line.setWorkload(Integer.parseInt(list.get(4).toString()));
			line.setMRequirement(Integer.parseInt(list.get(5).toString()));
			line.setLocation(location);

			session.update(line);
			transaction.commit();
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return true;
	}

	public void createWL(WorkingLine wl) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			LoggingTool.createLog(session, user, "Manager", "생산라인 등록: " + wl.getLine());
			LoggerUtil.info("workingLine", userID, "create");

			session.save(wl);
			transaction.commit();
	
		} catch (NoResultException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
	}

	public JSONArray selectWLById(int id) {
		Session session = null;
		Transaction transaction = null;
		JSONArray ret = null;
		try {

			session = factory.openSession();
			transaction = session.beginTransaction();
			ret = new JSONArray();
			WorkingLine wl = new WorkingLine();
			wl = session.find(WorkingLine.class, id);

			ret.add(wl.getId());
			ret.add(wl.getType());
			ret.add(wl.getLine());
			ret.add(wl.getRemarks());
			ret.add(wl.getWorkload());
			ret.add(wl.getMRequirement());
			ret.add(wl.getLocation() == null ? "" : wl.getLocation().getId());
			ret.add(wl.getLocation() == null ? "" : wl.getLocation().getName());

		} catch (Exception e) {
			e.printStackTrace();
			ret.add(false);
		} finally {
			SessionMethod.closeSession(session);
		}
		return ret;
	}

	public boolean updateWL(WorkingLine workingl) {
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			WorkingLine wl = new WorkingLine();
			wl = session.find(WorkingLine.class, workingl.getId());

			if (wl.getType() != workingl.getType())
				wl.setType(workingl.getType());
			if (wl.getLine() != workingl.getLine())
				wl.setLine(workingl.getLine());
			if (wl.getRemarks() != workingl.getRemarks())
				wl.setRemarks(workingl.getRemarks());
			if (wl.getWorkload() != workingl.getWorkload())
				wl.setWorkload(workingl.getWorkload());
			if (wl.getMRequirement() != workingl.getMRequirement())
				wl.setMRequirement(workingl.getMRequirement());
			if (wl.getLocation().getName() != workingl.getLocation().getName())
				wl.setLocation(workingl.getLocation());

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			LoggingTool.createLog(session, user, "Manager", "생산라인 업데이트: " + wl.getLine());
			LoggerUtil.info("workingLine", userID, "update");

			session.update(wl);
			transaction.commit();
			state = true;
		} catch (NoResultException e) {
			// TODO: handle exception
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

	public JSONObject deleteWL(int id) {
		JSONObject ob = new JSONObject();
		Session session = null;
		Transaction transaction = null;
		boolean state = false;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			WorkingLine wl = (WorkingLine) session.find(WorkingLine.class, id);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User user = new User();
			user = us.findAdmin(session, userID);

			LoggingTool.createLog(session, user, "Manager", "생산라인 삭제: " + wl.getLine());
			LoggerUtil.info("workingLine", userID, "delete");

			session.delete(wl);
			transaction.commit();
			state = true;
			ob.put("result", ResultCode.SUCCESS);

		} catch (NoResultException e) {
			ob.put("result", ResultCode.AUTHORITY_ERROR);
			// TODO: handle exception
		} catch (Exception e) {
			ob.put("reuslt", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return ob;
	}
}
