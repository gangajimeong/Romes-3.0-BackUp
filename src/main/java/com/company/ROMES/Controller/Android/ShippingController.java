package com.company.ROMES.Controller.Android;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.ShippingService;

@Controller
public class ShippingController{
	
	@Autowired
	ShippingService shipService;
	
	@RequestMapping(value = "/Android/getShippingList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getShipping(HttpServletRequest request) {
		JSONObject object = shipService.getShippingList(false,request);
		System.out.println(object);
		return object.toJSONString();
	}
	
	//바코드 스캔
	@RequestMapping(value = "/Android/releaseLot", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String releaseProduct(@RequestParam("userid") int userId,@RequestParam(name="lot") String lot) {
		return shipService.releaseProduct(userId,lot).toJSONString();
	}
	@RequestMapping(value = "/Android/releaseLotById", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String releaseProduct(@RequestParam("userid") int userId,@RequestParam(name="lotid") int lot) {
		return shipService.releaseProduct(userId,lot).toJSONString();
	}
	@RequestMapping(value = "/Android/completeShipping", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String completeShipping(@RequestParam(name="data") String data,@RequestParam("userid") int userId,@RequestParam(name="printCount") int printCount, int printer) {
		JSONParser parser = new JSONParser();
		JSONArray datas = null;
		try {
			datas = (JSONArray) parser.parse(data.replace("?", "[").replace("!", "]"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shipService.completeShipping(datas, userId,printCount,printer).toJSONString();
		
	}
}
