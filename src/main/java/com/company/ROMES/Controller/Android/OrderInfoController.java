package com.company.ROMES.Controller.Android;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.apache.bcel.generic.RET;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.OrderHistoryService;
import com.company.ROMES.Services.StandardInfo.MeterialService;
import com.company.ROMES.entity.Lot;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.OrderHistory;

import Error_code.ResultCode;

@Controller
public class OrderInfoController {

	@Autowired
	OrderHistoryService orderHistoryService;

	@Autowired
	SessionFactory factory;
	@Autowired
	MeterialService meterialService;

	@RequestMapping(value = "/Android/orderHistoryList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getOrderHistory(@RequestParam(name = "userid") int userId) {
		JSONObject object = new JSONObject();
		JSONArray retlist = new JSONArray();
		Session session = null;
		try {
			session = factory.openSession();
			List<OrderHistory> historys = orderHistoryService.selectTodayOrderHistory(session);
			if (historys == null) {
				object.put("result", ResultCode.DB_CONNECT_ERROR);
			} else {
				for (OrderHistory history : historys) {
					JSONObject o = new JSONObject();
					JSONArray productlist = new JSONArray();
					Material orderProduct = session.find(Material.class, history.getProduct_id());
					o.put("unit_type",
							orderProduct.getUnitType() == null ? "No Info" : orderProduct.getUnitType().getValue());
					o.put("unit_count", orderProduct.getUnitCount());
					o.put("default_location", orderProduct.getDefaultLocation() == null ? "No Info"
							: orderProduct.getDefaultLocation().makeName());
//						o.put("url", orderProduct.getUrl());

					o.put("order_count", history.getOrderCount());
					o.put("store_count", history.getStoredCount());
					o.put("product", history.getProduct());
					o.put("id", history.getId());
					o.put("product_id", history.getProduct_id());
					o.put("data", productlist);
					o.put("complete", history.isComplete());
					o.put("make_company", history.getCompany().getCompanyName());
					o.put("remark", history.getRemark());
					o.put("arrive_date", history.getPlannedArriveDate().format(DateTimeFormatter.ISO_DATE));
					retlist.add(o);
				}
				object.put("result", ResultCode.SUCCESS);
				object.put("data", retlist);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			object.put("result", ResultCode.NO_RESULT);
		} catch (Exception e) {
			e.printStackTrace();
			object.put("result", ResultCode.UNKNOWN_ERROR);
		}
		return object.toJSONString();
	}

	@RequestMapping(value = "/Android/CompleteOrder", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String completeOrder(@RequestParam("id") int id, @RequestParam("userid") int userid,
			@RequestParam("count") int count, @RequestParam("printer") int printerId,
			@RequestParam(name = "location") int locationId, @RequestParam("remark") String remark) {
		JSONObject ret = new JSONObject();
		try {

			boolean isSave = orderHistoryService.completeOrder(id, userid, count, printerId, locationId,remark);
			if (isSave) {
				ret.put("result", ResultCode.SUCCESS);
			} else {
				ret.put("result", ResultCode.REQUIRE_ELEMENT_ERROR);
			}
		} catch (Exception e) {
			ret.put("result", ResultCode.UNKNOWN_ERROR);
			e.printStackTrace();
		}
		return ret.toJSONString();
	}

}
