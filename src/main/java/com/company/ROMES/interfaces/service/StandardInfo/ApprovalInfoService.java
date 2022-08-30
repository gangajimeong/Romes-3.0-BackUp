package com.company.ROMES.interfaces.service.StandardInfo;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.company.ROMES.entity.ApprovalInfo;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;

public interface ApprovalInfoService {
	
	//조회
	public JSONArray aboutApprovalInfoByFalse();
	public JSONObject approvalInfoOrder(int id);
	
	//셀렉트 박스 조회
	public JSONArray divisions();
	public JSONArray filteredPostions(int division_id);
	public JSONArray filterdUsers(int division_id,String postion);
	//CRUD
	public JSONObject createApprovalInfo(ApprovalInfo approvalInfo);
	public JSONObject updateApprovalInfo(ApprovalInfo approvalInfo);
	public JSONObject deleteApprovalInfo(int id);
	
}
