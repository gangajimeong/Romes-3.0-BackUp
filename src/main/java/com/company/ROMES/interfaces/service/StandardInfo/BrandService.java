package com.company.ROMES.interfaces.service.StandardInfo;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.company.ROMES.entity.Brand;
import com.company.ROMES.entity.Company_manager;
import com.company.ROMES.entity.LatLng;
import com.google.gson.JsonObject;

public interface BrandService {

	
	//브랜드 관리 삭제 되지 않은 내용 조회
	public JSONArray SelectBrandByFalse();
	//매장 관리 조회 
	public JSONArray SelectBranch(int id);//파라미터 brand ID
	public JSONArray SelectBrandAllDataById(int id);
	//브랜드 생성
	public JSONObject createBrand(Brand brand);
	//매장 생성
	public JSONObject createBranch(List<String>lists, int id);//브랜드 id
	//브랜드 수정
	public JSONObject UpdateBrand(Brand brand);
	//매장 수정 
	public boolean updateBranch(List<String>lists, int id);
	public boolean DeleteBrand(int id);
	public boolean deleteBranch(int id, int brand);
	public boolean updateBranch(String datas, int id);
	
	//Excel import
	public boolean importExcel(JsonObject datas);
}
