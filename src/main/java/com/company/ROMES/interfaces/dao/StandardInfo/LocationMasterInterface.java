package com.company.ROMES.interfaces.dao.StandardInfo;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.entity.LocationMaster;

public interface LocationMasterInterface {
 
	public List<LocationMaster>SelectAllByfalse(Session session);
	public List<CommonCode>SelectLocation(Session session);
	public LocationMaster SelectById(Session session, int id);
	public List<LocationMaster>SelectByLevel0(Session session);
	public boolean createLocation(Session session, LocationMaster locationMaster);
	public boolean updateLocation(Session session, LocationMaster locationMaster);
	public boolean deleteLocation(Session session, int id);
	
}