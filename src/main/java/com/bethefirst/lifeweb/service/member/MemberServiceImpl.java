package com.bethefirst.lifeweb.service.member;

import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.service.member.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

}
