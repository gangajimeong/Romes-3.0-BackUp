package com.company.ROMES.Controller.StandardInfo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.ROMES.dao.CommonCodeDAO;
import com.company.ROMES.entity.CommonCode;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CommonInfo {

	private static final Logger logger = LoggerFactory.getLogger(CommonInfo.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	


	@RequestMapping(value = "/DivisionManagement", method = RequestMethod.GET)
	public String businessMangement() {
		return "StandardInfo/DivisionManagement";
	}


}
