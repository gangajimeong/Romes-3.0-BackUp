package com.company.ROMES.Controller.ProcessManagement;

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

import com.company.ROMES.Services.ProcessM.ProductDefectService;
import com.company.ROMES.entity.ConstructionResult;
import com.company.ROMES.entity.ErrorProductHistory;
import com.company.ROMES.entity.ProcessMaster;

@Controller
public class ProductDefectController {

	@Autowired
	ProductDefectService service;
	
	@RequestMapping(value = "/ProductDefect", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("ProductDefect", service.getPDList());
		model.addAttribute("ConstructionDefects", service.getCDList());
		model.addAttribute("errors", service.getErrorcodeList());
		return "ProcessM/ProductDefect";
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectPDById", method = RequestMethod.GET)
	public JSONArray selectPDById(@RequestParam int id) {
		return service.selectPDById(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectCDById", method = RequestMethod.GET)
	public JSONArray selectCDById(@RequestParam int id) {
		return service.selectCDById(id);
	}
	
	@ResponseBody
	@PostMapping(value = "/updatePD")
	public boolean updatePD(@ModelAttribute ErrorProductHistory ep) {
		return service.updatePD(ep);
	}
	
	@ResponseBody
	@GetMapping(value = "/deletePD")
	public boolean deletePD(@RequestParam(name = "id") int id) {
		return service.deletePD(id);
	}
	
	@ResponseBody
	@PostMapping(value = "/updateCD")
	public boolean updateCD(@ModelAttribute ConstructionResult cr) {
		return service.updateCD(cr);
	}
	
	@ResponseBody
	@GetMapping(value = "/deleteCD")
	public boolean deleteCD(@RequestParam(name = "id") int id) {
		return service.deleteCD(id);
	}
}
