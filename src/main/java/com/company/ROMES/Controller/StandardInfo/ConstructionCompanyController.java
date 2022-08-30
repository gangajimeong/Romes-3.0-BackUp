package com.company.ROMES.Controller.StandardInfo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.ConstructionCompany;
import com.company.ROMES.interfaces.service.StandardInfo.ConstructionCompanyInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;



@Controller
public class ConstructionCompanyController {
	@Autowired
	ConstructionCompanyInterface service;
	
	@GetMapping(value = "/ConstructionCompany")
	public String home(Model model) {
		try {
			model.addAttribute("CompanyList",service.companyInfo());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "StandardInfo/ConstructionCompany" ;
	}
	
	@ResponseBody
	@RequestMapping(value = "/createConstruction", method = RequestMethod.POST)
	public void createConstruction(@ModelAttribute ConstructionCompany company) {
		service.createCompany(company);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateConstructionBtn", method = RequestMethod.POST)
	public JSONObject updateConstruction(@ModelAttribute ConstructionCompany company) {
		return service.updateCompany(company);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/SelectConstruction", method = RequestMethod.GET)
	public JSONArray getConstruct(@RequestParam(name = "id") int id) {
		JSONArray ret = new JSONArray();
		ConstructionCompany company = service.getConstruct(id);
		JSONArray result = new JSONArray();
		result.add(company.getId());
		result.add(company.getCompanyName());
		if(company.getRemark() != null)
			result.add(company.getRemark());
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteConstruction", method = RequestMethod.GET)
	public boolean deleteConstruction(@RequestParam(name = "id") int id) {
		return service.deleteCompany(id);
	}
	
	@ResponseBody
	@PostMapping(value = "/constructionExcel")
	public String excedlImport(@RequestParam("Construction")String datas) {
		boolean state = false;
		int fail = 0;
		Gson gson = new Gson();
		JsonArray ret = gson.fromJson(datas, JsonArray.class);
		System.out.println(ret.size());
		for(int i = 0; i<ret.size(); i++) {
			state = service.importExcel(ret.get(i).getAsJsonObject());
		}
		
		return "redirect:/main";
	}
	@ResponseBody
	@RequestMapping(value = "/setEmployees", method = RequestMethod.GET)
	public JSONArray setEmployees(@RequestParam(name = "id") int id) {
		return service.getEmployees(id);
	}
	
}
