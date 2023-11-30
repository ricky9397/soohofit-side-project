package com.project.soohofit.user.login.ctrl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
@Controller
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserLoginController {

    @GetMapping("/login/loginForm")
    public String loginForm() throws Exception {
        return "user/login/loginForm";
    }

}
