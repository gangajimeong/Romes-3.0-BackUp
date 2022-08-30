package com.company.ROMES.interfaces.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface WorkOrderServiceInterface {
	
	public JSONObject getWorkOrder( boolean isComplete );
	public JSONObject completeWorkOrder(int userid,int workorderId,int count,int errorid);
	public JSONArray calculateProduction(int manufactureId, int count,LocalDateTime startTime);
	public JSONObject failWorkInfo(int userId,int workInfoId,int errorId,boolean remake,String remark);
	public JSONObject failWorkInfo(int workInfoId,int errorId,boolean remake,String remark);
	public JSONArray getWorkOrderWithChangeLog();
}
