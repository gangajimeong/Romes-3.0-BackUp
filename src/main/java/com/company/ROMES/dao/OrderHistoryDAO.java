package com.company.ROMES.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.ROMES.entity.OrderHistory;
import com.company.ROMES.interfaces.dao.OrderHistoryDAOInterface;


@Repository
public class OrderHistoryDAO implements OrderHistoryDAOInterface{
	@Autowired
	SessionFactory factory;
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderHistory> selectAll(Session session) {
		return session.createQuery("select this_ from OrderHistory this_ where this_.disabled= false order by this_.registerDate").getResultList();
	}

	

	@Override
	public OrderHistory selectById(Session session, int pk) {
		return session.find(OrderHistory.class, pk);
	}

	@Override
	public List<OrderHistory> selectByRegistedDate(Session session, LocalDate startDate, LocalDate endDate) {
		return session.createQuery("Select this_ from OrderHistory this_ where this_.disabled= false and (this_.orderDate between :start and :end) order by this_.registerDate")
					.setParameter("start", startDate)
					.setParameter("end", endDate)
					.getResultList();
	}



	@Override
	public List<OrderHistory> selectByRegistedDate(Session session, LocalDate date) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_ from OrderHistory this_ where this_.disabled= false and this_.orderDate=:start order by this_.registerDate")
				.setParameter("start", date)
				.getResultList();
	}



	@Override
	public List<OrderHistory> selectByArriveDate(Session session, LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return  session.createQuery("Select this_ from OrderHistory this_ where this_.disabled= false and  (this_.plannedArriveDate between :start and :end) order by this_.registerDate")
				.setParameter("start", startDate)
				.setParameter("end", endDate)
				.getResultList();
	}



	@Override
	public List<OrderHistory> selectByArriveDate(Session session, LocalDate date) {
		// TODO Auto-generated method stub
		return session.createQuery("Select this_ from OrderHistory this_ where (this_.plannedArriveDate=:start or this_.isComplete="+false +") order by this_.registerDate")
				.setParameter("start", date == null?LocalDate.now():date)
				.getResultList();
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<OrderHistory> selectByisComplete(Session session, boolean state) {
		return session.createQuery("Select this_ from OrderHistory this_ where this_.disabled= false and this_.isComplete="+state+"order by this_.registerDate").getResultList();
	}


	@Override
	public boolean createOrderHistory(Session session, OrderHistory orderHistory) {
		boolean state = false;
		try {
			 session.save(orderHistory);
			 state = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}



	@Override
	public boolean updateOrderHistory(Session session, OrderHistory orderHistory) {
		boolean state = false;
		try {
			session.update(orderHistory);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}



	@Override
	public boolean deleteOrderHistory(Session session, int id) {
		OrderHistory orderHistory = new OrderHistory();
		boolean state = false;
		try {
			orderHistory= session.find(OrderHistory.class, id);
			session.delete(orderHistory);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> selectDoc(Session session) {
		return session.createSQLQuery("select distinct on(this_.documentNumber)this_.documentNumber,this_.DocTitle from order_history this_ where this_.disabled= false "+" order by this_.documentNumber").getResultList();
	}



	@Override
	public List<OrderHistory> selectByDocNum(Session session, String DocNum) {
		List<OrderHistory>lists = new ArrayList<OrderHistory>();
		try {
			Query q = session.createQuery("Select this_ from OrderHistory this_ where this_.disabled= false and this_.documentNumber ='"+DocNum+"' order by this_.id");
			lists = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return lists;
	}



	@Override
	public boolean deleteDocument(Session session, OrderHistory orderHistory) {
		boolean state = false;
		try {
			session.delete(orderHistory);
			state = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}
}
