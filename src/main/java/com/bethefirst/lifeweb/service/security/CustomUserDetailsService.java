package com.bethefirst.lifeweb.service.security;

import com.bethefirst.lifeweb.dto.CustomUser;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public CustomUser loadUserByUsername(final String email) {
        return memberRepository.findByEmail(email)
                .map(this::createUser)
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));

    }

    private CustomUser createUser(Member member) {

        /** 유저의 권한 정보를 만듭니다 */
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(member.getRole().name()));

        CustomUser customUser = new CustomUser(member.getEmail(),
                member.getPwd(),
                grantedAuthorities);
        customUser.setMemberId(member.getId());
        return customUser;
    }
}
