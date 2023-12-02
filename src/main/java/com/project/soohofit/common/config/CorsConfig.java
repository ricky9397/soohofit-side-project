package com.project.soohofit.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    // 시큐리티 cors 맵핑 메서드
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");
        configuration.setAllowCredentials(true);
        // TODO jsp 스크립트 헤더에 토큰 ajax 로직 생성 후 해제
//        configuration.addExposedHeader("auth_token"); // 로그인시 Authorization 헤더에 전달하기 위한 cors
//        configuration.addExposedHeader("refresh_token");
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

}
