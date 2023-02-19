package com.bethefirst.lifeweb.config.security;

import com.bethefirst.lifeweb.dto.CustomUser;
import com.bethefirst.lifeweb.entity.member.Role;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import com.bethefirst.lifeweb.repository.campaign.LocalRepository;
import com.bethefirst.lifeweb.util.security.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final LocalRepository localRepository;

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
						// sns, campaign-categories, campaign-types, locals
						.requestMatchers(HttpMethod.GET,"/sns", "/campaign-categories", "/campaign-types", "/locals").permitAll()//전체조회
						.requestMatchers("/sns/**", "/campaign-categories/**", "/campaign-types/**", "/locals/**").hasRole("ADMIN")
						// members 회원
						.requestMatchers(HttpMethod.POST,"/members/**").anonymous()//회원가입,로그인
						.requestMatchers(HttpMethod.GET,"/members/nickname", "/members/email").anonymous()//중복체크
						.requestMatchers(HttpMethod.GET,"/members/**").hasRole("ADMIN")//전체조회
						.requestMatchers("/members/**").authenticated()
						// campaigns 캠페인
						.requestMatchers(HttpMethod.GET,"/campaigns/**").permitAll()//조회
//						.requestMatchers(HttpMethod.GET,"/campaigns/**").access(new WebExpressionAuthorizationManager("(!isAuthenticated() and #memberId == null) or (isAuthenticated() and ((#memberId == principal.memberId) or hasRole('ADMIN')))"))//조회
//						.requestMatchers(HttpMethod.GET,"/campaigns/**").access((authentication, object) -> check(object))//조회
//						.requestMatchers(HttpMethod.GET,"/campaigns/**").access(new WebExpressionAuthorizationManager("@check(authentication,request)"))//조회
						.requestMatchers("/campaigns/**").hasRole("ADMIN")
						// applications 신청서
						.requestMatchers(HttpMethod.GET, "/applications/**").authenticated()//조회
						.requestMatchers("/applications/**").hasRole("ADMIN")
						// applicants 신청자
						.requestMatchers("/applicants/status").hasRole("ADMIN")//신청자 상태 수정
						.requestMatchers("/applicants/**").authenticated()
						// reviews 리뷰
						.requestMatchers(HttpMethod.GET, "/reviews").permitAll()//전체조회
						.requestMatchers("/reviews/**").authenticated()

						.anyRequest().permitAll()
//						.anyRequest().denyAll()
				)

                // 스프링시큐리티가 동작하기 전 토큰작업이 먼저 실행됩니다
                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }

//	public AuthorizationDecision check(RequestAuthorizationContext context) {
////		HttpServletRequest request = context.getRequest();
////		String memberId = (String) request.getAttribute("memberId");
//		String memberId = context.getVariables().getOrDefault("memberId", "");
////		Map<String, String> variables = context.getVariables();
//
//		if (SecurityUtil.getCurrentAuthority().equals(Role.ADMIN.getValue())) {
//			return new AuthorizationDecision(false);
//		}
//
//		if (memberId.equals("")) {
//			return new AuthorizationDecision(true);
//		} else if (SecurityUtil.getCurrentMemberId().equals(Long.valueOf(memberId)) || SecurityUtil.getCurrentAuthority().equals(Role.ADMIN.getValue())) {
//			return new AuthorizationDecision(true);
//		}
//		return new AuthorizationDecision(false);
//	}

//	@Bean
//	@Scope("prototype")
//	public boolean check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
////		HttpServletRequest request = context.getRequest();
////		String memberId = (String) request.getAttribute("memberId");
//		String memberId = context.getVariables().getOrDefault("memberId", "");
//////		Map<String, String> variables = context.getVariables();
//
//		if (SecurityUtil.getCurrentAuthority().equals(Role.ADMIN.getValue())) {
//			return false;
//		}
//
//		if (memberId.equals("")) {
//			return true;
//		} else if (SecurityUtil.getCurrentMemberId().equals(Long.valueOf(memberId)) || SecurityUtil.getCurrentAuthority().equals(Role.ADMIN.getValue())) {
//			return true;
//		}
//		return false;
//
//	}

}
