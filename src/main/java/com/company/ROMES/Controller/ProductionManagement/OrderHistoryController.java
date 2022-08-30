package com.company.ROMES.Controller.ProductionManagement;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.entity.Company;
import com.company.ROMES.entity.DocAndTitle;
import com.company.ROMES.entity.OrderHistory;
import com.company.ROMES.functions.JSONParsers;
import com.company.ROMES.interfaces.service.ProductionM.OrderHistoryService;
import com.company.ROMES.interfaces.service.StandardInfo.CompanyServiceInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class OrderHistoryController {

	@Autowired
	OrderHistoryService service;
	@Autowired
	CompanyServiceInterface company;

	@RequestMapping(value = "/OrderHistory", method = RequestMethod.GET)
	public String home(Model model) {
		List<DocAndTitle> orderHistory = new ArrayList<DocAndTitle>();
		try {
			orderHistory = service.selectByDoc();
			model.addAttribute("Docs", orderHistory);
			// orderHistory = service.selectAll();
			// model.addAttribute("Orderhistorys", orderHistory);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ProductionM/OrderHistory";
	}

	@RequestMapping(value = "/CreateOrderHistory/{title}", method = RequestMethod.POST)
	public String create(@RequestParam(name = "data") String orders, @PathVariable String title) {
		String s = orders;
		List<OrderHistory> lists = new ArrayList<OrderHistory>();
		JSONParser parser = new JSONParser();
		try {

			JsonArray datas = JSONParsers.parseStingToJsonArray(s.toString());
			String[] key = { "isMaterial", "product_id", "product", "OrderCount", "price", "company.id", "orderDate",
					"plannedArriveDate" };
			String docTitle = title;
			for (int i = 0; i < datas.size(); i++) {
				JSONObject ret = (JSONObject) parser.parse(datas.get(i).toString());
//				JsonObject ret = (JsonObject) datas.get(i);
				OrderHistory order = new OrderHistory();
				System.out.println(ret);
				Company cp = new Company();
				order.setMaterial(Boolean.parseBoolean(ret.get(key[0]).toString()));
				order.setProduct_id(Integer.parseInt(ret.get(key[1]).toString()));
				order.setProduct(ret.get(key[2]).toString());
				order.setOrderCount(Integer.parseInt(ret.get(key[3]).toString()));
//				order.setPrice(Integer.parseInt(ret.get(key[4]).toString()));
				order.setPrice(0);
				order.setDocTitle(docTitle);
				order.setOrderDate(LocalDate.parse(ret.get(key[6]).toString()));
				order.setPlannedArriveDate(LocalDate.parse(ret.get(key[7]).toString()));
				cp = company.SelectCompanyById(Integer.parseInt(ret.get(key[5]).toString()));
				order.setCompany(cp);
				lists.add(order);
			}
			service.createAllOrderHistory(lists);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ProductionM/OrderHistory";
	}

	@ResponseBody
	@RequestMapping(value = "/SelectByDocNum", method = RequestMethod.GET)
	public List<OrderHistory> lists(@RequestParam("Doc") String Doc) {
		List<OrderHistory> lists = new ArrayList<OrderHistory>();
		try {
			lists = service.selectByDocNum(Doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	@ResponseBody
	@RequestMapping(value = "/updateOrderHistory/{title}", method = RequestMethod.POST)
	public boolean updateOrderHistory(@RequestParam(name = "createData") String createData,
			@RequestParam(name = "updateData") String updateData, @PathVariable String title) {
		List<OrderHistory> create = new ArrayList<OrderHistory>();
		List<OrderHistory> update = new ArrayList<OrderHistory>();
		String[] key = { "isMaterial", "product_id", "product", "OrderCount", "price", "company.id", "orderDate",
				"plannedArriveDate" };
		boolean state = false;
		try {
			JsonArray createDatas = JSONParsers.parseStingToJsonArray(createData);
			JsonArray updateDatas = JSONParsers.parseStingToJsonArray(updateData);
			if (createDatas.size() > 0) {
				for (int i = 0; i < createDatas.size(); i++) {
					JsonObject ret = (JsonObject) createDatas.get(i);
					OrderHistory order = new OrderHistory();
					Company cp = new Company();
					order.setMaterial(ret.get(key[0]).getAsBoolean());
					order.setProduct_id(ret.get(key[1]).getAsInt());
					order.setProduct(ret.get(key[2]).getAsString());
					order.setOrderCount(ret.get(key[3]).getAsInt());
//					order.setPrice(ret.get(key[4]).getAsInt());
					order.setPrice(0);
					order.setOrderDate(LocalDate.parse(ret.get(key[6]).getAsString()));
					order.setPlannedArriveDate(LocalDate.parse(ret.get(key[7]).getAsString()));
					cp = company.SelectCompanyById(ret.get(key[5]).getAsInt());
					order.setCompany(cp);
					create.add(order);
				}
				state = true;

			}
			if (updateDatas.size() > 0) {
				for (int i = 0; i < updateDatas.size(); i++) {
					System.out.println(updateDatas);
					JsonObject ret = (JsonObject) updateDatas.get(i);
					OrderHistory order = new OrderHistory();
					Company cp = new Company();
					order.setId(ret.get("id").getAsInt());
					order.setMaterial(ret.get(key[0]).getAsBoolean());
					order.setProduct_id(ret.get(key[1]).getAsInt());
					order.setProduct(ret.get(key[2]).getAsString());
					order.setOrderCount(ret.get(key[3]).getAsInt());
//					order.setPrice(ret.get(key[4]).getAsInt());
					order.setPrice(0);
					order.setDocTitle(title);
					order.setOrderDate(LocalDate.parse(ret.get(key[6]).getAsString()));
					order.setPlannedArriveDate(LocalDate.parse(ret.get(key[7]).getAsString()));
					cp = company.SelectCompanyById(ret.get(key[5]).getAsInt());
					order.setCompany(cp);
					update.add(order);
				}
				state = true;
			}
			if (state != false) {
				state = service.updateAllOrderHistory(create, update);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return state;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteEachOrderItems", method = RequestMethod.GET)
	public boolean deleteRow(@RequestParam(name = "id") int id) {
		boolean state = false;
		state = service.deleteOrderHistory(id);
		return state;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteDoc", method = RequestMethod.GET)
	public boolean deleteAll(@RequestParam(name = "DocNum") List<String> DocNum) {
		boolean state = false;
		try {
			state = service.deleteDocuments(DocNum);
		} catch (Exception e) {
			System.out.println("문서 삭제 오류");
			e.printStackTrace();
		}
		return state;
	}
	
}
