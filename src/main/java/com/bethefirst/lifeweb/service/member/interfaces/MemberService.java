package com.bethefirst.lifeweb.service.member.interfaces;


import com.bethefirst.lifeweb.dto.member.request.JoinDto;
import com.bethefirst.lifeweb.dto.member.request.UpdateMemberDto;
import com.bethefirst.lifeweb.dto.member.request.PasswordDto;
import org.springframework.web.multipart.MultipartFile;


public interface MemberService {

    /** 회원 가입 */
    void join(JoinDto joinDto);

    /** 회원정보 수정 */
    void updateMemberInfo(UpdateMemberDto updateMemberDto, Long memberId);

    /** 회원 이미지 수정 */
    void updateMemberImage(MultipartFile memberFileName, Long memberId);

    /** 회원 비밀번호 변경 */
    void updatePassword(PasswordDto passwordDto, Long memberId);

    /** 회원탈퇴 **/
    void withdraw(Long memberId);

    /** 닉네임 중복체크 */
    void existsNickname(String nickname);

}
