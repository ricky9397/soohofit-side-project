package com.project.soohofit.common.config.security;


import com.project.soohofit.common.config.CorsConfig;
import com.project.soohofit.common.config.security.service.UserSecurityService;
import com.project.soohofit.common.oauth2.handler.OAuth2SuccessHandler;
import com.project.soohofit.common.oauth2.service.PrincipalOauth2UserService;
import com.project.soohofit.common.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final CorsConfig corsConfig;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final OidcUserService principalOidcUserService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final UserSecurityService userSecurityService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector handlerMappingIntrospector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(handlerMappingIntrospector);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> corsConfig.corsFilter())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(mvcMatcherBuilder.pattern("/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/user")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/user/login/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/user/join/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/user/login/loginForm").permitAll()
                        .defaultSuccessUrl("/")
                );

        http
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/user/login/loginForm").permitAll()
                        .userInfoEndpoint( // oauth2Login 성공 이후의 설정을 시작
                                userinfo -> userinfo.userService(principalOauth2UserService) // 카카오 페이스북 등 Oauth2User
                                        .oidcUserService(principalOidcUserService) // google OidcUser
                        )
                        .successHandler(oAuth2SuccessHandler)
                );

        return http.build();
    }


}