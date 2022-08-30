package com.company.ROMES.interfaces.service.StandardInfo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.company.ROMES.entity.ConstructionCompany;
import com.google.gson.JsonObject;

public interface ConstructionCompanyInterface {

	public JSONArray companyInfo();

	public JSONObject createCompany(ConstructionCompany com);

	public ConstructionCompany getConstruct(int id);

	public JSONObject updateCompany(ConstructionCompany com);

	public boolean deleteCompany(int id);

	public boolean importExcel(JsonObject datas);

	public JSONArray getEmployees(int id);
	
}
