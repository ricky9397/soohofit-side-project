package com.project.soohofit.common.filter;

import com.project.soohofit.common.Consts;
import com.project.soohofit.common.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;


    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 일반 로그인 요청이면 다음 필터인 CustomAuthenticationFilter 호출
        if (request.getRequestURI().equals("/user/login/login")) { 
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(Consts.HEADER_AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(Consts.TOKEN_PREFIX)) {
            log.info("Authorization 토큰이 없거나 잘못 보냈습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(Consts.TOKEN_PREFIX.length());
        log.info("#############토큰 정보############ : {}", token);
        String userName = jwtTokenProvider.getUserId(token);

        if (jwtTokenProvider.validToken(token)) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

}
