package com.company.ROMES.Controller.StandardInfo;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.interfaces.service.StandardInfo.ApprovalInfoService;

@Controller
public class ApprovalInfoController {
	@Autowired
	ApprovalInfoService service;

	@RequestMapping(value = "/ApprovalInfo", method = RequestMethod.GET)
	public String ProcessMaster(Model model) {
		JSONArray rets = service.aboutApprovalInfoByFalse();
		model.addAttribute("orderInfos", rets);
		System.out.println(rets.size());
		return "StandardInfo/ApprovalInfo";
	}

	@ResponseBody
	@RequestMapping(value = "/getDivisionOrPositionOrUsers", method = RequestMethod.GET)
	public JSONArray getDivisionOrPositionOrUsers(@RequestParam(name = "option") int option,
			@RequestParam(name = "division_id") int id, @RequestParam(name = "position")String position) { // 0: division, 1: positions,2: users
		JSONArray rets = new JSONArray();
		if (option == 0 && id == 0) {
			rets = service.divisions();
		} else if (option == 1 && id != 0) {
			rets = service.filteredPostions(id);
		} else if (option == 2 && id != 0) {
			System.out.println(id+","+position);
			rets = service.filterdUsers(id,position);
		}else {
			
		}

		return rets;
	}
}
