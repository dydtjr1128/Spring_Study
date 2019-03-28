package com.dydtjr1128.restdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class HomeController {

    static Logger logger = LoggerFactory.getLogger(HomeController.class);

    // @RequestMapping(value="/", method = RequestMethod.GET)과 같은 뜻
    @GetMapping("/")
    public String home(Model model) {

        logger.debug("calling home()");

        model.addAttribute("message", "hello world");
        return "index";

    }
}