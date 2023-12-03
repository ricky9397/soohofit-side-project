package com.project.soohofit.common.handler;


import com.project.soohofit.common.Consts;
import com.project.soohofit.common.jwt.JwtTokenProvider;
import com.project.soohofit.common.redis.RefreshToken;
import com.project.soohofit.common.redis.RefreshTokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final long AUTH_TIME = 60 * 10;
    private static final long REFRESH_TIME = 60 * 60 * 24 * 3; // 3일
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();

        String accessToken = jwtTokenProvider.generateToken(userId, AUTH_TIME);
        String refreshToken = jwtTokenProvider.generateToken(userId, REFRESH_TIME);
        saveRefreshToken(userId, refreshToken); // 리플레쉬 토큰 redis 저장

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(Consts.REFRESH_TOKEN, refreshToken);
        response.setHeader(Consts.ACCESS_TOKEN, accessToken);

        log.info("로그인에 성공하였습니다. 이메일 : {}", userId);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", AUTH_TIME);
    }

    private void saveRefreshToken(String userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));
        refreshTokenRepository.save(refreshToken);
    }


}
