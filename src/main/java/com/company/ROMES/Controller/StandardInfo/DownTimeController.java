package com.company.ROMES.Controller.StandardInfo;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.ROMES.Services.StandardInfo.DownTimeService;
import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.entity.DownTime;
import com.google.gson.JsonObject;

import Error_code.ResultCode;

@Controller
public class DownTimeController {
	@Autowired
	DownTimeService downTimeService;

	@RequestMapping(value = "/DownTime", method = RequestMethod.GET)
	public String downTime(Model model) {
		try {
			List<DownTime> downTimes = downTimeService.getDownTimes();
			if (downTimes == null) {
				// DB Connection 오류
				model.addAttribute("Error", "DB ERROR" + ":" + ResultCode.DB_CONNECT_ERROR);

			} else {
				model.addAttribute("downTimes", downTimes);
			}
			return "StandardInfo/downtime";
		} catch (Exception e) {
			
			String ErrorMsg = e.getMessage();
			model.addAttribute("Error", ErrorMsg);
			return "StandardInfo/downtime";
		}

	}

	@RequestMapping(value = "/createDownTime", method = RequestMethod.POST)
	public String create(@ModelAttribute DownTime downTime, RedirectAttributes flash) {
		if (downTime.getStartTime().isAfter(downTime.getDownTime())) {
			flash.addFlashAttribute("Error", "시작시간이 종료시간보다 빠르도록 설정해 주세요.");
		} else {
			boolean result = downTimeService.createDownTime(downTime);
			if (!result) {
				// DB 저장 실패
				flash.addFlashAttribute("Error", "Create Error" + ":" + ResultCode.DB_CONNECT_ERROR);
			}
		}
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/updateDownTime", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(@RequestBody DownTime downTime) {
			JSONObject ret = new JSONObject();
			System.out.println(downTime.getId());
			boolean result = downTimeService.updateDownTime(downTime);
			ret.put("ret",result);
			if(result!=false) {
			ret.put("id",downTime.getId());
			}
		return ret;
	}
	//ajax
	@RequestMapping(value = "/deleteDownTime", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject delete(@RequestParam(name = "id")int id) {
		System.out.println(id);
		JSONObject ret = new JSONObject();
		boolean result = downTimeService.deleteDownTime(id);
		ret.put("ret",result);
		ret.put("id", id);
		return ret;
	} 
	
	@RequestMapping(value = "/SelectDeleteDownTime", method = RequestMethod.GET)
	@ResponseBody
	public boolean SelectDelete(@RequestParam(name = "chkArr") List<Integer> chkArr) {
		boolean result = false;
		for (int i : chkArr) {
			result = downTimeService.deleteDownTime(i);
		}
		return result;
	}
}
