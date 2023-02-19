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



    public static Long getCurrentMemberId() {
        CustomUser customUser = getCustomUserFromSecurityContext();
        return customUser.getMemberId();
    }


    public static String getCurrentAuthority(){
        CustomUser customUser = getCustomUserFromSecurityContext();
        return customUser.getAuthorities().stream().toList().get(0).getAuthority();
    }

    private static CustomUser getCustomUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal().equals("anonymousUser")) {
            throw new UnauthorizedException("Security Context에 인증 정보가 없습니다.");
        }

        if(authentication.getPrincipal() instanceof CustomUser){
          return (CustomUser)authentication.getPrincipal();
        }else{
            throw new RuntimeException("CustomUser 캐스팅에 실패 하였습니다.");
        }

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