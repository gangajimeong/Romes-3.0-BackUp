package com.company.ROMES.interfaces.service.StandardInfo;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.company.ROMES.entity.ManufactureProduct;

public interface ProductService {
		//제품 리스트
		public List<ManufactureProduct>getList();
		public List<ManufactureProduct>getFalseList();
		
		//제품 검색
		public ManufactureProduct getProductById(int id);
		//CUD
		public void createProduct(ManufactureProduct manufactureProduct);
		public ManufactureProduct updateProduct(ManufactureProduct manufactureProduct);
		public int deleteProduct(int id);
		public JSONObject getCommonCode();
		public boolean isExistProduct(String code);
		public JSONArray getProduct();
		
	
}
