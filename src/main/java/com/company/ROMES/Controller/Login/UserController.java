package com.company.ROMES.Controller.Login;




import java.util.List;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.company.ROMES.Services.UserService;
import com.company.ROMES.entity.Division;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.User;

@Controller
public class UserController {
	
	@Autowired
	UserService service;

	/*
	 * @RequestMapping(value = "/login", method = RequestMethod.POST) public String
	 * login() {
	 * 
	 * return "statics/login"; }
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String create(@ModelAttribute @Valid User user, BindingResult result) {
		if (result.hasErrors()) {
	       
	        return "signup";
	    }
		service.createUser(user);
		return "login";
	}
	@RequestMapping(value = "/duplicationCheck", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject duplicationCheck(@RequestParam(name = "loginId")String LoginId) {
		JSONObject ret = service.checkId(LoginId);
		return ret;
	}
	@RequestMapping(value = "/findUserId", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject findUserId(@RequestParam(name = "name")String name,@RequestParam(name = "phone")String phoneNum,@RequestParam(name = "email")String email) {
		JSONObject ret = service.findUser(name, phoneNum, email);
		return ret;
	}
	@RequestMapping(value = "/isUser", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject isUser(@RequestParam(name = "name")String name,@RequestParam(name = "phone")String phoneNum,@RequestParam(name = "email")String email,@RequestParam(name="id")String id) {
		JSONObject ret = service.findUser(name, phoneNum, email, id);
		return ret;
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject changePassword(@RequestParam(name="userid")int userid,@RequestParam(name="pw")String newPw) {
		JSONObject ret = service.resetPassword(userid, newPw);
		return ret;
	}
	
//	@RequestMapping(value = "/signup", method = RequestMethod.GET)
//	public String home(Model model) {
//		
//		try {
//			model.addAttribute("location",service.getDivision());
//		} catch (Exception e) {
//			e.printStackTrace();
//			}
//		return "StandardInfo/MaterialMaster";
//	}
}
