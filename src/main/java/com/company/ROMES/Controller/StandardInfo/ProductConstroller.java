//package com.company.ROMES.Controller.StandardInfo;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.jboss.logging.Logger;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.company.ROMES.entity.CommonCode;
//import com.company.ROMES.entity.ManufactureProduct;
//import com.company.ROMES.entity.Product;
//import com.company.ROMES.interfaces.service.StandardInfo.ProductService;
//
//@Controller
//public class ProductConstroller {
//	Logger logger = Logger.getLogger("ProductController");
//
//	@Autowired
//	ProductService ps;
//	
//	@Autowired
//	com.company.ROMES.Services.StandardInfo.ProductService Service;
//
//	@RequestMapping(value = "/productMaster", method = RequestMethod.GET)
//	public String home(Model model) {
//		List<ManufactureProduct> list = null;
//		try {
//			list = ps.getFalseList();
//			model.addAttribute("Products", list);
////			model.addAttribute("Products",ps.getProduct());
//		} catch (Exception e) {
//			for (StackTraceElement s : e.getStackTrace()) {
//				logger.error(s.toString());
//			}
//
//		}
//		return "StandardInfo/productMaster";
//	}
//	@ResponseBody
//	@RequestMapping(value = "/productLists", method = RequestMethod.GET)
//	public List<ManufactureProduct> getProductLists() {
//		List<ManufactureProduct> list = null;
//		try {
//			list = ps.getFalseList();
//		} catch (Exception e) {
//			for (StackTraceElement s : e.getStackTrace()) {
//				logger.error(s.toString());
//			}
//
//		}
//		return list;
//	}
//	
//
//	@RequestMapping(value = "/SelectProduct", method = RequestMethod.GET)
//	@ResponseBody
//	public ManufactureProduct lists(@RequestParam(name = "id") int id) {
//		ManufactureProduct m = null;
//		try {
//			m = ps.getProductById(id);
//		} catch (Exception e) {
//			for (StackTraceElement s : e.getStackTrace()) {
//				logger.error(s.toString());
//			}
//		}
//		return m;
//	}
//	@RequestMapping(value = "/isRegi", method = RequestMethod.GET)
//	@ResponseBody
//	public boolean isExist(@RequestParam(name = "productCode") String code) {
//		boolean state = false;
//		System.out.println("isRegi");
//		try {
//			state = ps.isExistProduct(code);
//		} catch (Exception e) {
//			for (StackTraceElement s : e.getStackTrace()) {
//				logger.error(s.toString());
//			}
//		}
//		return state;
//	}
//	@RequestMapping(value = "/createProduct", method = RequestMethod.POST)
//	public String create(@ModelAttribute ManufactureProduct m) {
//
//		try {
//			System.out.println(m.toString());
//			ps.createProduct(m);
//
//		} catch (Exception e) {
//			for (StackTraceElement s : e.getStackTrace()) {
//				logger.error(s.toString());
//			}
//		}
//
//		return "redirect:/main";
//	}
//
//	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
//	@ResponseBody
//	public ManufactureProduct update(@RequestBody ManufactureProduct m) {
//		ManufactureProduct updateProduct = null;
//		LocalDateTime create = LocalDateTime.MIN;
//		try {
//			create = ps.getProductById(m.getId()).getCreateDate();
//			m.setCreateDate(create);
//			updateProduct = ps.updateProduct(m);
//
//		} catch (Exception e) {
//			for (StackTraceElement s : e.getStackTrace()) {
//				logger.error(s.toString());
//			}
//		}
//		return updateProduct;
//	}
//
//	@RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
//	@ResponseBody
//	public JSONObject delete(@RequestParam(name = "id") int id) {
//		int deleteProduct = 0;
//		boolean state = false;
//		JSONObject ret = new JSONObject();
//		try {
//			deleteProduct = ps.deleteProduct(id);
//			state = true;
//			ret.put("ret", state);
//			ret.put("id", id);
//		} catch (Exception e) {
//			for (StackTraceElement s : e.getStackTrace()) {
//				logger.error(s.toString());
//			}
//		}
//		return ret;
//	}
//
//	@RequestMapping(value = "/SelectNeedLists", method = RequestMethod.GET)
//	@ResponseBody
//	public JSONObject lists() {
//		JSONObject ret = new JSONObject();
//		try {
//			ret = ps.getCommonCode();
//
//		} catch (Exception e) {
//			for (StackTraceElement s : e.getStackTrace()) {
//				logger.error(s.toString());
//			}
//		}
//		return ret;
//	}
//}
