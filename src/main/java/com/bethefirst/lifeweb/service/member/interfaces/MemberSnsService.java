package com.bethefirst.lifeweb.service.member.interfaces;

import com.bethefirst.lifeweb.dto.member.request.CreateMemberSnsDto;

public interface MemberSnsService {

    /** 회원 SNS 등록 **/
    void createMemberSns(CreateMemberSnsDto createMemberSnsDto, Long memberId);

    /** 회원 SNS 삭제 */
    void deleteMemberSns(Long memberSnsId);

}
