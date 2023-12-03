package com.project.soohofit.common.config.security;


import com.project.soohofit.common.config.CorsConfig;
import com.project.soohofit.common.config.security.service.UserService;
import com.project.soohofit.common.filter.CustomAuthenticationFilter;
import com.project.soohofit.common.filter.TokenAuthenticationFilter;
import com.project.soohofit.common.handler.LoginFailureHandler;
import com.project.soohofit.common.handler.LoginSuccessHandler;
import com.project.soohofit.common.jwt.JwtTokenProvider;
import com.project.soohofit.common.oauth2.handler.OAuth2FailureHandler;
import com.project.soohofit.common.oauth2.handler.OAuth2SuccessHandler;
import com.project.soohofit.common.oauth2.service.PrincipalOauth2UserService;
import com.project.soohofit.common.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector handlerMappingIntrospector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder( handlerMappingIntrospector);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> corsConfig.corsFilter())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers(mvcMatcherBuilder.pattern("/user/login/loginForm")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/favicon.ico")).permitAll()
//                        .anyRequest().authenticated()
                                .anyRequest().permitAll()
                );

        http
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint( // oauth2Login 성공 이후의 설정을 시작
                                userinfo -> userinfo.userService(principalOauth2UserService)
                        )
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                );

        // 순서 : LogoutFilter -> tokenAuthenticationFilter -> customAuthenticationFilter
        http.addFilterAfter(customAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(tokenAuthenticationFilter(), CustomAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 스프링 2.X 버전의 WebSecurityConfigurerAdapter 에 담긴 authenticationManager() @Bean 등록 사용
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(userService);
        return new ProviderManager(provider);
    }


    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtTokenProvider, refreshTokenRepository);
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    /**
     * 1. 로그인 전용 UsernamePasswordAuthenticationFilter 참고하여 만든 커스텀 필터
     */
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomAuthenticationFilter();
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(jwtTokenProvider);
    }


}