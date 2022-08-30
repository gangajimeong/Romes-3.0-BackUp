package com.company.ROMES.Controller.StandardInfo;

import java.time.LocalDateTime;
import java.util.List;

import org.jboss.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.Product;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.interfaces.service.StandardInfo.MeterialServiceInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

@Controller
public class MaterialController {
	@Autowired
	MeterialServiceInterface ms;
	Logger logger = Logger.getLogger("MaterialController");
	
	@RequestMapping(value = "/MaterialMaster", method = RequestMethod.GET)
	public String home(Model model) {
		List<Material>lists = null;
		try {
			lists = ms.selectAll();
			model.addAttribute("Materials",lists);
			model.addAttribute("location",ms.getlocation());
			model.addAttribute("prints",ms.printList());
		} catch (Exception e) {
			for(StackTraceElement ste: e.getStackTrace()) {
				logger.error(ste.toString());
			}
		}
		return "StandardInfo/MaterialMaster";
	}
	@RequestMapping(value = "/createMaterial", method = RequestMethod.POST)
	public String createMaterial(@ModelAttribute Material m) {
		
		try {
			ms.createMaterial(m);

		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}

		return "redirect:/main";
	}
	@ResponseBody
	@PostMapping(value = "/updateMaterial")
	public boolean updateMaterial(@ModelAttribute Material m) {
		return ms.updateMaterial(m);
	}	
	@RequestMapping(value = "/deleteMaterial", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject deleteMaterial(@RequestParam(name = "id")int id) {
		boolean state = false;
		JSONObject ret = new JSONObject();
		try {
			state = ms.deleteMaterial(id);
			System.out.println(state);
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
	@RequestMapping(value = "/printMaterial", method = RequestMethod.GET)
	public JSONObject printBarcode(@RequestParam(name = "id")int id, @RequestParam(name = "printName")String printName) {
		System.out.println("here");
		return ms.printBarcode(id, printName);
	}
	@ResponseBody
	@RequestMapping(value = "/selectMaterialById", method = RequestMethod.GET)
	public JSONArray selectMaterialById(@RequestParam int id) {
		return ms.selectMaterialById(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/MaterialLists", method = RequestMethod.GET)
	public List<Material> getMaterialLists(){
		List<Material> list = null;
		try {
			list = ms.getMaterialList();
		}catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return list;
	}
	
	@ResponseBody
	@PostMapping(value = "/materialExcel")
	public String excedlImport(@RequestParam("Material")String datas) {
		boolean state = false;
		int fail = 0;
		Gson gson = new Gson();
		JsonArray ret = gson.fromJson(datas, JsonArray.class);
		System.out.println(ret.size());
		for(int i = 0; i<ret.size(); i++) {
			state = ms.importExcel(ret.get(i).getAsJsonObject());
		}
		
		return "redirect:/main";
	}
}
