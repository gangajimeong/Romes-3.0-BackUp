package com.company.ROMES.interfaces.service;

import java.time.LocalDate;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;


public interface NoticeServiceInterface {
	public JSONObject getNotice();
	public JSONObject getNotice(LocalDate date);
	public JSONObject getNotice(Division division);
	public JSONObject addNotice(String title,String msg,int userid);
}
