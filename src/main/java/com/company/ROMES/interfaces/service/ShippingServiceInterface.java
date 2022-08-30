package com.company.ROMES.interfaces.service;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface ShippingServiceInterface {
	public JSONObject getShippingList(boolean isShipping,HttpServletRequest request);
	public JSONObject completeShipping(JSONArray data,int userId,int printingCount,int printid);
	public JSONObject receiveProductData(int userId);
	public JSONObject releaseProduct(int userId,String lot);
	public JSONObject releaseProduct(int userId,int lotId);

}
