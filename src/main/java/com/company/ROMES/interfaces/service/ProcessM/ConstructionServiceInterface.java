package com.company.ROMES.interfaces.service.ProcessM;

import java.time.LocalDate;

import org.hibernate.Session;
import org.json.simple.JSONArray;

import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.Division;

public interface ConstructionServiceInterface {
	public JSONArray getConstructionResult(LocalDate date1, LocalDate date2,int orderCompany,int constructDivision);
	public JSONArray getConstructionPlan();
	public JSONArray getConstructionRes();
	public JSONArray ConstructionPlan();
	public JSONArray getConstructionCompany();
	public boolean ConstructionConfirm(int id, int company);
}
