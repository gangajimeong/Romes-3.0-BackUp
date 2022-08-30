package com.company.ROMES.Controller.StandardInfo;

import java.time.LocalDateTime;
import java.util.List;

import org.jboss.logging.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.StandardInfo.ProductService;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Product;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


@Controller
public class ProductController {
	Logger logger = Logger.getLogger("ProductController");

	@Autowired
	ProductService Service;
	
	@RequestMapping(value = "/productMaster", method = RequestMethod.GET)
	public String home(Model model) {
		List<Product> list = null;
		try {
			list = Service.getFalseList();
			model.addAttribute("Products", list);
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		
		return "StandardInfo/productMaster";
	}
	
	@ResponseBody
	@RequestMapping(value = "/productLists", method = RequestMethod.GET)
	public List<Product> getProductLists(){
		List<Product> list = null;
		try {
			list = Service.getFalseList();
		}catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return list;
	}
	
	@RequestMapping(value = "/SelectProduct", method = RequestMethod.GET)
	@ResponseBody
	public Product lists(@RequestParam(name = "id") int id) {
		Product m = null;
		try {
			m = Service.getProductById(id);
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return m;
	}
	
	@RequestMapping(value = "/createProduct", method = RequestMethod.POST)
	public String create(@ModelAttribute Product p) {

		try {
			System.out.println(p.toString());
			Service.createProduct(p);

		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}

		return "redirect:/main";
	}
	
	@RequestMapping(value = "/isRegi", method = RequestMethod.GET)
	@ResponseBody
	public boolean isExist(@RequestParam(name = "productCode") String code) {
		boolean state = false;
		System.out.println("isRegi");
		try {
			state = Service.isExistProduct(code);
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return state;
	}
	
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	@ResponseBody
	public Product update(@RequestBody Product p) {
		Product updateProduct = null;
		try {
			updateProduct = Service.updateProduct(p);

		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return updateProduct;
	}
	
	@RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject delete(@RequestParam(name = "id") int id) {
		int deleteProduct = 0;
		boolean state = false;
		JSONObject ret = new JSONObject();
		try {
			deleteProduct = Service.deleteProduct(id);
			state = true;
			ret.put("ret", state);
			ret.put("id", id);
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return ret;
	}
	
	@RequestMapping(value = "/SelectNeedLists", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject lists() {
		JSONObject ret = new JSONObject();
		try {
			ret = Service.getCommonCode();

		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return ret;
	}
	
	@ResponseBody
	@PostMapping(value = "/productExcel")
	public String excedlImport(@RequestParam("Product")String datas) {
		boolean state = false;
		int fail = 0;
		Gson gson = new Gson();
		JsonArray ret = gson.fromJson(datas, JsonArray.class);
		System.out.println(ret.size());
		for(int i = 0; i<ret.size(); i++) {
			state = Service.importExcel(ret.get(i).getAsJsonObject());
		}
		
		return "redirect:/main";
	}
}
