package com.company.ROMES.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.interfaces.dao.StandardInfo.LocationMasterInterface;

@Repository
public class LocationMasterDAO implements LocationMasterInterface {

	@Override
	public List<LocationMaster> SelectAllByfalse(Session session) {
		return session.createQuery("Select this_ from LocationMaster this_ where this_.disabeld = false Order By this_.id ")
				.getResultList();
	}

	@Override
	public List<CommonCode> SelectLocation(Session session) {
		return session.createQuery("Select this_ from LocationMaster this_ ").getResultList();
	}

	@Override
	public boolean createLocation(Session session, LocationMaster locationMaster) {
		boolean state = false;
		try {
			session.save(locationMaster);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public boolean updateLocation(Session session, LocationMaster locationMaster) {
		boolean state = false;
		try {
			state = true;
			session.update(locationMaster);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return state;
	}

	@Override
	public boolean deleteLocation(Session session, int id) {
		boolean state = false;
		LocationMaster lm = new LocationMaster();
		LocationMaster parents = new LocationMaster();
		try {
			state = true;
			lm = session.find(LocationMaster.class, id);
			parents = lm.getParentLocation();
			while(parents != null) {
			parents.getSubLocations().remove(lm);
			parents = parents.getParentLocation();
			}
			lm.setDisabeld(true);
			session.update(lm);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return state;
	}

	@Override
	public LocationMaster SelectById(Session session, int id) {
		return (LocationMaster) session.createQuery("Select this_ from LocationMaster this_ where this_.disabeld= false and this_.id="+id+" Order By this_.id").getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationMaster> SelectByLevel0(Session session) {
		return session.createQuery("Select this_ from LocationMaster this_ where this_.disabeld = false and this_.level =0 Order By this_.id ")
				.getResultList();
	}

	/*
	 * @Override public List<LocationMaster> selectAll(Session session) { // TODO
	 * Auto-generated method stub return
	 * session.createCriteria(LocationMaster.class).list(); }
	 * 
	 * @Override public boolean saveLocation(Session session, LocationMaster m) { //
	 * TODO Auto-generated method stub try { session.save(m); return true; } catch
	 * (Exception e) { // TODO: handle exception e.printStackTrace(); return false;
	 * } }
	 * 
	 * @Override public boolean updateLocation(Session session, LocationMaster m) {
	 * // TODO Auto-generated method stub try { session.update(m); return true; }
	 * catch (Exception e) { // TODO: handle exception e.printStackTrace(); return
	 * false; } }
	 * 
	 * @Override public boolean deleteLocation(Session session, LocationMaster m) {
	 * // TODO Auto-generated method stub try { session.delete(m); return true; }
	 * catch (Exception e) { // TODO: handle exception e.printStackTrace(); return
	 * false; } }
	 * 
	 * @Override public LocationMaster selectTopLocation(Session session) {
	 * LocationMaster ret = null; List<LocationMaster> ms = selectAll(session); for
	 * (LocationMaster m : ms) { if (m.getParentLocation() == null) { ret = m;
	 * break; } } return ret; }
	 */
}
