package com.company.ROMES.interfaces.dao.StandardInfo;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.entity.WorkingLine;

public interface WorkingLineDAOInterface {

	 public List<WorkingLine>SelectAll(Session session);
	 public List<WorkingLine>SelectAllByFalse(Session session);
	 public WorkingLine SelectWorkingLine(Session session, int id);
	 public boolean createWorkingLine(Session session, WorkingLine work);
	 public boolean updateWorkingLine(Session session, WorkingLine work);
	 public boolean deleteWorkingLine(Session session, int id);
	 public List<LocationMaster>workingLineForLocation(Session session);
	 public LocationMaster selectLocation(Session session, int id);
	 
	 
}
