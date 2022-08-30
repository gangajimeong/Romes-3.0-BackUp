package com.company.ROMES.Controller.ProductionManagement;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.WorkOrderService;
import com.company.ROMES.Services.StandardInfo.ErrorService;
import com.company.ROMES.entity.ErrorCode;

@Controller
public class WorkOrderInfoController {
	@Autowired
	WorkOrderService woService;
	
	@Autowired
	ErrorService erService;
	
	@RequestMapping(value = "/work", method = RequestMethod.GET)
	public String orderInfo( Model model) {
//		model.addAttribute("data",woService.getWorkOrderWithChangeLog());
//		List<ErrorCode> codes = erService.selectAllErrorCode();
//		JSONArray arry = new JSONArray();
//		for(ErrorCode c : codes) {
//			JSONObject p = new JSONObject();
//			p.put("id", c.getId());
//			p.put("code", c.getErrorCode());
//			arry.add(p);
//		}
//		model.addAttribute("errors",arry);
		model.addAttribute("data",woService.getWorkOrderDto());
		model.addAttribute("complete",woService.getcompleteWork());
		return "work";
	}
	
//	@RequestMapping(value = "/ProductionPlan", method = RequestMethod.GET)
//	public String ProductionPlanning(Model model) {
//		model.addAttribute("plans",woService.getWorkOrder());
//		return "ProductionM/ProductionPlan";
//	}
	
	@RequestMapping(value = "/remakeWork",method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String remakeWorkInfo(@RequestParam("workid") int workinfoId,@RequestParam("errorid") int errorId,@RequestParam("remake") boolean remake,@RequestParam("remark")String remark) {
		System.out.println(workinfoId + "" + remake + "" + remark);
		return woService.failWorkInfo(workinfoId,errorId, remake,remark).toJSONString();
	}
}
