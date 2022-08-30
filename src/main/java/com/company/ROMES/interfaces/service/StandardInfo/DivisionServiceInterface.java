package com.company.ROMES.interfaces.service.StandardInfo;

import java.util.List;

import org.json.simple.JSONArray;

import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.User;

public interface DivisionServiceInterface {
	public List<Division>SelectAll();
	public List<Division>SelectAllByFalse();
	public List<User>SelectEmployee( int DivId);
	public Division SelectDivision(int id);
	public boolean createDivision(Division division);
	public boolean updateDivision(Division division);
	public boolean deleteDivision(int id);
	public JSONArray getEmployee(int id);
}
