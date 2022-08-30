package com.company.ROMES.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.interfaces.dao.StandardInfo.WorkingLineDAOInterface;

@Repository
public class WorkingLineDAO implements WorkingLineDAOInterface {

	@Override
	public List<WorkingLine> SelectAll(Session session) {
		return session.createQuery("Select this_ from WorkingLine this_").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkingLine> SelectAllByFalse(Session session) {
		return session.createQuery("Select this_ from WorkingLine this_ where this_.isDisabled = false Order By this_.id").getResultList();
	}

	@Override
	public WorkingLine SelectWorkingLine(Session session, int id) {
		return session.find(WorkingLine.class,id);
	}

	@Override
	public boolean createWorkingLine(Session session, WorkingLine work) {
		boolean state = false;
		try {
			session.save(work);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return state;
	}

	@Override
	public boolean updateWorkingLine(Session session, WorkingLine work) {
		boolean state = false;
		try {
			session.update(work);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return state;
	}

	@Override
	public boolean deleteWorkingLine(Session session, int id) {
		boolean state = false;
		WorkingLine workingline = null;
		try {
			workingline = session.find(WorkingLine.class, id);
			workingline.setDisabled(true);
			session.update(workingline);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return state;
	}
	// 
	@Override
	public List<LocationMaster> workingLineForLocation(Session session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocationMaster selectLocation(Session session, int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
