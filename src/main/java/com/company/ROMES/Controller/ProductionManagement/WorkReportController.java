package com.company.ROMES.Controller.ProductionManagement;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.ROMES.Services.ProductionM.WorkReportService;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.WorkReport;

import Error_code.ResultCode;

@Controller
public class WorkReportController {
	
	@Autowired
	WorkReportService service;
	
	@RequestMapping(value = "/WorkReport", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("Reports",service.getWorkReport());
		return "ProductionM/WorkReport";
	}
	
	@RequestMapping(value="/createReportWork",method = RequestMethod.POST)
	public String createReportWork(@ModelAttribute WorkReport report) {
		boolean result = service.createReportWork(report);
		if(!result)
			System.out.println("DB connection error");
		return "redirect:/main";
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectWorkReportById", method = RequestMethod.GET)
	public JSONArray selectOrderInfoById(@RequestParam int id) {
		return service.selectWorkReportById(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectWorkReportByIdForShow", method = RequestMethod.GET)
	public JSONArray selectWorkReportByIdForShow(@RequestParam int id) {
		return service.selectWorkReportByIdForShow(id);
	}
	
	@ResponseBody
	@PostMapping(value = "/updateWorkReport")
	public boolean updateWorkReport(@ModelAttribute WorkReport report) {
		return service.updateWorkReport(report);
	}
	
	@ResponseBody
	@GetMapping(value = "/deleteWorkReport")
	public boolean deleteWorkReport(@RequestParam int id) {
		return service.deleteWorkReport(id);
	}
}