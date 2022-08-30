package com.company.ROMES.Controller.Android;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.LoggerUtil;
import com.company.ROMES.Services.NoticeService;
import com.sun.istack.logging.Logger;

@Controller
public class AddonsController {

	
	@Autowired
	NoticeService noticeService;

	@RequestMapping(value = "/Android/addNotice", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String addNotice(@RequestParam(name="userid")int userId,@RequestParam(name="title")String title,@RequestParam(name="msg")String msg) {
		String tmp = noticeService.addNotice(title, msg, userId).toJSONString();
		return tmp;
	}
}
