package com.company.ROMES.interfaces.service.StandardInfo;

import java.util.List;

import org.json.simple.JSONObject;

import com.company.ROMES.entity.Product;
import com.google.gson.JsonObject;

public interface ProductServiceInterface {

	public List<Product> getList();

	public List<Product> getFalseList();

	public Product getProductById(int id);

	public void createProduct(Product product);

	public boolean isExistProduct(String code);

	public Product updateProduct(Product product);

	public int deleteProduct(int id);

	public JSONObject getCommonCode();

	public boolean importExcel(JsonObject datas);

}
