package com.company.ROMES.Controller.TabSys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TabController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String startPage() {
		return "login";
	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String returnPage() {
		return "login";
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String Tab() {
		return "statics/Main";
	}
}
