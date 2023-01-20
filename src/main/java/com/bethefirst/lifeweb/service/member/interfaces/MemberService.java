package com.bethefirst.lifeweb.service.member.interfaces;


import com.bethefirst.lifeweb.dto.member.JoinDto;
import com.bethefirst.lifeweb.dto.member.MemberUpdateDto;


public interface MemberService {

    /** 회원 가입 */
    void join(JoinDto joinDto);

    /** 회원수정 */
    void update(MemberUpdateDto memberUpdateDto, Long memberId);



}
