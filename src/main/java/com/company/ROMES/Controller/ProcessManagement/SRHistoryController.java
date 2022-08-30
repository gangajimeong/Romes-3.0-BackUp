package com.company.ROMES.Controller.ProcessManagement;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.ROMES.Services.ProcessM.SRHistoryService;

@Controller
public class SRHistoryController {
	@Autowired
	SRHistoryService srhistoryService;
	
	@RequestMapping(value = "/SRHistoryList", method = RequestMethod.GET)
	public String getSRHistoryList(Model model) {
		JSONArray arry = srhistoryService.getSRList();
		model.addAttribute("lists", arry);
		return "ProcessM/SRHistoryList";
	}

}
