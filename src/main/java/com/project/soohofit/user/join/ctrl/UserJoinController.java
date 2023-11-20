package com.project.soohofit.user.join.ctrl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserJoinController {

    @GetMapping("/join/joinForm.do")
    public String loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "user/join/joinForm";
    }

}
