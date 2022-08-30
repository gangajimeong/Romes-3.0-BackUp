package com.company.ROMES.Controller.ProcessManagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.ROMES.Services.ProcessM.ConstructionService;
import com.company.ROMES.entity.DocAndTitle;

@Controller
public class ConstructionController {
	
	@Autowired
	ConstructionService constructionService;
	
	@RequestMapping(value = "/ConstructionPerformance", method = RequestMethod.GET)
	public String getConstructionPerformance(Model model) {
		JSONArray arry = constructionService.getConstructionPlan();
		model.addAttribute("lists", arry);
		return "ProcessM/ConstructionPerformance";
	}

}
