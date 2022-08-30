package com.company.ROMES.interfaces.service.ProductionM;



import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public interface  DocumentForWorkOrderService {
	public String checkGrant();
	public JSONArray selectForGrant();
	public JSONArray selectAllDoc();
	public JSONArray selectReadyApprovalDoc();
	public JSONArray selectMakingDesignDoc();
	public JSONArray selectDocToId(int id);
	public JSONArray orderInfos();
	public JSONObject registerDesignDocument(HashMap<Object, Object>datas,MultipartFile file) throws InvalidFormatException, IOException;
	public JSONObject updateDesignDocument(HashMap<Object, Object>datas,MultipartFile file);
	public boolean deleteDesignDocument(List<Integer>ids);
	public JSONObject confirmCustomer(int id);
	public JSONObject getDocInfo(int id);
}
