package com.bethefirst.lifeweb.service.member.interfaces;

import com.bethefirst.lifeweb.dto.member.MemberSnsDto;

public interface MemberSnsService {

    /** 회원 SNS 등록 **/
    void createMemberSns(MemberSnsDto memberSnsDto, Long memberId);

    /** 회원 SNS 삭제 */
    void deleteMemberSns(Long memberSnsId);

}
