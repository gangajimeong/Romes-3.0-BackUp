package com.company.ROMES.Controller.SystemM;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.LogService;
import com.company.ROMES.dao.UserDAO;
import com.company.ROMES.entity.User;
import com.company.ROMES.functions.SessionMethod;

@Controller
public class LogController {
	
	@Autowired
	SessionFactory factory;
	
	@Autowired
	LogService logService;
	
	@Autowired
	UserDAO us;
	
	@RequestMapping(value = "/LogInfo")
	public String logInfo(Model model) {
		Session session = null;
		JSONArray arry = new JSONArray();
		try {
			session = factory.openSession();
			
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			User u = new User();
			u = us.findAdmin(session, userID);
			
			if(u != null) {
				List<User> users = session.createQuery(
						"Select this_ from User this_ where this_.enabled=true and this_.constructionCompany is Null order by this_.id")
						.getResultList();
				for (User user : users) {
					JSONObject o = new JSONObject();
					o.put("id", user.getId());
					o.put("name", user.getName());
					arry.add(o);
				}
				model.addAttribute("users", arry);
				model.addAttribute("log",logService.getData());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
//		List<User> users = userservice.users();
//		model.addAttribute("users", users);
		return "SystemM/LogInfo";
	}
	@RequestMapping(value = "/logData", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String kpiData(@RequestParam(name = "preTime") String preTime,
			@RequestParam(name = "lastTime") String lastTime, @RequestParam(name = "userid") int userid,@RequestParam String category) {
		System.out.println("here");
		return logService.getLogData(preTime, lastTime, userid, category).toJSONString();
	}
}
