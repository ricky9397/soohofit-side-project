package com.project.soohofit.user.login.ctrl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserLoginController {

    @GetMapping("/login/loginForm.do")
    public String loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "user/login/loginForm";
    }


}
