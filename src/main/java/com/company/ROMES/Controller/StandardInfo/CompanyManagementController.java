package com.company.ROMES.Controller.StandardInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.company.ROMES.Services.StandardInfo.CompanyService;
import com.company.ROMES.entity.Brand;
import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.Company_manager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import Error_code.ResultCode;

@Controller
public class CompanyManagementController {
	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/companyManagement", method = RequestMethod.GET)
	public String companyManagement(Model model) {
		try {
			List<Company> companyList = companyService.SelectCompanyByFalse();
			model.addAttribute("companyList", companyList);
			return "StandardInfo/companyManagement";
		} catch (Exception e) {
			e.printStackTrace();
			String error = "Error occured:" + e.getMessage();
			model.addAttribute("error", error);
			return "StandardInfo/companyManagement";
		}

	}

	@ResponseBody
	@RequestMapping(value = "/companyLists", method = RequestMethod.GET)
	public JSONArray getCompanyLists() {
		JSONArray ret = new JSONArray();
		try {
			ret = companyService.company();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return ret;

	}

	@RequestMapping(value = "/createCompanyManagement", method = RequestMethod.POST)
	public String create(@ModelAttribute Company company, RedirectAttributes flash) {
		System.out.println(company);
		boolean result = companyService.createCompany(company);
		if (!result) {
			flash.addFlashAttribute("Error", "Create Error" + ":" + ResultCode.DB_CONNECT_ERROR);
		}
		return "redirect:/main";
	}

	@RequestMapping(value = "/SearchDBcompany", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray SearchDBcompany(@RequestParam(name = "id") int id) {
		JSONArray result = new JSONArray();
		result = companyService.managerLists(id);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/registerForcompany", method = RequestMethod.POST)
	public JSONObject register(@RequestParam HashMap<Object, Object> manager) {
		JSONObject ret = new JSONObject();
		for (Entry<Object, Object> key : manager.entrySet()) {
			System.out.println(key.getKey() + ":" + key.getValue());
		}
		Company_manager company_Manager = new Company_manager();
		company_Manager.setName(manager.get("name").toString());
		company_Manager.setPosition(manager.get("position").toString());
		company_Manager.setPhone(manager.get("phone").toString());
		company_Manager.setEmail(manager.get("email").toString());
		boolean result = companyService.ManagerRegister(company_Manager,
				Integer.parseInt(manager.get("companyId").toString()));
		ret.put("ret", result);
		return ret;
	}

	@RequestMapping(value = "/updateManager", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateManager(@ModelAttribute Company_manager company_manager) {
		JSONObject ret = new JSONObject();
		boolean result = companyService.MangerInfoUpdate(company_manager, 0);
		ret.put("ret", result);
		return ret;
	}

	@RequestMapping(value = "/deleteManager", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteManager(@RequestParam(name = "id") int id, RedirectAttributes flash) {

		boolean result = companyService.ManagerInfoDelete(id);
		if (!result) {
			flash.addFlashAttribute("Error", "Create Error" + ":" + ResultCode.DB_CONNECT_ERROR);
		}
		return result;
	}

	@RequestMapping(value = "/updatecompany", method = RequestMethod.POST)
	public String update(@ModelAttribute Company company, RedirectAttributes flash) {

		boolean result = companyService.UpdateCompany(company);
		if (!result) {
			flash.addFlashAttribute("Error", "Create Error" + ":" + ResultCode.DB_CONNECT_ERROR);
		}
		return "redirect:/main";
	}

	@ResponseBody
	@RequestMapping(value = "/deletecompany", method = RequestMethod.GET)
	public boolean delete(@RequestParam(name = "id") int id, RedirectAttributes flash) {

		Company company = companyService.SelectCompanyById(id);
		boolean result = companyService.DeleteCompany(company);

		return result;
	}

	@RequestMapping(value = "/SelectDeletecompany", method = RequestMethod.GET)
	@ResponseBody
	public boolean SelectDelete(@RequestParam(name = "chkArr") List<Integer> chkArr) {

		boolean result = false;
		for (int i : chkArr) {
			Company company = companyService.SelectCompanyById(i);
			result = companyService.DeleteCompany(company);
		}
		return result;
	}

	@RequestMapping(value = "/Selectcompany", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray SelectCompany(@RequestParam(name = "id") int id) {

		Company company = companyService.SelectCompanyById(id);
		JSONArray result = new JSONArray();
		result.add(company.getId());
		// result.add(company.getCompanyClassification());
		result.add(company.getCompanyName());
		result.add(company.getCEO_Name());
		result.add(company.getBusinessNumber());
		// result.add(company.getIndustryType());
		// result.add(company.getIndustryType_detail());
		result.add(company.getPostalCode());
		result.add(company.getRealAddress());
		result.add(company.getPhone());
		result.add(company.getFax());
		result.add(company.getEmail());
		result.add(company.getRemarks());

		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateCompanyBtn", method = RequestMethod.POST)
	public JSONObject update(@ModelAttribute Company company) {
		return companyService.updateCompanyBtn(company);
	}
	
	@ResponseBody
	@PostMapping(value = "/companyExcel")
	public String excedlImport(@RequestParam("Company")String datas) {
		
		Gson gson = new Gson();
		JsonArray ret = gson.fromJson(datas, JsonArray.class);
		System.out.println(ret.size());
		for(int i = 0; i<ret.size(); i++) {
			companyService.importExcel(ret.get(i).getAsJsonObject());
		}
//		System.out.println(ret.get(0).getAsJsonObject().get("companyName"));
		return "redirect:/main";
	}
}
