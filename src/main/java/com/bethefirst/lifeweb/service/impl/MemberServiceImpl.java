package com.bethefirst.lifeweb.service.impl;

import com.bethefirst.lifeweb.repository.MemberRepository;
import com.bethefirst.lifeweb.service.MemberService;
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
