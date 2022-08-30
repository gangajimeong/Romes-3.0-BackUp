package com.company.ROMES.interfaces.dao;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.LocationMaster;

public interface LocationDAOInterface {
	public List<LocationMaster> selectAll(Session session);
	public boolean saveLocation(Session session,LocationMaster m);
	public boolean updateLocation(Session session,LocationMaster m);
	public boolean deleteLocation(Session session,LocationMaster m);
	public LocationMaster selectTopLocation(Session session);
}
