package com.company.ROMES.Controller.StandardInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.ROMES.Services.StandardInfo.CompanyService;
import com.company.ROMES.entity.Brand;

import com.company.ROMES.entity.Company_manager;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.interfaces.service.StandardInfo.BrandService;
import com.google.api.client.json.JsonObjectParser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Error_code.ResultCode;
import net.sf.json.JSON;

@Controller
public class BrandManagement {
	@Autowired
	BrandService service;

	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/BrandManagement", method = RequestMethod.GET)
	public String home(Model model) {
			List<Brand>lists = new ArrayList<Brand>();
			lists = service.SelectBrandByFalse();
			model.addAttribute("BrandList",lists);
			model.addAttribute("companys",companyService.company());
			model.addAttribute("Construction",companyService.constructionCompany());
		return "StandardInfo/BrandManagement";
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectBranch", method = RequestMethod.GET)
	public JSONArray selectBranch(@RequestParam(name = "id") int id) {
		return service.SelectBranch(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/createBrand", method = RequestMethod.POST)
	public JSONObject create(@ModelAttribute Brand brand) {
		JSONObject ret = new JSONObject();
		ret = service.createBrand(brand);
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(value = "/createBranch", method = RequestMethod.POST)
	public JSONObject createBranch(@RequestParam("data")List<String>lists, @RequestParam("brand")int id) throws ParseException {
		JSONObject ret = new JSONObject();
		ret=service.createBranch(lists,id);
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateBrand", method = RequestMethod.POST)
	public JSONObject update(@ModelAttribute Brand brand) {
		return service.UpdateBrand(brand);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateBranch", method = RequestMethod.POST)
	public boolean updateBranch(@RequestParam("data")List<String>lists, @RequestParam("brand")int id,@RequestParam("tr")int len) throws ParseException {
		Gson gson = new  Gson();
		System.out.println(lists);
		List<String>res = new ArrayList<String>();
		if(len==0) {
			String ret = lists.toString().replace("[", "").replace("]", "");
			res.add(ret);
		}else {
			for(String data : lists) {
				service.updateBranch(data, id);
			}
		
	}
		return service.updateBranch(res,id);
	}

//	@ResponseBody
//	@RequestMapping(value = "/updateBranch", method = RequestMethod.POST)
//	public boolean updateBranch(@RequestParam("data")List<String>lists, @RequestParam("brand")int id) {
//		return service.updateBranch(lists, id);
//	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteBrand", method = RequestMethod.GET)
	public boolean delete(@RequestParam(name = "id") int id) {
		boolean result = service.DeleteBrand(id);
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteBranch", method = RequestMethod.GET)
	public boolean delete(@RequestParam(name = "id") int id, @RequestParam(name = "brand") int brand) {
		boolean result = service.deleteBranch(id,brand);
		return result;
	}
	@RequestMapping(value = "/SelectBrandById", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray SelectCompany(@RequestParam(name = "id") int id) {
		return service.SelectBrandAllDataById(id);
	}

	@ResponseBody
	@PostMapping(value = "/brandExcel")
	public String excedlImport(@RequestParam("Brand")String datas) {
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
}
