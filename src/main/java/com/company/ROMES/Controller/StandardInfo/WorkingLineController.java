package com.company.ROMES.Controller.StandardInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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

import com.company.ROMES.entity.ProcessMaster;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.interfaces.dao.StandardInfo.WorkingLineDAOInterface;
import com.company.ROMES.interfaces.service.StandardInfo.WorkingLineServiceInterface;

@Controller
public class WorkingLineController {
	Logger logger = Logger.getLogger("WorkingLineController");
	@Autowired
	WorkingLineServiceInterface ws;
	
	@RequestMapping(value = "/WorkingLine", method = RequestMethod.GET)
	public String home(Model model) {
		List<WorkingLine>lists = null;
		try {
			
			lists = ws.SelectAllByFalse();
//			model.addAttribute("WorkingLines",lists);
			model.addAttribute("line",ws.getWorkingLine());
			model.addAttribute("location",ws.getLocation());
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return "StandardInfo/WorkingLine";	
	}
	@RequestMapping(value = "/createWL", method = RequestMethod.POST)
	public String createWL(@ModelAttribute WorkingLine w) {
		ws.createWL(w);
		
		return "redirect:/main";
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectWLById", method = RequestMethod.GET)
	public JSONArray selectWLById(@RequestParam int id) {
		return ws.selectWLById(id);
	}
	
	@ResponseBody
	@PostMapping(value = "/updateWL")
	public boolean updateWL(@ModelAttribute WorkingLine w) {
		return ws.updateWL(w);
	}
	
	@ResponseBody
	@GetMapping(value = "/deleteWL")
	public JSONObject deleteWL(@RequestParam(name = "id") int id) {
		return ws.deleteWL(id);
	}
	
}
