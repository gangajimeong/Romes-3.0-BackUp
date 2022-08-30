package com.company.ROMES.interfaces.service.StandardInfo;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.company.ROMES.entity.Material;
import com.google.gson.JsonObject;

public interface MeterialServiceInterface {
	//KeySet으로 제품 리스트 받아오기
	public List<Material> selectMeterials(Set<Integer> keylist);
	public List<Material> selectMeterials(Session session,Set<Integer> keylist);
	
	public List<Material> selectAll();
	public Material selectById(int id);
	public boolean createMaterial(Material material);
	public boolean updateMaterial(Material material);
	public boolean deleteMaterial(int id);
	public JSONArray getlocation();
	public JSONArray printList();
	public JSONObject printBarcode(int id, String printName);
	public JSONArray selectMaterialById(int id);
	public List<Material> getMaterialList();
	public boolean importExcel(JsonObject datas);
	
}
