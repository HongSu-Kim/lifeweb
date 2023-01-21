package com.bethefirst.lifeweb.service.member.interfaces;


import com.bethefirst.lifeweb.dto.member.JoinDto;
import com.bethefirst.lifeweb.dto.member.MemberSnsDto;
import com.bethefirst.lifeweb.dto.member.MemberUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface MemberService {

    /** 회원 가입 */
    void join(JoinDto joinDto);

    /** 회원정보 수정 */
    void updateMemberInfo(MemberUpdateDto memberUpdateDto, Long memberId);

    /** 회원 이미지 수정 */
    void updateMemberImage(MultipartFile memberFileName, Long memberId);

    /** 회원 SNS 수정 */
    void updateMemberSnsList(List<MemberSnsDto> memberSnsDtoList, Long memberId);

}
