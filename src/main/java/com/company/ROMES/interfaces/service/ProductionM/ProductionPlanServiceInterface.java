package com.company.ROMES.interfaces.service.ProductionM;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface ProductionPlanServiceInterface {
	public JSONArray getProductionPlanList();
	public JSONArray getUnresistedOrderInfoList();
	public JSONArray getWorkingLineList();
	public int registProductionPlan(int orderId,String userId,int latlngId, int workingLineId, int productId,int count, LocalDateTime startTime, LocalDateTime endTime,boolean coating, boolean backprint, boolean isEmergency,LocalDate releaseDay,String remark);
	JSONObject selectAboutProductionPlan(int id);
	public boolean updateProductionPlan();
	public JSONArray getWorkOrder();
	public boolean switchEmergency(int id);
	public boolean selectPrinter(int id, int printer);
	public boolean completeProduction(int id);
}
