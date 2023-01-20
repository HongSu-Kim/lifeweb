package com.bethefirst.lifeweb.util.security;

import com.bethefirst.lifeweb.dto.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
public class SecurityUtil {


    private SecurityUtil() {}

    public static Optional<Long> getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        Long memberId = null;
        try{
            CustomUser springSecurityUser = (CustomUser) authentication.getPrincipal();
            memberId = springSecurityUser.getMemberId();
            log.info("캐스팅 되었습니다");
        }catch (IllegalArgumentException e){
            log.info("캐스팅 실패");
        }

        return Optional.ofNullable(memberId);
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