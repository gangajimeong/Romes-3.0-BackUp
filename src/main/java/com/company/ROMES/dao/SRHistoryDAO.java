package com.company.ROMES.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.SRHistory;
import com.company.ROMES.interfaces.dao.SRHistoryDAOInterface;

@Repository
public class SRHistoryDAO implements SRHistoryDAOInterface {

	@Override
	public boolean saveSRHistory(Session session, SRHistory history) {
		try {
			session.save(history);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateSRHistory(Session session, SRHistory history) {
		try {
			session.update(history);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteSRHistory(Session session, SRHistory history) {
		try {
			session.update(history);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<SRHistory> selectAll(Session session) {
		// TODO Auto-generated method stub
		return session.createCriteria(SRHistory.class).addOrder(Order.asc("id")).list();
	}

	@Override
	public List<SRHistory> selectByPerid(Session session, LocalDate startdate, LocalDate endDate) {
		List<SRHistory> ret = null;
		try {
			ret = session.createQuery("Select this_ from SRHistory this_ where this_.time between :start and :end")
					.setParameter("start", startdate.atTime(0, 0, 0))
					.setParameter("end", endDate.atTime(23, 59,59))
					.getResultList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public List<SRHistory> selectByPerid(Session session, LocalDate date) {
		List<SRHistory> ret = null;
		try {
			ret = session.createQuery("Select this_ from SRHistory this_ where this_.time between :start and :end")
					.setParameter("start", date.atTime(0, 0, 0))
					.setParameter("end", date.atTime(23, 59,59))
					.getResultList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
