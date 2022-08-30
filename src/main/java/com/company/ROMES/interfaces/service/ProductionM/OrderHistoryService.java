package com.company.ROMES.interfaces.service.ProductionM;

import java.time.LocalDate;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.company.ROMES.entity.DocAndTitle;
import com.company.ROMES.entity.OrderHistory;
import com.company.ROMES.entity.ReceivedOrderInfo;



public interface OrderHistoryService {

	public List<OrderHistory>selectAll();
	public OrderHistory selectById(int id);
	public List<DocAndTitle> selectByDoc();
	public List<OrderHistory> selectByDocNum(String DocNum);
	public List<OrderHistory> selectByRegistedDate(LocalDate startDate,LocalDate endDate);
	public List<OrderHistory> selectByRegistedDate(LocalDate date);
	public List<OrderHistory> selectByArriveDate(LocalDate startDate,LocalDate endDate);
	public List<OrderHistory> selectByArriveDate(LocalDate date);
	public List<OrderHistory> selectByisComplete(boolean state);
	public OrderHistory MateralORProductDecision(int product_id,int cnt, OrderHistory orderHistory); 
	public boolean createOrderHistory(OrderHistory orderHistory);
	public boolean createAllOrderHistory(List<OrderHistory>lists);
	public boolean updateAllOrderHistory(List<OrderHistory>create, List<OrderHistory>update);
	public boolean deleteOrderHistory(int id);
	public boolean deleteDocuments(List<String>DocLists);
	
	
	
	
}
