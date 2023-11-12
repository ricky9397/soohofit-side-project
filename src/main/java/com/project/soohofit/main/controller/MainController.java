package com.project.soohofit.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String about() throws Exception {
        return "about";
    }

    @GetMapping("/main.do")
    public String home() throws Exception{
        return "main/main";
    }


}
