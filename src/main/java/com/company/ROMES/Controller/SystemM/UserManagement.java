package com.company.ROMES.Controller.SystemM;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.ROMES.ScriptUtils.ScriptUtils;
import com.company.ROMES.Services.UserService;
import com.company.ROMES.Services.SystemM.AuthControllService;
import com.company.ROMES.entity.User;
import com.company.ROMES.interfaces.service.UserServiceInterface;
import com.company.ROMES.interfaces.service.SystemM.AuthConrollInterface;


@Controller
public class UserManagement {
	@Autowired
	UserServiceInterface  userservice;

	@Autowired
	AuthConrollInterface authControllService;

	@RequestMapping(value = "/UserManagement")
	public String users(Model model) {
		List<User> users = userservice.users();
		model.addAttribute("users", users);
		return "SystemM/UserManagement";
	}
	
	@RequestMapping(value = "/admins/role/{userId}/{role}")
	public String changeRole(@PathVariable int userId, @PathVariable String role, HttpServletResponse response) throws IOException {
		boolean state = false;
		state = authControllService.updateAuth(userId, role);
		if(state == false) {
			ScriptUtils.alert(response, "권한 변경 실패");
			}
		return "redirect:/main";
	}
	@RequestMapping(value = "/UserManagement/userInfoDelete/{userId}")
	public String deleteUser(@PathVariable int userId, HttpServletResponse response) throws IOException  {
		boolean state = false;
		state=authControllService.removeUser(userId);
		if(state == false) {
		ScriptUtils.alert(response, "유저 삭제 실패");
		}
		
		return "redirect:/main";
	}
	

}
