package com.company.ROMES.Controller.StandardInfo;

import java.time.LocalDateTime;
import java.util.List;

import org.jboss.logging.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.entity.ErrorCode;
import com.company.ROMES.interfaces.service.StandardInfo.ErrorCodeServiceInterface;

@Controller
public class ErrorCodeController {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	ErrorCodeServiceInterface ec;
	@RequestMapping(value = "/ErrorCode", method = RequestMethod.GET)
	public String ErrorCodes(Model model) {
		try {
			List<ErrorCode>lists = ec.selectAllErrorCode();
			model.addAttribute("ErrorCodes", lists);
		} catch (Exception e) {
			for(StackTraceElement ste: e.getStackTrace()) {
				logger.error(ste.toString());
			}
		}
		return "StandardInfo/ErrorCode";
	}
	@RequestMapping(value = "/ResultCode", method = RequestMethod.GET)
	public String ErrorCode(Model model) {
		try {
			List<ErrorCode>lists = ec.selectAllErrorCode();
			model.addAttribute("ErrorCodes", lists);
		} catch (Exception e) {
			for(StackTraceElement ste: e.getStackTrace()) {
				logger.error(ste.toString());
			}
		}
		return "StandardInfo/ResultCode";
	}
	@RequestMapping(value = "/SelectErrorCode", method = RequestMethod.GET)
	@ResponseBody
	public ErrorCode SelectError(@RequestParam(name = "id")int id) {
		ErrorCode code = null;
		try {
			code = ec.selectErrorCodeById(id);
		} catch (Exception e) {
			for(StackTraceElement ste: e.getStackTrace()) {
				logger.error(ste.toString());
			}
		}
		return code;
	}
	@RequestMapping(value = "/createErrorCode", method = RequestMethod.POST)
	public String create(@ModelAttribute ErrorCode code) {
		
		try {
			ec.createErrorCode(code);

		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}

		return "redirect:/main";
	}
	@RequestMapping(value = "/updateErrorCode", method = RequestMethod.POST)
	@ResponseBody
	public ErrorCode update(@RequestBody ErrorCode code) {
		boolean state = false;
		LocalDateTime create = LocalDateTime.MIN;
		try {
			create = ec.selectErrorCodeById(code.getId()).getCreateDate();
			code.setCreateDate(create);
			state = ec.updateErrorCode(code);
			} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return code;
	}
	@RequestMapping(value = "/deleteErrorCode", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject delete(@RequestParam(name = "id")int id) {
		int deleteProduct = 0;
		boolean state = false;
		JSONObject ret = new JSONObject();
		try {
			state = ec.deleteErrorCode(id);
			ret.put("ret", state);
			ret.put("id", id);	
		} catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		return ret;
	}
	
	
	
}
