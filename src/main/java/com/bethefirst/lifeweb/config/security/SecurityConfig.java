package com.bethefirst.lifeweb.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 시큐리티의 검증에서 제외됩니다.
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/images/**", "/js/**",
                        "/webjars/**","/error","/favicon.ico");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                // 401 , 403 Exception 핸들링
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

				//
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()

                // 세션을 사용하지 않습니다
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				//
                .and()
                .formLogin().disable() //폼을 사용하지 않습니다.
                .httpBasic().disable() //basic 방식을 사용하지 않습니다.

                // 요청 접근제한 설정
                .authorizeHttpRequests(authorize -> authorize
//						.requestMatchers(HttpMethod.POST,"/sns", "/campaign-categories", "/campaign-types", "/locals").hasRole("ADMIN")
//						.requestMatchers("/sns/**", "/campaign-categories/**", "/campaign-types/**", "/locals/**").hasRole("ADMIN")
                		.anyRequest().permitAll())

                // 스프링시큐리티가 동작하기 전 토큰작업이 먼저 실행됩니다
                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }

}
