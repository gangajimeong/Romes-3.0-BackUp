package com.company.ROMES.Controller.StandardInfo;

import java.util.List;


import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.ROMES.Services.StandardInfo.CommonCodeService;
import com.company.ROMES.entity.CommonCode;

import Error_code.ResultCode;

@Controller
public class CommonCodeController {
	Logger logger = Logger.getLogger("CommonCodeController");
	@Autowired
	CommonCodeService commonCodeService;
	
	// CommonCode Page view
	@RequestMapping(value = "/commonCode", method = RequestMethod.GET)
	public String commonCode(Model model) {
		try {
			List<CommonCode> commonCodes = commonCodeService.selectCommonCodeByFalse();
			if (commonCodes == null) {
				// DB Connection 오류
				model.addAttribute("Error", "DB ERROR" + ":" + ResultCode.DB_CONNECT_ERROR);
				return "StandardInfo/commonCode";

			}
			model.addAttribute("commonCodes", commonCodes);
			return "StandardInfo/commonCode";
		} catch (Exception e) {
			// 요기에 오류페이지 써 놓으면 좋을듯
			String ErrorMsg = e.getMessage();
			model.addAttribute("Error", ErrorMsg);
			return "StandardInfo/commonCode";
		}

	}
	@RequestMapping(value = "/CommonCodeList" , method = RequestMethod.POST)
	@ResponseBody
	public List<CommonCode>lists(){
		List<CommonCode>CommonCodeIdList =null;
		try {
			CommonCodeIdList= commonCodeService.selectCommonCodeByFalse();
		}catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		
		
		return CommonCodeIdList;
		
	}
	@RequestMapping(value = "/types" , method = RequestMethod.GET)
	@ResponseBody
	public List<String[]>types(){
		
		List<String[]>types =null;
		try {
			types = commonCodeService.getTypes();
		}catch (Exception e) {
			for (StackTraceElement s : e.getStackTrace()) {
				logger.error(s.toString());
			}
		}
		
		
		return types;
		
	}

	@RequestMapping(value = "/createCommonCode", method = RequestMethod.POST)
	public String create(@ModelAttribute CommonCode commoncode, RedirectAttributes flash) {

		boolean result = commonCodeService.createCommonCode(commoncode);
		if (!result) {
			// DB 저장 실패
			flash.addFlashAttribute("Error", "Create Error" + ":" + ResultCode.DB_CONNECT_ERROR);
		}
		return "redirect:/main";
	}

	@RequestMapping(value = "/updateCommonCode", method = RequestMethod.POST)
	public String update(@ModelAttribute CommonCode commoncode, RedirectAttributes flash) {
		
		boolean result = commonCodeService.updateCommonCode(commoncode);
		if (!result) {
			// DB 저장 실패
			flash.addFlashAttribute("Error", "Update Error" + ":" + ResultCode.DB_CONNECT_ERROR);
		}
		return "redirect:/main";
	}

	@RequestMapping(value = "/deleteCommonCode/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable int id, RedirectAttributes flash) {
		CommonCode commoncode = commonCodeService.SelectCommonCodeById(id);
		boolean result = commonCodeService.deleteCommonCode(commoncode);
		if (!result) {

			flash.addFlashAttribute("Error", "Delete Error" + ":" + ResultCode.DB_CONNECT_ERROR);

		}
		return "redirect:/";
	}

	@RequestMapping(value = "/SelectDeleteCommonCode", method = RequestMethod.GET)
	@ResponseBody
	public boolean SelectDelete(@RequestParam(name = "chkArr") List<Integer> chkArr) {
		boolean result = false;
		for (int i : chkArr) {
			CommonCode commoncode = commonCodeService.SelectCommonCodeById(i);
			 result = commonCodeService.deleteCommonCode(commoncode);
		}
		return result;
	}

}
