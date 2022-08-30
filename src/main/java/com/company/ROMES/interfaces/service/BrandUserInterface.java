package com.company.ROMES.interfaces.service;



import org.json.simple.JSONObject;

import com.company.ROMES.entity.BrandUser;


public interface BrandUserInterface {
	public BrandUser getBrandUserByIdPw(String id,String pw);
	public void getConstructData();
	public JSONObject checkId(String id);
	public JSONObject joinMember(String id, String pw, String name, int divisionId, String phoneNum,
			String email);
	public JSONObject findUser(String name, String phoneNum, String email);
	public JSONObject findUser(String name, String phoneNum, String email, String id);
	public JSONObject getBrandList();
	public JSONObject resetPassword(int userId,String newPw);
	public JSONObject getCompleteConstructionResult(int userId, int year, int month);
	public JSONObject approve(int id);
	public JSONObject getApproveList(int userId, int year, int month);
}
