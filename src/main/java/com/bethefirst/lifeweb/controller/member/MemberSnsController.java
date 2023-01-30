package com.bethefirst.lifeweb.controller.member;

import com.bethefirst.lifeweb.dto.member.MemberSnsDto;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import com.bethefirst.lifeweb.service.member.interfaces.MemberSnsService;
import com.bethefirst.lifeweb.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membersns")
@RequiredArgsConstructor
@Slf4j
public class MemberSnsController {

    private final MemberSnsService memberSnsService;


    /** 회원 SNS 등록 */
   @PostMapping
   @ResponseStatus(HttpStatus.OK)
   public void create(@RequestBody MemberSnsDto memberSnsDto){

       //현재 로그인 된 회원 조회
       Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
               -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

       //회원 SNS 생성
       memberSnsService.createMemberSns(memberSnsDto, currentMemberId);


   }


    /** 회원 SNS 삭제 */
    @DeleteMapping("/{memberSnsId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long memberSnsId){

        //회원 SNS 삭제
        memberSnsService.deleteMemberSns(memberSnsId);

    }


}
