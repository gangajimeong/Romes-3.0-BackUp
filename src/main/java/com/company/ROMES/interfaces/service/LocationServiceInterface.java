package com.company.ROMES.interfaces.service;

import java.util.List;

import org.hibernate.Session;

import com.company.ROMES.entity.LocationMaster;

public interface LocationServiceInterface {
	public List<LocationMaster> getAllLocation();
	public LocationMaster getTopLocation();
	public List<LocationMaster> getTopLocation(Session session);
}
