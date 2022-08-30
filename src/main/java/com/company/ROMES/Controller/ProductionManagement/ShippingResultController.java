package com.company.ROMES.Controller.ProductionManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.ROMES.Services.ProductionM.ShippingResultService;

@Controller
public class ShippingResultController {

	@Autowired
	ShippingResultService service;
	
	@RequestMapping(value = "/ShippingResult", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("SR",service.CompleteWork());
		return "ProductionM/ShippingResult";
	}
}
