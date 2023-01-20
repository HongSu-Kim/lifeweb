package com.bethefirst.lifeweb.service.member.interfaces;


import com.bethefirst.lifeweb.dto.member.JoinDto;
import com.bethefirst.lifeweb.dto.member.MemberUpdateDto;


public interface MemberService {

    /** 회원 가입 */
    void join(JoinDto joinDto);

    /** 회원 수정 */
    void updateMemberInfo(MemberUpdateDto memberUpdateDto, Long memberId);

    /** 회원 이미지 수정 */
    void updateMemberImage(MemberUpdateDto memberUpdateDto, Long memberId);

    /** 회원 SNS 수정 */
    void updateMemberSnsList(MemberUpdateDto memberUpdateDto, Long memberId);

}
