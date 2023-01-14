package com.bethefirst.lifeweb.service.member;

import com.bethefirst.lifeweb.dto.member.JoinDto;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.member.Role;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.service.member.interfaces.MemberService;
import com.bethefirst.lifeweb.util.security.SecurityUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	@Value("${basic.image}")
	private String basicImage;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;


	@Override
	public void join(JoinDto joinDto) {

		memberRepository.findByEmail(joinDto.getEmail()).ifPresent(member -> {
			throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
		});

		Member member = Member.createMember(passwordEncoder,joinDto.getEmail(),
				joinDto.getPwd(),
				joinDto.getNickname(),
				basicImage);

		memberRepository.save(member);

	}


}
