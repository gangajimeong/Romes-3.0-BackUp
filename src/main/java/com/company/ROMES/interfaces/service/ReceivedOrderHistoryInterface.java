package com.company.ROMES.interfaces.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.company.ROMES.entity.ConstructionPlan;
import com.company.ROMES.entity.Design;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.WorkOrderInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface ReceivedOrderHistoryInterface {

	public List<ConstructionPlan> ConstructionPlanList(Session session, int userId, boolean receivedProduct);
	

	public JSONObject getReceiveProductData(int userId);

	public JSONObject receiveProduct(int userId, int planId);

	public JSONObject getCompleteConstructionResult(int userId, int year, int month);

	public JSONObject getDashboardData(int userId);

	public JSONObject getProductsAndImages(int info_id);

	public int saveReceivedOrder(JsonObject data);

	public JSONObject createReceivedOrderInfo(HashMap<Object, Object> info, String products, List<String> urls);

	public List<Division> lists();

	public LatLng createLatLng(LatLng data);

	public JSONArray selectLatLng();

	public JSONArray selectConstructionPlan(int id);

	public JSONObject createConstructionPlan(int sujuId, int divisionID, LocalDate plan, List<Integer> latlng);

	public boolean updateReceivedOrderInfo(HashMap<Object, Object> info, String products, List<Integer> urls);

	public boolean deleteReceivedOrderInfo(int id);

	public JSONArray planLocationList(int id);

	public boolean updateConstructionPlan(HashMap<Object, Object> plan, List<Integer> latIds,
			List<Integer> conslocationList);

	public boolean deleteConstructionPlan(int id);

	public JSONObject createProductData(HashMap<Object, Object> info, List<String> urls);

	// new(적용 메소드)
	// selectBox data
	public JSONArray getCompanyList();
	public JSONArray getBrandList();
	public JSONArray getUserByDivision();
	// 조회 
	public List<ReceivedOrderInfo> receivedOrderInfos();
	public List<WorkOrderInfo> workOrderInfos();
	public JSONArray selectOrderInfoById(int id);
	public JSONArray  selectOrderInfoByDate(LocalDateTime start, LocalDateTime end);
	
	// 수주 crud
	public boolean createOrderInfo(ReceivedOrderInfo info);
	public int updateOrderInfo(ReceivedOrderInfo info);
	public boolean deleteOrderInfo(int id);
	
	//작업지시 생성
	public int createWorkOrderInfo(JsonArray ret, JsonArray data);

	//시공계획 생성
	public boolean createConstructionPlan(int id);


	public JSONArray getlatlng();
	
	//기간검색
	public JSONArray period(String preTime, String lastTime);

	//시안 등록
	public boolean registDesign(String filename, int id);
	public String SaveSampleImg(MultipartFile file, int id);




	public boolean importExcel(JsonObject datas);


	public JSONArray setWorkorderBrand(int id);


	public JSONArray orderFindBrand(int id);


	public boolean deleteWorkOrder(int id);


	public JSONArray updateOrderFindBrand(int id);





}
