package com.company.ROMES.Controller.ProductionManagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections4.map.HashedMap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.KPIService;
import com.company.ROMES.entity.DocAndTitle;
import com.company.ROMES.entity.User;
import com.company.ROMES.entity.WorkOrderInfo;
import com.company.ROMES.entity.WorkingLine;
import com.company.ROMES.functions.SessionMethod;

@Controller
public class KPIController {
	@Autowired
	SessionFactory factory;
	
	@Autowired
	KPIService Kpiservice;

	@RequestMapping(value = "/Capa", method = RequestMethod.GET )
	public String designerCapa(Model model) {
		Session session = null;
		JSONArray arry = new JSONArray();

		try {
			session = factory.openSession();
//			List<User> users = session.createQuery(
//					"Select this_ from User this_ where this_.enabled=true and this_.constructionCompany is Null order by this_.id")
//					.getResultList();
//			for (User user : users) {
//				JSONObject o = new JSONObject();
//				o.put("id", user.getId());
//				o.put("name", user.getName());
//				arry.add(o);
//			}
			List<WorkingLine> line = session.createQuery("Select this_ from WorkingLine this_ where this_.isDisabled = false order by this_.id").getResultList();
			for(WorkingLine lines : line) {
				JSONObject o = new JSONObject();
				o.put("id", lines.getId());
				o.put("name", lines.getLine());
				arry.add(o);
			}
			
			model.addAttribute("lines", arry);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return "ProductionM/DesignerCAPA";
	}

	@RequestMapping(value = "/CapaData", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String capaData(@RequestParam(name = "preTime") String preTime,
			@RequestParam(name = "lastTime") String lastTime, @RequestParam(name = "lineid") int lineid) {
		return Kpiservice.getCapaData(preTime, lastTime, lineid).toString();
	}

	@RequestMapping(value = "/kpipage", method = RequestMethod.GET)
	public String kpiPage(Model model) {
		Session session = null;
		try {
			session = factory.openSession();
			List<WorkingLine> lines = session.createQuery("Select this_ from WorkingLine this_ where this_.isDisabled= false order by this_.id").getResultList();
			JSONArray arry = new JSONArray();
			for(WorkingLine line: lines) {
				JSONObject o = new JSONObject();
				o.put("id", line.getId());
				o.put("name", line.getLine());
				arry.add(o);
			}
			model.addAttribute("printers", arry);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			SessionMethod.closeSession(session);
		}
		return "ProductionM/KPIpage";
	}
	@RequestMapping(value = "/KpiData", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String kpiData(@RequestParam(name = "preTime") String preTime,
			@RequestParam(name = "lastTime") String lastTime, @RequestParam(name = "printerid") int printerid) {
		return Kpiservice.getKPIData(preTime, lastTime, printerid).toString();
	}

}
