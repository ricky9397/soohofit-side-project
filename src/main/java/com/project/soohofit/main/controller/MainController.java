package com.project.soohofit.main.controller;

import com.project.soohofit.common.minio.FileUtil;
import com.project.soohofit.common.redis.RedisUtil;
import com.project.soohofit.common.response.CommonResponse;
import com.project.soohofit.common.response.DataResponse;
import com.project.soohofit.common.response.ResponseStatus;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
public class MainController {

    private Logger logger = LogManager.getLogger(MainController.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    FileUtil fileUtil;

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

    @RequestMapping("/ajax")
    @ResponseBody
    public CommonResponse ajax(/*@RequestParam("file") MultipartFile file*/) {
        Map<String, Object> data = new HashMap<>();

        redisUtil.setValues("set", "value", Duration.ofSeconds(60));
        String val = redisUtil.getValues("set");
        log.info("###### redis value :: ");
        redisUtil.deleteValues("set");

        return DataResponse.of(ResponseStatus.SUCCESS, "result", val);
    }

}
