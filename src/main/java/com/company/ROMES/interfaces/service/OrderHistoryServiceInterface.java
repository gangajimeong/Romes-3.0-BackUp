package com.company.ROMES.interfaces.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.json.simple.JSONArray;

import com.company.ROMES.entity.OrderHistory;
import com.company.ROMES.entity.SRHistory;

public interface OrderHistoryServiceInterface {
	public List<OrderHistory> selectTodayOrderHistory();
	public List<OrderHistory> selectTodayOrderHistory(Session session);
	public OrderHistory selectOrderHistory(int id);
	public boolean completeOrder(int orderId,int userId,int productIdAndCount,int printerId,int locationId,String remark);
}
