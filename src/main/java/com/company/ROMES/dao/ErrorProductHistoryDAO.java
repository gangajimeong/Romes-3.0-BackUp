package com.company.ROMES.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.ErrorProductHistory;
import com.company.ROMES.interfaces.dao.ErrorProductHistoryDAOInterface;

@Repository
public class ErrorProductHistoryDAO implements ErrorProductHistoryDAOInterface {

	@Override
	public List<ErrorProductHistory> selectAllErrorHistory(Session session) {
		return session.createCriteria(ErrorProductHistory.class).addOrder(Order.asc("id")).list();
	}

	@Override
	public List<ErrorProductHistory> selectErrorHistoryByPeriod(Session session, LocalDate date) {
		return session.createQuery("Select this_ from ErrorProductHistory this_ where this_.time between :start and :end")
				.setParameter("start", date.atTime(0,0,0))
				.setParameter("end", date.atTime(23, 59, 59))
				.getResultList();
	}

	@Override
	public List<ErrorProductHistory> selectErrorHistoryByPeriod(Session session, LocalDate date, LocalDate date2) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_ from ErrorProductHistory this_ where this_.time between :start and :end")
				.setParameter("start", date.atTime(0,0,0))
				.setParameter("end", date2.atTime(23, 59, 59))
				.getResultList();
	}

	@Override
	public ErrorProductHistory selectErrorHistory(Session session, int id) {
		// TODO Auto-generated method stub
		return session.find(ErrorProductHistory.class, id);
	}

	@Override
	public boolean saveErrorHistory(Session session, ErrorProductHistory history) {
		try {
			session.save(history);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
	}

	@Override
	public boolean updateErrorHistory(Session session, ErrorProductHistory history) {
		try {
			session.update(history);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
	}

	@Override
	public boolean deleteErrorHistory(Session session, ErrorProductHistory history) {
		try {
			session.delete(history);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
	}

}
