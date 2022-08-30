package com.company.ROMES.Controller.SystemM;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SettingController {

	@RequestMapping(value = "/Setting", method = RequestMethod.GET)
	public String home(Model model) {
		return "SystemM/Setting";
	}
}
