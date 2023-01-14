package com.bethefirst.lifeweb;

import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Component
public class initData {
    @Value("${basic.image}")
    private String basicImage;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init(){

        Member admin = Member.createAdmin(passwordEncoder,"admin", "admin", "admin",  basicImage);
        memberRepository.save(admin);
    }
}
