package com.bethefirst.lifeweb.controller.member;

import com.bethefirst.lifeweb.config.security.JwtFilter;
import com.bethefirst.lifeweb.config.security.TokenProvider;
import com.bethefirst.lifeweb.dto.jwt.TokenDto;
import com.bethefirst.lifeweb.dto.member.request.*;
import com.bethefirst.lifeweb.dto.member.response.MemberInfoDto;
import com.bethefirst.lifeweb.dto.review.reqeust.ExistNicknameDto;
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

import java.net.URI;

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
    @PostMapping
    public ResponseEntity<?> join(@Valid @RequestBody JoinDto joinDto) {

        memberService.join(joinDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/members/login"));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /** 회원정보 수정 */
    @PutMapping("/{memberId}")
    public ResponseEntity<?> update(@PathVariable(required = false) Long memberId,
                             @Valid @RequestBody UpdateMemberDto updateMemberDto) {


        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        memberService.updateMemberInfo(updateMemberDto, currentMemberId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/members/" + memberId));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);

    }

    /** 로그인 */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPwd());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new TokenDto(jwt), headers, HttpStatus.OK);
    }

    /** 회원 탈퇴 */
    @DeleteMapping()
    public ResponseEntity<?> withdraw(){
        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        memberService.withdraw(currentMemberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /** 회원 단건 조회*/
    @GetMapping("/{memberId}")
    public MemberInfoDto read(@PathVariable Long memberId){

        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        return memberService.getMember(currentMemberId);

    }

    /** 이미지 수정 */
    @PutMapping("/image")
    public ResponseEntity<?> updateMemberImage(MultipartFile fileName){

        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        memberService.updateMemberImage(fileName,currentMemberId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/members/" + currentMemberId));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /** 비밀번호 변경 */
    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto){

        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        memberService.updatePassword(passwordDto, currentMemberId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/members/" + currentMemberId));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /** 회원 SNS 등록 */
    @PostMapping("/sns")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody CreateMemberSnsDto createMemberSnsDto){

        //현재 로그인 된 회원 조회
        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        //회원 SNS 생성
        memberSnsService.createMemberSns(createMemberSnsDto, currentMemberId);


        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    /** 회원 SNS 삭제 */
    @DeleteMapping("/sns/{memberSnsId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> delete(@PathVariable Long memberSnsId){

        //회원 SNS 삭제
        memberSnsService.deleteMemberSns(memberSnsId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /** 닉네임 중복 체크 */
    @PostMapping("/nickname")
    public void existNickname(@RequestBody ExistNicknameDto nicknameDto){
        memberService.existsNickname(nicknameDto.getNickname());
    }

}
