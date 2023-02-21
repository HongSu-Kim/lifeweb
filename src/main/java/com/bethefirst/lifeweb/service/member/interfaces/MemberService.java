package com.bethefirst.lifeweb.service.member.interfaces;


import com.bethefirst.lifeweb.dto.member.request.JoinDto;
import com.bethefirst.lifeweb.dto.member.request.MemberSearchRequirements;
import com.bethefirst.lifeweb.dto.member.request.UpdatePasswordDto;
import com.bethefirst.lifeweb.dto.member.request.UpdateMemberDto;
import com.bethefirst.lifeweb.dto.member.response.MemberInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface MemberService {

    /** 회원 가입 */
    void join(JoinDto joinDto);

    /** 회원정보 수정 */
    void updateMemberInfo(UpdateMemberDto updateMemberDto, Long memberId);

    /** 회원 이미지 수정 */
    void updateMemberImage(MultipartFile memberFileName, Long memberId);

    /** 회원 비밀번호 변경 */
    void updatePassword(UpdatePasswordDto updatePasswordDto, Long memberId);

    /** 회원탈퇴 **/
    void withdraw(Long memberId);

    /** 회원 단건조회 */
    MemberInfoDto getMember(Long memberId);

    /** 회원 전체조회 */
    Page<MemberInfoDto> getMemberList(MemberSearchRequirements requirements , Pageable pageable);

    /** 닉네임 중복체크 */
    void existsNickname(String nickname);

    /** 이메일 중복체크 */
    void existsEmail(String email);

    /** 비밀번호 찾기 */
    void findPassword(String email);
}
