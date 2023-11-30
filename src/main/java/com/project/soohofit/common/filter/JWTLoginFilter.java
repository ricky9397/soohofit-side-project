package com.project.soohofit.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.soohofit.user.domain.User;
import com.project.soohofit.user.domain.UserLogin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/user/login/login");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("############################로그인시도####################################");
        UserLogin userLogin = objectMapper.readValue(request.getInputStream(), UserLogin.class);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userLogin.getUserId(), userLogin.getUserPwd(), null
        );
        // 1. UserDetailsService 의 findByUserEmail() 함수 실행됨.
        // 2. UserDetailsService 의 사용자가 있으면 권한부여 받고 -> successfulAuthentication()로 호출한다.
        return this.getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        logger.info("############################로그인 성공 테스트####################################");
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
