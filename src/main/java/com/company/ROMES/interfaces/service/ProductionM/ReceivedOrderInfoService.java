package com.company.ROMES.interfaces.service.ProductionM;

import java.util.List;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import com.company.ROMES.entity.ConstructionPlan;

public interface ReceivedOrderInfoService {
	public List<ConstructionPlan> ConstructionPlanList(Session session,int userId,boolean receivedProduct);
	public JSONObject getReceiveProductData(int userId);
	public JSONObject receiveProduct(int userId,int planId);
	public JSONObject getCompleteConstructionResult(int userId,int year, int month);
	public JSONObject getDashboardData(int userId);

}
