package com.company.ROMES.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;
import com.company.ROMES.interfaces.dao.StandardInfo.DivisionDAOInterface;
@Repository
public class DivisionDAO implements DivisionDAOInterface {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Division> SelectAll(Session session) {
		return session.createQuery("Select this_ from Division this_").getResultList();
	}

	@Override
	public List<Division> SelectAllByFalse(Session session) {
		return session.createQuery("Select this_ from Division this_ where this_.disabled=false Order By this_.id").getResultList();
	}

	@Override
	public List<User> SelectEmployee(Session session, int DivId) {
		return session.createQuery("Select this_ from User this_ where this_.division.id="+DivId+" and this_.isLock=false").getResultList();
	}

	@Override
	public Division SelectDivision(Session session,int id) {
		return session.find(Division.class,id);
	}

	@Override
	public boolean createDivision(Session session, Division division) {
		boolean state = false;
		try {
			session.save(division);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public boolean updateDivision(Session session, Division division) {
		boolean state = false;
		try {
			session.update(division);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public boolean deleteDivision(Session session, int id) {
		boolean state = false;
		Division division = new Division();
		try {
			division = session.find(Division.class,id);
			division.setDisabled(true);
			session.update(division);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

}
