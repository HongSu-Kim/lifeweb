package com.bethefirst.lifeweb.controller.member;

import com.bethefirst.lifeweb.service.member.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("member")
@Slf4j
public class MemberController {

	private final MemberService memberService;

}
