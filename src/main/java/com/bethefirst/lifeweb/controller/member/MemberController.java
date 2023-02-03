package com.bethefirst.lifeweb.controller.member;

import com.bethefirst.lifeweb.config.security.JwtFilter;
import com.bethefirst.lifeweb.config.security.TokenProvider;
import com.bethefirst.lifeweb.dto.CustomUser;
import com.bethefirst.lifeweb.dto.jwt.TokenDto;
import com.bethefirst.lifeweb.dto.member.request.*;
import com.bethefirst.lifeweb.dto.member.response.MemberInfoDto;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import com.bethefirst.lifeweb.service.member.interfaces.MemberService;
import com.bethefirst.lifeweb.service.member.interfaces.MemberSnsService;
import com.bethefirst.lifeweb.util.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;
    private final MemberSnsService memberSnsService;

    /** 회원 가입 */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void join(@Valid @RequestBody JoinDto joinDto) {
        memberService.join(joinDto);
    }

    /** 회원정보 수정 */
    @PutMapping("/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long memberId,
                             @Valid @RequestBody MemberUpdateDto memberUpdateDto) {

        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        memberService.updateMemberInfo(memberUpdateDto, currentMemberId);

    }

    /** 로그인 */
    @PostMapping("/login")
    public ResponseEntity<MemberInfoDto> login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPwd());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUser springSecurityUser = (CustomUser) authentication.getPrincipal();
        Member member = springSecurityUser.getMember();

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        MemberInfoDto memberInfoDto = new MemberInfoDto(member, new TokenDto(jwt));


        return new ResponseEntity<>(memberInfoDto, httpHeaders, HttpStatus.OK);
    }

    /** 회원 탈퇴 */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void withdraw(){
        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        memberService.withdraw(currentMemberId);
    }

    /** 이미지 수정 */
    @PutMapping("/images")
    @ResponseStatus(HttpStatus.OK)
    public void updateMemberImage(MultipartFile multipartFile){

        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        memberService.updateMemberImage(multipartFile,currentMemberId);
    }

    /** 비밀번호 변경 */
    @PutMapping("/passwords")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@RequestBody PasswordDto passwordDto){

        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        memberService.updatePassword(passwordDto, currentMemberId);
    }

    /** 회원 SNS 등록 */
    @PostMapping("/sns")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody CreateMemberSnsDto createMemberSnsDto){

        //현재 로그인 된 회원 조회
        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        //회원 SNS 생성
        memberSnsService.createMemberSns(createMemberSnsDto, currentMemberId);


    }


    /** 회원 SNS 삭제 */
    @DeleteMapping("/sns/{memberSnsId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long memberSnsId){

        //회원 SNS 삭제
        memberSnsService.deleteMemberSns(memberSnsId);

    }




}
