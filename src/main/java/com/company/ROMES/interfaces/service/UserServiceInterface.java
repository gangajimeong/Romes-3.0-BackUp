package com.company.ROMES.interfaces.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.company.ROMES.entity.User;

public interface UserServiceInterface  {
	public JSONObject joinMember(String id, String pw, String name, String position, int divisionId, String phoneNum, String email);
	public List<User>users();
	public void createUser(User user);
	public JSONObject saveDeviceName(String device);
	//id/pw으로 사용자 찾기(로그인)
	public User getUserByIdPw(String id,String pw);
	public int changeUserCommute(String workerId, String commuteCode);
	public JSONObject getDashBoard(int userId);
	public JSONObject checkId(String id);
	public JSONObject findUser(String name,String phoneNum,String email);
	public JSONObject findUser(String name,String phoneNum,String email,String id);
	public JSONObject resetPassword(int userId,String newPw);
	public JSONObject getDivisionList();
	public JSONObject getUserList(int userId);
	public JSONObject acceptJoinRequest(int userId);
	public JSONObject changeAuthorities(int userId, boolean AuthoritiesId);
	public JSONArray getDivision();
}
