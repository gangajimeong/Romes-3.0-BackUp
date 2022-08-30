package com.company.ROMES.interfaces.service.StandardInfo;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.company.ROMES.entity.WorkingLine;

public interface WorkingLineServiceInterface {
	
	public List<WorkingLine>SelectAll();
	public List<WorkingLine>SelectAllByFalse();
	public WorkingLine SelectById(int id);
	public boolean createWorkingLine(WorkingLine workingLine);
	public boolean updateWorkingLine(WorkingLine workingLine);
	public boolean deleteWorkingLine(int id);
	public JSONArray getLocation();
	public JSONArray getWorkingLine();
	public boolean updateWorkingLine(List list);
	public void createWL(WorkingLine w);
	public JSONArray selectWLById(int id);
	public boolean updateWL(WorkingLine w);
	public JSONObject deleteWL(int id);
	
	
}
