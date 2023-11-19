package com.project.soohofit.common.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/static/**"))
                .requestMatchers(AntPathRequestMatcher.antMatcher("/user/login/loginForm"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector handlerMappingIntrospector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(handlerMappingIntrospector);

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(mvcMatcherBuilder.pattern("/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/user")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/user/login/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/user/join/**")).permitAll()
//                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                                .loginPage("/user/login/loginForm").permitAll()
                                .defaultSuccessUrl("/")
                        )
                .build();
    }


}


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//
//                .exceptionHandling((handling) ->
//                        handling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                                .accessDeniedHandler(jwtAccessDeniedHandler)
//                )
//
//                .headers((header) ->
//                        header.frameOptions(
//                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
//                        )
//                )
//
//                .authorizeHttpRequests((registry) ->
//                        registry.requestMatchers("/api/hello").permitAll()
//                                .requestMatchers("/api/authentication").permitAll()
//                                .requestMatchers("/api/signup").permitAll()
//                                .anyRequest().authenticated()
//                );
//        return httpSecurity.build();
//    }