package com.company.ROMES.Controller.ProductionManagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.ProductionM.ProductionPlanService;

@Controller
public class ProductionManagement {
	@Autowired
	ProductionPlanService planService;

	@Autowired
	ApplicationEventPublisher publisher;

	//생산 계획
	@RequestMapping(value = "/ProductionPlan", method = RequestMethod.GET)
	public String ProductionPlanning(Model model) {
//		model.addAttribute("plans", planService.getProductionPlanList());
		model.addAttribute("line", planService.getWorkingLineList());
		model.addAttribute("data", planService.getUnresistedOrderInfoList());
		model.addAttribute("plans",planService.getWorkOrder());
//		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return "ProductionM/ProductionPlan";
	}

	// 생산 계획 + 작업지시 + 출하 계획 생성
	@RequestMapping(value = "/makeProductionPlan", method = RequestMethod.GET)
	@ResponseBody
	public String makeProductionPlan(@RequestParam(name = "userId") int userId,
			@RequestParam(name = "orderId") int orderId, @RequestParam(name = "productId") int productId,
			@RequestParam int count, @RequestParam int lineId, @RequestParam boolean isCoating,
			@RequestParam boolean isBackPrint, @RequestParam boolean isEmergency, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") LocalDateTime startTime,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")LocalDateTime endTime, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd")LocalDate releaseDate, @RequestParam int direction) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loginId = auth.getName();
		int resultCode = planService.registProductionPlan(orderId, loginId, direction, lineId, productId, count,
				startTime, endTime, isCoating, isBackPrint, isEmergency, releaseDate,"");
		JSONObject ob= new JSONObject();
		ob.put("result", resultCode);
		return ob.toJSONString();
	}
	
	@RequestMapping(value = "/selectAboutProductionPlan", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject selectAboutProductionPlan(@RequestParam(name = "id") int id) {
		JSONObject ret= new JSONObject();
		ret = planService.selectAboutProductionPlan(id);
		return ret;
	}
	@RequestMapping(value = "/updateProductionPlan", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateProductionPlan(@RequestParam HashMap<Object, Object> plan) {
		//key:{"productionPlan_id","orderInfoId","isEmergency ","backprint ","count ","coating ","backprint ","startTime","endTime ","releaseDay","direction","remark"};
		for (Entry<Object, Object> data : plan.entrySet()) {
				System.out.println(data.getKey() +" : " + data.getValue());		
			}

		return null;
	}
	@RequestMapping(value = "/deleteProductionPlan", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject deleteProductionPlan(@RequestParam(name = "id")int id) {
		System.out.println(id);

		return null;
	}
	
	@RequestMapping(value="/switchEmergency", method=RequestMethod.POST)
	@ResponseBody
	public boolean switchEmergency(@RequestParam(name = "id") int id) {
		boolean state = false;
		System.out.println("switchEmergency");
		state = planService.switchEmergency(id);
		publisher.publishEvent("refresh");
		return state;
	}
	
	@RequestMapping(value = "/selectPrinter", method=RequestMethod.POST)
	@ResponseBody
	public boolean selectPrinter(@RequestParam(name = "id")int id,@RequestParam(name = "printer") int printer) {
		boolean state = false;
		state = planService.selectPrinter(id,printer);
		publisher.publishEvent("refresh");
		return state;
	}
	
	@RequestMapping(value="/completeProduction", method = RequestMethod.POST)
	@ResponseBody
	public boolean  completeProduction(@RequestParam(name = "workOrderId") int id) {
		boolean ret =planService.completeProduction(id);
		publisher.publishEvent("refresh");
		return ret;
	}
	
//	//생산 실적
//	@RequestMapping(value = "/productionPerformance", method = RequestMethod.GET)
//	public String productionPerformance(Locale locale, Model model) {
//		return "ProductionManagement/productionPerformance";
//	}
//	//Lot 추적
//	@RequestMapping(value = "/LotTracking", method = RequestMethod.GET)
//	public String LotTracking(Locale locale, Model model) {
//		return "ProductionManagement/LotTracking";
//	}
//	//보고서 관리
//	@RequestMapping(value = "/ReportManagement", method = RequestMethod.GET)
//	public String ReportManagement(Locale locale, Model model) {
//		return "ProductionManagement/ReportManagement";
//	}

}
