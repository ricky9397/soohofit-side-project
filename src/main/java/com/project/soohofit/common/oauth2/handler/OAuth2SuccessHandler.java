package com.project.soohofit.common.oauth2.handler;

import com.project.soohofit.common.config.security.repository.UserSecurityRepository;
import com.project.soohofit.user.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserSecurityRepository userSecurityRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2User) {
            if(principal instanceof OidcUser){ // google

            } else { // kakao, naver 등
                String kakaoId = String.valueOf(((OAuth2User) principal).getAttributes().get("id"));

                User user = userSecurityRepository.findByUserId(kakaoId).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("회원 인증을 실패하였습니다."));

                // TODO SNS 로그인 성공 후 토큰 발급 후 전달 로직 만들 어야함.

            }
        }
    }
}
