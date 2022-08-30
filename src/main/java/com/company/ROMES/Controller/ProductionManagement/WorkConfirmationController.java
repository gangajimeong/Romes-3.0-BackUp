package com.company.ROMES.Controller.ProductionManagement;

import com.company.ROMES.Services.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class WorkConfirmationController {

    @Autowired
    NoticeService noticeService;

    @RequestMapping(value = "/WorkConfirmation", method = RequestMethod.GET)
    public String WorkConfirmation(Model model) {


        return "ProductionM/WorkConfirmation";
    }
}
