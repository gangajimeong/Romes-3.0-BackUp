package com.company.ROMES.Controller.ProductionManagement;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.company.ROMES.entity.Design;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.LatLng;
import com.company.ROMES.entity.ReceivedOrderInfo;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.functions.webUpload;
import com.company.ROMES.interfaces.service.ReceivedOrderHistoryInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

@Controller
public class OrderInfoConstroller {
	@Autowired
	ReceivedOrderHistoryInterface service;

	@Autowired
	ApplicationEventPublisher publisher;
	@Autowired
	webUpload upload;

	@RequestMapping(value = "/OrderInfo", method = RequestMethod.GET)
	public String home(Model model) {
		try {
			model.addAttribute("lists", service.receivedOrderInfos());
			model.addAttribute("brands", service.getBrandList());
			model.addAttribute("companys", service.getCompanyList());
			model.addAttribute("datas",service.workOrderInfos());
//			model.addAttribute("branch", service.getlatlng());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ProductionM/orderInfo";
	}
	@ResponseBody
	@RequestMapping(value = "/selectOrderInfoById", method = RequestMethod.GET)
	public JSONArray selectOrderInfoById(@RequestParam int id) {
		return service.selectOrderInfoById(id);

	}
	@ResponseBody
	@RequestMapping(value = "/selectOrderInfoByDate", method = RequestMethod.GET)
	public JSONArray selectOrderInfoById(@RequestParam(name = "start")LocalDateTime start,@RequestParam(name = "end")LocalDateTime end) {
		return service.selectOrderInfoByDate(start, end);

	}
	
	@ResponseBody
	@RequestMapping(value = "/createOrderBasicInfo", method = RequestMethod.POST)
	public boolean createReceivedOrderInfo(@ModelAttribute ReceivedOrderInfo info) {
		return service.createOrderInfo(info);

	}
	
	@ResponseBody
	@PostMapping(value = "/updateOrderBasicInfo")
	public int updateOrderBasicInfo(@ModelAttribute ReceivedOrderInfo info) {
		return service.updateOrderInfo(info);
	}
	
	@ResponseBody
	@GetMapping(value = "/deleteOrderBasicInfo")
	public boolean deleteOrderBasicInfo(@RequestParam(name = "id") int id) {
		return service.deleteOrderInfo(id);
	}
	
	@ResponseBody
	@PostMapping(value = "/createWorkOrderInfo")
	public boolean createWorkOrderInfo(@RequestParam("data")String datas, @RequestParam("orderInfo")String info) {
		
		boolean state = false;
		Gson gson = new Gson();
		JsonArray ret = gson.fromJson(datas, JsonArray.class);
		JsonArray infos = gson.fromJson(info, JsonArray.class);
		int workOrderId = 0;
		
		try {
			for(int i = 0; i<ret.size(); i++) {
				workOrderId = service.createWorkOrderInfo(ret.get(i).getAsJsonArray(),infos);	//작업지시 생성		작업지시 id 리턴
//				service.createConstructionPlan(workOrderId);
				state = true;
			}
			publisher.publishEvent("refresh");
		} catch(Exception e) {
			state = false;
			e.printStackTrace();
		}
		
		return state;
	}
	
	 //기간검색시 데이터를 받아오기 위한 부분
	@RequestMapping(value = "/orderData", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String kpiData(@RequestParam(name = "preTime") String preTime,
			@RequestParam(name = "lastTime") String lastTime) {
		JSONArray ret = service.period(preTime, lastTime);
		System.out.println(ret.size());
		return ret.toJSONString();
	}
	
	@RequestMapping(value="/DesignRegist", method = RequestMethod.POST)
	public String RegistSample(@RequestParam(name = "img") MultipartFile file ,@RequestParam(name = "inputId") int id) {
		System.out.println(id);
		String fileName = null;
		String dir = null;
		if (!file.isEmpty()) {
			dir = service.SaveSampleImg(file,id);
			fileName = file.getOriginalFilename();
		}
		try {
			service.registDesign(dir, id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/main";
	}
	
	@ResponseBody
	@PostMapping(value = "/importExcel")
	public String excedlImport(@RequestParam("Company")String datas) {
		
		Gson gson = new Gson();
		JsonArray ret = gson.fromJson(datas, JsonArray.class);
		System.out.println(ret.size());
		for(int i = 0; i<ret.size(); i++) {
			service.importExcel(ret.get(i).getAsJsonObject());
		}
//		System.out.println(ret.get(0).getAsJsonObject().get("companyName"));
		return "redirect:/main";
	}
	
	@ResponseBody
	@GetMapping(value = "/setWorkorderBrand")
	public JSONArray setWorkorderBrand(@RequestParam(name = "id") int id) {
		JSONArray ret = service.setWorkorderBrand(id);
		return ret;
	}
	
	@ResponseBody
	@GetMapping(value = "/orderFindBrand")
	public JSONArray orderFindBrand(@RequestParam(name = "id") int id) {
		JSONArray ret = service.orderFindBrand(id);
		return ret;
	}
	
	@ResponseBody
	@GetMapping(value="/updateOrderFindBrand")
	public JSONArray updateOrderFindBrand(@RequestParam(name ="id") int id) {
		JSONArray ret = service.updateOrderFindBrand(id);
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(value ="/deleteWorkOrder", method = RequestMethod.POST)
	public boolean deleteWorkOrder(@RequestParam(name = "id") int id) {
		boolean state = service.deleteWorkOrder(id);
		return state;
	}
	
//	@ResponseBody
//	@PostMapping(value = "/createWorkOrder")
//	public JSONArray createWorkOrder(@RequestParam(name = "brand") String brand, @RequestParam(name = "title") String title
//			,@RequestParam(name = "product") String product, @RequestParam(name = "iscoating") boolean iscoating
//			,@RequestParam(name = "isback") boolean isback, @RequestParam(name = "manufacturing") String manufacturing
//			,@RequestParam(name = "isconstruction") boolean isconstruction) {
//		return service.createWorkOrder();
//	}
	
	
	
//
//	@ResponseBody
//	@RequestMapping(value = "getReceivedOrderInfo/{id}", method = RequestMethod.GET)
//	public JSONObject getReceivedOrderInfo(@PathVariable int id) {
//
//		JSONObject ret = new JSONObject();
//		try {
//			ret = service.getProductsAndImages(id);
//			JSONArray planData = service.selectConstructionPlan(id);
//			;
//			ret.put("ConstructionPlan", planData);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ret;
//	}
//
//	@ResponseBody
//	@GetMapping(value = "/imageView", produces = { MediaType.IMAGE_JPEG_VALUE, "text/plain;charset=UTF-8" })
//	public ResponseEntity<byte[]> userSearch(@RequestParam("imagename") String imagename) throws IOException {
//		return upload.imageView(imagename, "/Desktop/Suju/");
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/createReceivedOrderInfo", method = RequestMethod.POST)
//	public JSONObject createReceivedOrderInfo(@RequestParam HashMap<Object, Object> info,
//			@RequestParam(name = "products") String products, @RequestParam(name = "file") List<MultipartFile> files) {
//		JSONObject data = new JSONObject();
//		List<String> urls = new ArrayList<String>();
//		try {
//			// 수주 데이터 처리
//			info.remove("products");
//			// 업로드 처리
//			urls = upload.uploadAndGetUrls(files, "/Desktop/Suju/");
//			data = service.createReceivedOrderInfo(info, products, urls);
//			data.put("title", info.get("title").toString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return data;
//
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "getConstructionDivisionList", method = RequestMethod.GET)
//	public List<Division> getConstructionDivisionList() {
//		List<Division> lists = new ArrayList<Division>();
//		try {
//			lists = service.lists();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return lists;
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "createLatLng", method = RequestMethod.POST)
//	public LatLng createLatLng(@ModelAttribute LatLng location) {
//		LatLng newLocation = new LatLng();
//		try {
//			newLocation = service.createLatLng(location);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return newLocation;
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "createConstructionPlan", method = RequestMethod.POST)
//	public JSONObject createConstructionPlan(@RequestParam HashMap<String, String> ConsData,
//			@RequestParam(name = "latlng") List<Integer> lats) {
//		JSONObject cons = new JSONObject();
//		try {
//			int suju = Integer.parseInt(ConsData.get("receivedOrderInfo.id"));
//			int division = Integer.parseInt(ConsData.get("constructionDivision.id"));
//			LocalDate plan = LocalDate.parse(ConsData.get("planConstructionDate"));
//			cons = service.createConstructionPlan(suju, division, plan, lats);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return cons;
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/updateRecivedOrderInfo", method = RequestMethod.POST)
//	public boolean updateRecivedOrderInfo(@RequestParam HashMap<Object, Object> info,
//			@RequestParam(name = "products") String products, @RequestParam(name = "urls") List<Integer> urls) {
//		boolean state = false;
//		try {
//			// 수주 데이터 처리
//			info.remove("products");
//			info.remove("urls");
//			state = service.updateReceivedOrderInfo(info, products, urls);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return state;
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/deleteRecivedOrderInfo", method = RequestMethod.GET)
//	public boolean updateRecivedOrderInfo(@RequestParam(name = "id") int id) {
//		boolean state = false;
//		try {
//			service.deleteReceivedOrderInfo(id);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return state;
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/selectConstructionPlanLocationList", method = RequestMethod.GET)
//	public JSONArray selectConstructionPlanLocationList(@RequestParam(name = "id") int id) {
//		JSONArray ret = new JSONArray();
//		try {
//			ret = service.planLocationList(id);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ret;
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/updateConstructionPlan", method = RequestMethod.POST)
//	public boolean updateConstructionPlan(@RequestParam HashMap<Object, Object> plan,
//			@RequestParam(name = "addLocation") List<Integer> latIds,
//			@RequestParam(name = "consLocation") List<Integer> conslocationList) {
//		boolean state = false;
//		try {
//			state = service.updateConstructionPlan(plan, latIds, conslocationList);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return state;
//	}
//
//	@ResponseBody
//	@RequestMapping(value = "/deleteConstructionPlan", method = RequestMethod.GET)
//	public boolean deleteConstructionPlan(@RequestParam(name = "id") int id) {
//		boolean state = false;
//		try {
//			state = service.deleteConstructionPlan(id);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return state;
//	}

}
