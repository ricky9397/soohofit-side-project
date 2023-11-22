package com.project.soohofit.user.login.ctrl;

import com.project.soohofit.common.config.security.service.UserService;
import com.project.soohofit.common.jwt.JwtTokenProvider;
import com.project.soohofit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserLoginController {

//    @GetMapping("/login/loginForm")
//    public String loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        return "user/login/loginForm";
//    }

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping(value = "/login/loginForms")
    @ResponseBody
    public ResponseEntity<String> loginForm(@RequestBody User user) throws Exception {

        // TODO 로그인 시 신규 토큰 발행 현재 테스트 토큰 발행 임.
//        User userInfo = (User) userService.loadUserByUsername(user.getUserId());

        return ResponseEntity.ok().body(jwtTokenProvider.generateToken(user, 60L));
    }



}
