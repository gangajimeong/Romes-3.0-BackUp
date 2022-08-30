package com.company.ROMES.Controller.Android;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.WorkOrderService;

@Controller
public class WorkOrderController {
	@Autowired
	WorkOrderService workOrderService;
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@RequestMapping(value = "/Android/getWorkOrderList",method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getWorkOrderList() {
		return workOrderService.getWorkOrder(false).toJSONString();
	}
	
	@RequestMapping(value = "/Android/completeWork",method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String completeWorkInfo(@RequestParam(name="userid") int userId,@RequestParam("workid") int workinfoId,@RequestParam("count") int count,@RequestParam("errorid") int errorid) {
		String ret =  workOrderService.completeWorkOrder(userId, workinfoId, count,errorid).toJSONString();
		publisher.publishEvent("refresh");
		return ret;
	}
	
	@RequestMapping(value = "/Android/failWork",method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String failWorkInfo(@RequestParam(name="userid") int userId,@RequestParam("workid") int workinfoId,@RequestParam("errorid") int errorId,@RequestParam("remake") boolean remake,@RequestParam("remark")String remark) {
		String ret =  workOrderService.failWorkInfo(userId, workinfoId, errorId,remake,remark).toJSONString();
		publisher.publishEvent("refresh");
		return ret;
	}
	
}
