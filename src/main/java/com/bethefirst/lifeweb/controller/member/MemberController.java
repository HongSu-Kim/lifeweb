package com.bethefirst.lifeweb.controller.member;

import com.bethefirst.lifeweb.config.security.JwtFilter;
import com.bethefirst.lifeweb.config.security.TokenProvider;
import com.bethefirst.lifeweb.dto.jwt.TokenDto;
import com.bethefirst.lifeweb.dto.member.JoinDto;
import com.bethefirst.lifeweb.dto.member.LoginDto;
import com.bethefirst.lifeweb.dto.member.MemberUpdateDto;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import com.bethefirst.lifeweb.service.member.interfaces.MemberService;
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
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;

    /** 회원 가입 */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/join")
    public void join(@Valid @RequestBody JoinDto joinDto) {
        memberService.join(joinDto);
    }

    /** 회원정보 수정 */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateMember(@Valid @RequestBody MemberUpdateDto memberUpdateDto) {

        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        memberService.updateMemberInfo(memberUpdateDto, currentMemberId);

    }

    /** 이미지 수정 */
    @PutMapping("/images")
    @ResponseStatus(HttpStatus.OK)
    public void updateMemberImage(MultipartFile multipartFile){

        Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
                -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

        memberService.updateMemberImage(multipartFile,currentMemberId);
    }

    /** 로그인 */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPwd());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

}
