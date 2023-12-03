package com.project.soohofit.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.soohofit.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/user/login/login", "POST"); // "/login" + POST로 온 요청에 매칭된다.

    public CustomAuthenticationFilter() {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        User userLogin = objectMapper.readValue(request.getInputStream(), User.class);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userLogin.getUserId(), userLogin.getUserPwd(), null
        );
        return this.getAuthenticationManager().authenticate(authToken);
    }

}
