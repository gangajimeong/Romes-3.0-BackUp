package com.company.ROMES.interfaces.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.company.ROMES.entity.DocAndTitle;
import com.company.ROMES.entity.OrderHistory;

public interface OrderHistoryDAOInterface {
	public List<OrderHistory> selectAll(Session session);
	public OrderHistory selectById(Session session, int pk);
	public List<Object[]> selectDoc(Session session);
	public List<OrderHistory>selectByDocNum(Session session, String DocNum);
	public List<OrderHistory> selectByRegistedDate(Session session,LocalDate startDate,LocalDate endDate);
	public List<OrderHistory> selectByRegistedDate(Session session,LocalDate date);
	public List<OrderHistory> selectByArriveDate(Session session,LocalDate startDate,LocalDate endDate);
	public List<OrderHistory> selectByArriveDate(Session session,LocalDate date);
	public List<OrderHistory> selectByisComplete(Session session, boolean state);
	public boolean createOrderHistory(Session session, OrderHistory orderHistory);
	public boolean updateOrderHistory(Session session, OrderHistory orderHistory);
	public boolean deleteOrderHistory(Session session, int id);
	public boolean deleteDocument(Session session, OrderHistory orderHistory);
}
