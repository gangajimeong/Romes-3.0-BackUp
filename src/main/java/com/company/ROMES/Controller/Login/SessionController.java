package com.company.ROMES.Controller.Login;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.ROMES.LoggerUtil;
import com.company.ROMES.Services.SystemM.SessionService;
import com.company.ROMES.entity.User;

@Controller
public class SessionController {

	/*
	 * @RequestMapping(value = "/login", method = RequestMethod.GET)
	 * 
	 * public String login() { System.out.println("접근 됨"); return "statics/login"; }
	 */

	@Autowired
	SessionService service;
	
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(@ModelAttribute User user,Model model) {
		model.addAttribute("division",service.getDivision());
		System.out.println("here");
		return "signup";

	}

	@RequestMapping(value = "/findUserInfo", method = RequestMethod.GET)
	public String findUserInfo() {

		
		return "findInfo";

	}

	@RequestMapping(value = "/readyPage", method = RequestMethod.GET)
	public String readyPage() {
		return "readyPage";

	}
}
