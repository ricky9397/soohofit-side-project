package com.project.soohofit.user.login.ctrl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@Controller
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserLoginController {

    @GetMapping("/login/loginForm")
    public String loginForm() throws Exception {
        return "user/login/loginForm";
    }

//    @PostMapping("/login/login")
//    public String login() throws Exception {
//        return "user/login/loginForm";
//    }


}
