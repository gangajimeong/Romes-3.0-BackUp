package com.company.ROMES.Controller.StandardInfo;

import java.util.List;

import org.jboss.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.entity.Division;
import com.company.ROMES.interfaces.service.StandardInfo.DivisionServiceInterface;
@Controller
public class DivisionController {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	DivisionServiceInterface ds;
	
	@RequestMapping(value = "/Division", method = RequestMethod.GET)
	public String home(Model model) {
		List<Division>lists = null;
		try {
			
			lists =ds.SelectAllByFalse();
			model.addAttribute("Divisions",lists);
			
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return "StandardInfo/DivisionManagement";	
	}
	@RequestMapping(value = "/SelectDivision", method = RequestMethod.GET)
	@ResponseBody
	public Division selectDivision(@RequestParam(name = "id")int id){
		Division division = null;
		try {
			division = ds.SelectDivision(id);
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return  division;
	}
	@RequestMapping(value = "/createDivision", method = RequestMethod.POST)
	public String create(@ModelAttribute Division div) {
		
		try {
			ds.createDivision(div);
			
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}

		return "redirect:/main";
	}
	@RequestMapping(value = "/updateDivision", method = RequestMethod.POST)
	@ResponseBody
	public Division update(@RequestBody Division div) {
		
		Division division = null;
		try {
			boolean state = false;
			state = ds.updateDivision(div);	
			if(state = true) division = div;
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return division;
	}
	@RequestMapping(value = "/deleteDivision", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject delete(@RequestParam(name = "id")int id) {
		
		boolean state = false;
		JSONObject ret = new JSONObject();
		try {
			state = ds.deleteDivision(id);
			ret.put("ret", state);
			ret.put("id", id);	
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return ret;
	}
	@ResponseBody
	@RequestMapping(value = "/setEmployee", method = RequestMethod.GET)
	public JSONArray setEmployee(@RequestParam(name = "id") int id) {
		System.out.println(id);
		return ds.getEmployee(id);
	}
}
