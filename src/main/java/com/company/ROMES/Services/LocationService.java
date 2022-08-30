package com.company.ROMES.Services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ROMES.dao.LocationMasterDAO;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.interfaces.service.LocationServiceInterface;

@Service
public class LocationService implements LocationServiceInterface{

	@Autowired
	SessionFactory factory;
	@Autowired
	LocationMasterDAO locationDao;
	
	@Override
	public List<LocationMaster> getAllLocation() {
		Session session = null;
		List<LocationMaster> ret = null;
		try {
			session = factory.openSession();
			//ret = locationDao.selectAll(session);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public LocationMaster getTopLocation() {
		Session session = null;
		LocationMaster ret = null;
		try {
			session = factory.openSession();
			//ret = locationDao.selectTopLocation(session);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null)
				if(session.isOpen())
					session.close();
		}
		return ret;
	}

	@Override
	public List<LocationMaster> getTopLocation(Session session) {
		List<LocationMaster> m = session.createQuery("Select this_ from LocationMaster this_ where this_.parentLocation is null").getResultList();
		return m;
	}

//	@Override
////	public LocationMaster getTopLocation(Session session) {
//		//return locationDao.selectTopLocation(session);
//	}
	
}
