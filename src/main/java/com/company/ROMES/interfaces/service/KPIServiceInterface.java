package com.company.ROMES.interfaces.service;

import org.json.simple.JSONObject;

public interface KPIServiceInterface {
	public JSONObject getCapaData(String preTime,String lastTime,int lineid);
	public JSONObject getKPIData(String preTime,String lastTime,int lineId);
}
