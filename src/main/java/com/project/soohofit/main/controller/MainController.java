package com.project.soohofit.main.controller;

import com.project.soohofit.common.response.CommonResponse;
import com.project.soohofit.common.response.DataResponse;
import com.project.soohofit.common.response.ResponseStatus;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
public class MainController {

    private Logger logger = LogManager.getLogger(MainController.class);

    @Value("${postgre.url}")
    private String url;

    @GetMapping("/")
    public String about() throws Exception {
        return "about";
    }

    @GetMapping("/main.do")
    public String home() throws Exception{
        return "main/main";
    }

    @GetMapping("/ajax")
    @ResponseBody
    public CommonResponse ajax() {
        Map<String, Object> data = new HashMap<>();
        data.put("result", url);
        return DataResponse.of(ResponseStatus.SUCCESS, data);
    }

}
