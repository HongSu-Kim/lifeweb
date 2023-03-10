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
        // ??????????????? ???????????? ???????????????.
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

                // 401 , 403 Exception ?????????
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

				//
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()

                // ????????? ???????????? ????????????
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				//
                .and()
                .formLogin().disable() //?????? ???????????? ????????????.
                .httpBasic().disable() //basic ????????? ???????????? ????????????.

                // ?????? ???????????? ??????
                .authorizeHttpRequests(authorize -> authorize
						// sns, campaign-categories, campaign-types, locals
						.requestMatchers(HttpMethod.GET,"/sns", "/campaign-categories", "/campaign-types", "/locals").permitAll()//????????????
						.requestMatchers("/sns/**", "/campaign-categories/**", "/campaign-types/**", "/locals/**").hasRole("ADMIN")
						// members ??????
						.requestMatchers(HttpMethod.POST,"/members/**").anonymous()//????????????,?????????
						.requestMatchers(HttpMethod.GET,"/members/nickname", "/members/email").anonymous()//????????????
						.requestMatchers(HttpMethod.GET,"/members/**").hasRole("ADMIN")//????????????
						.requestMatchers("/members/**").authenticated()
						// campaigns ?????????
						.requestMatchers(HttpMethod.GET,"/campaigns/**").permitAll()//??????
//						.requestMatchers(HttpMethod.GET,"/campaigns/**").access(new WebExpressionAuthorizationManager("(!isAuthenticated() and #memberId == null) or (isAuthenticated() and ((#memberId == principal.memberId) or hasRole('ADMIN')))"))//??????
//						.requestMatchers(HttpMethod.GET,"/campaigns/**").access((authentication, object) -> check(object))//??????
//						.requestMatchers(HttpMethod.GET,"/campaigns/**").access(new WebExpressionAuthorizationManager("@check(authentication,request)"))//??????
						.requestMatchers("/campaigns/**").hasRole("ADMIN")
						// applications ?????????
						.requestMatchers(HttpMethod.GET, "/applications/**").authenticated()//??????
						.requestMatchers("/applications/**").hasRole("ADMIN")
						// applicants ?????????
						.requestMatchers("/applicants/status").hasRole("ADMIN")//????????? ?????? ??????
						.requestMatchers("/applicants/**").authenticated()
						// reviews ??????
						.requestMatchers(HttpMethod.GET, "/reviews").permitAll()//????????????
						.requestMatchers("/reviews/**").authenticated()

						.anyRequest().permitAll()
//						.anyRequest().denyAll()
				)

                // ???????????????????????? ???????????? ??? ??????????????? ?????? ???????????????
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
