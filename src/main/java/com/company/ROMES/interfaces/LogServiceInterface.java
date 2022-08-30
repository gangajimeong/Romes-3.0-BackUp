package com.company.ROMES.interfaces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface LogServiceInterface {
	public JSONObject getLogData(String preDate,String lastDate,int userId,String category);

	public JSONArray getData();
}
