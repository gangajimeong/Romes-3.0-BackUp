package com.company.ROMES.Controller.StandardInfo;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.entity.LocationMaster;
import com.company.ROMES.interfaces.service.StandardInfo.LocationMasterService;

@Controller
public class LocationMasterConroller {
	@Autowired
	LocationMasterService ls;

	@RequestMapping(value = "/LocationMaster", method = RequestMethod.GET)
	public String LocationMaster(Model model) {
		List<LocationMaster> lists = new ArrayList<LocationMaster>();
		try {
			lists = ls.SelectTop();
			model.addAttribute("Locations", lists);
			model.addAttribute("prints",ls.printList());
//			System.out.println(ls.printList().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "StandardInfo/LocationMaster";
	}

	@RequestMapping(value = "/LocationCreate", method = RequestMethod.POST)
	public String create(@ModelAttribute LocationMaster lm, @RequestParam(name = "img") MultipartFile file) {
		boolean state = false;
		String fileName = null;
		if (!file.isEmpty()) {
			state = ls.SaveLocationImg(file);
			fileName = file.getOriginalFilename();
		}
		try {

			ls.createLocation(lm, fileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/main";
	}
	@RequestMapping(value = "/LocationUpdate", method = RequestMethod.POST)
	public String update(@ModelAttribute LocationMaster lm, @RequestParam(name = "imgs") MultipartFile file) {
		boolean state = false;
		String fileName = null;
		if (!file.isEmpty()) {
			state = ls.SaveLocationImg(file);
			fileName = file.getOriginalFilename();
		}
		try {
			System.out.println(fileName);
			ls.updateLocation(lm, fileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/main";
	}
	@ResponseBody
	@RequestMapping(value = "/LocationDelete", method = RequestMethod.GET)
	public boolean Delete(@RequestParam(name = "id")List<Integer>id) {
		boolean state = false;
		try {
			for(int i =0; i< id.size();i++) {
				System.out.println(id.get(i)+" ");
				state = ls.deleteLocation(id.get(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@ResponseBody
	@RequestMapping(value = "/getLocationTypeCode", method = RequestMethod.POST)
	public List<CommonCode> TypeList() {
		List<CommonCode> lists = new ArrayList<CommonCode>();
		try {
			lists = ls.SelectAboutLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	@RequestMapping(value = "/getLocationLists", method = RequestMethod.POST)
	@ResponseBody
	public List<LocationMaster> lists() {
		List<LocationMaster> lists = new ArrayList<LocationMaster>();
		try {
			lists = ls.SelectAllbyfalse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	@ResponseBody
	@RequestMapping(value = "/getTopAndSubLocationLists", method = RequestMethod.POST)
	public JSONObject getTopAndSubLocationLists(@RequestParam(name = "id") int id) {
		JSONObject ret = new JSONObject();
		try {
			ret.put("Subs", ls.SelectSubLocation(id));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(value = "/printBarcode", method = RequestMethod.GET)
	public JSONObject printBarcode(@RequestParam(name = "id")int id, @RequestParam(name = "printName")String printName) {
		return ls.printBarcode(id, printName);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPrintList", method = RequestMethod.GET)
	public JSONArray getPrintList() {
		return ls.printList();
	}
	

}
