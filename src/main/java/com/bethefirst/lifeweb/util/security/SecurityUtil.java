package com.bethefirst.lifeweb.util.security;

import com.bethefirst.lifeweb.dto.CustomUser;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
public class SecurityUtil {


    private SecurityUtil() {}



    public static Optional<Long> getCurrentMemberId() {
        CustomUser customUser = getCustomUserFromSecurityContext();
        return Optional.ofNullable(customUser.getMemberId());
    }


    public static String getCurrentAuthority(){
        CustomUser customUser = getCustomUserFromSecurityContext();
        return customUser.getAuthorities().stream().toList().get(0).getAuthority();
    }

    private static CustomUser getCustomUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal().equals("anonymousUser")) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            throw new UnauthorizedException("Security Context에 인증 정보가 없습니다.");
        }

        try {
            return (CustomUser) authentication.getPrincipal();

        } catch (IllegalArgumentException e) {
            log.debug("캐스팅 실패");
		}catch (Exception e){
            log.info("예외 발생: ",e);
        }
        return null;
    }



    public static Optional<String> getCurrentMemberEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String email = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            email = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            email = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(email);
    }



}