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

import com.company.ROMES.Services.ProductionM.DefectService;
import com.company.ROMES.entity.ErrorProductHistory;
import com.company.ROMES.entity.WorkingLine;

@Controller
public class EquipmentDefectController {

	@Autowired
	DefectService service;
	
	@RequestMapping(value = "/EquipmentDefect", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("equipmentDefects",service.ProductError());
		model.addAttribute("errorCode",service.getErrorCode());
		model.addAttribute("lines",service.getLine());
		return "ProductionM/EquipmentDefect";
	}
	@ResponseBody
	@RequestMapping(value = "/selectEDById", method = RequestMethod.GET)
	public JSONArray selectEDById(@RequestParam int id) {
		return service.selectEDById(id);
	}
	
	@ResponseBody
	@PostMapping(value = "/updateED")
	public boolean updateED(@ModelAttribute ErrorProductHistory ep) {
		return service.updateED(ep);
	}
	
	@ResponseBody
	@GetMapping(value = "/deleteED")
	public boolean deleteED(@RequestParam(name = "id") int id) {
		return service.deleteED(id);
	}
	
}
