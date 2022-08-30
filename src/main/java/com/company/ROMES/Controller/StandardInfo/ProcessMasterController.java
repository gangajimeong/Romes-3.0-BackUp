package com.company.ROMES.Controller.StandardInfo;

import java.util.List;

import org.jboss.logging.Logger;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.StandardInfo.ProcessMasterService;
import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.entity.ProcessMaster;
import com.company.ROMES.entity.Product;
import com.company.ROMES.entity.WorkReport;

@Controller
public class ProcessMasterController {
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	ProcessMasterService service;
	
	@RequestMapping(value = "/ProcessMaster", method = RequestMethod.GET)
	public String home(Model model) {
		
		model.addAttribute("ProcessMasters", service.getProcessList());
		
		return "StandardInfo/ProcessMaster";
	}
	
	@RequestMapping(value = "/createProcessMaster", method = RequestMethod.POST)
	public String create(@ModelAttribute ProcessMaster p) {
		System.out.println(p.getAfterProcess());
		System.out.println(p.getBeforeProcess());
		service.createProcess(p);

		return "redirect:/main";
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectProcessById", method = RequestMethod.GET)
	public JSONArray selectProcessById(@RequestParam int id) {
		return service.selectProcessById(id);
	}
	
	@ResponseBody
	@PostMapping(value = "/updateProcess")
	public boolean updateProcess(@ModelAttribute ProcessMaster p) {
		return service.updateProcess(p);
	}
	
	@ResponseBody
	@GetMapping(value = "/deleteProcess")
	public boolean deleteProcess(@RequestParam(name = "id") int id) {
		return service.deleteProcess(id);
	}
}
