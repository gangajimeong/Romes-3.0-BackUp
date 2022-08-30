package com.company.ROMES.Controller.ProductionManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.ProcessM.ConstructionService;

@Controller
public class ConstructionOrderController {
	
	@Autowired
	ConstructionService service;
	
	@GetMapping(value = "/ConstructionOrder")
	public String home(Model model) {
		try {
			model.addAttribute("Lists", service.ConstructionPlan());
			model.addAttribute("finList", service.getConstructionRes());
			model.addAttribute("company",service.getConstructionCompany());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "ProductionM/ConstructionOrder";
	}
	
	@RequestMapping(value = "/ConstructionConfirm", method = RequestMethod.POST)
	@ResponseBody
	public boolean ConstructionConfirm(@RequestParam(name = "id")int id, @RequestParam(name = "company")int company) {
		return service.ConstructionConfirm(id, company);
	}
}
