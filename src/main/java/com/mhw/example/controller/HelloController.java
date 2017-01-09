package com.mhw.example.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mhw.example.model.UserIsmBean;
import com.mhw.example.service.UserIsmService;

/**
 * Created by mahw on 2017/1/4.
 */
@Controller
public class HelloController {

	Logger logger = Logger.getLogger(HelloController.class);

	@Autowired
	UserIsmService userService;
	
	@RequestMapping("/")
    public String index() {
        return "home";
    }
	
	@RequestMapping("/home")
    public String home() {
        return "home";
    }

	@RequestMapping(value = "/hello", method = { RequestMethod.GET })
	public String hello(Model model) {
		return "hello";
	}
	
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String login(Model model){
		return "login";
	}
	
    @RequestMapping(value = "/test", method = { RequestMethod.GET })
    public String test(Model model) {
        UserIsmBean userBean = userService
            .getByUserId("014cd3a0-fde1-4c75-9545-4c62b06e4c69");
        model.addAttribute("user", userBean);
        logger.info("12111111111111");
        return "test";
    }
	
}
