package com.company.ROMES.Controller.SystemM;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.Services.SystemM.UserInfoService;
import com.company.ROMES.entity.User;

@Controller
public class UserInfoController {
	@Autowired
	UserInfoService service;

	@RequestMapping(value = "/UserInfo", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("division", service.getDivision());
		model.addAttribute("info", service.userInfo());
		return "SystemM/UserInfo";
	}

	@RequestMapping(value = "/checkCurrentPW", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkCurrentPW(@RequestParam(name = "data") String pw) {
		return service.checkPW(pw);
	}

	@RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean modifyInfo(@ModelAttribute User user) {
		
		return service.modifyInfo(user);
	}
	
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	public boolean modifyPassword(@RequestParam(name="newPassword") String newPassword) {
		return service.modifyPassword(newPassword);
	}
	
	@RequestMapping(value="/infoFindDivision",method=RequestMethod.GET)
	@ResponseBody
	public int findDivision() {
		return service.findDivision();
	}
}
