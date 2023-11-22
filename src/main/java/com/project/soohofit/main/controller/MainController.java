package com.project.soohofit.main.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Log4j2
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
