package com.company.ROMES.Controller.ProcessManagement;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.ROMES.Services.ProcessM.ProductStockService;

@Controller
public class ProductStockController {
	@Autowired
	ProductStockService service;
	
	@RequestMapping(value = "/ProductStock", method = RequestMethod.GET)
	public String getProductStock(Model model) {
		JSONArray arry = service.getProductStockData();
		model.addAttribute("lists", arry);
		return "ProcessM/ProductStock";
	}
}
