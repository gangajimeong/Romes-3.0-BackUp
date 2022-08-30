package com.company.ROMES.Controller.ProductionManagement;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.ROMES.Services.ProcessM.ConstructionService;
import com.company.ROMES.Services.ProductionM.SimulationService;

@Controller
public class SimulationController {

	@Autowired
	SimulationService service;
	
	@RequestMapping(value = "/Simulation", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("material",service.getMaterial());
		model.addAttribute("lines",service.getLinePd());
		model.addAttribute("simul",service.getSimulation());
		return "ProductionM/Simulation";
	}
	
	
}
