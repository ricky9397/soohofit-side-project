package com.project.soohofit.common.config.security;


import com.project.soohofit.common.config.CorsConfig;
import com.project.soohofit.common.config.security.service.UserService;
import com.project.soohofit.common.filter.JWTLoginFilter;
import com.project.soohofit.common.filter.TokenAuthenticationFilter;
import com.project.soohofit.common.jwt.JwtTokenProvider;
import com.project.soohofit.common.oauth2.handler.OAuth2SuccessHandler;
import com.project.soohofit.common.oauth2.service.PrincipalOauth2UserService;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            HandlerMappingIntrospector handlerMappingIntrospector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(
                handlerMappingIntrospector);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> corsConfig.corsFilter())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers(mvcMatcherBuilder.pattern("/**")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/user")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/user/login/loginForm")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/user/login/kakao")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/user/join/**")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/login/oauth2/code/kakao")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/oauth/token")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/v2/user/me")).permitAll()
//                        .requestMatchers(mvcMatcherBuilder.pattern("/favicon.ico")).permitAll()
//                        .anyRequest().authenticated()
                                .anyRequest().permitAll()
                );
        // TODO 최신 시큐리티 6.X 버전 부터 authenticationManager() 변경되어 주입이 안되기 때문에 찾아봐야 함. ㅠ
//                        .addFilterAt(new JWTLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
//                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint( // oauth2Login 성공 이후의 설정을 시작
                                userinfo -> userinfo.userService(principalOauth2UserService)
                                // 카카오 페이스북 등 Oauth2User
                        )
                        .successHandler(oAuth2SuccessHandler)
                );

        // 순서 : LogoutFilter -> tokenAuthenticationFilter -> jwtLoginFilter
        http.addFilterAt(new JWTLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(tokenAuthenticationFilter(), JWTLoginFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(userService);
        return new ProviderManager(provider);
    }

//    @Bean
//    public TokenAuthenticationFilter tokenAuthenticationFilter() {
//        return new TokenAuthenticationFilter(jwtTokenProvider);
//    }


}