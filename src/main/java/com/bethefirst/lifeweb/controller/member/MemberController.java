package com.bethefirst.lifeweb.controller.member;

import com.bethefirst.lifeweb.config.security.JwtFilter;
import com.bethefirst.lifeweb.config.security.TokenProvider;
import com.bethefirst.lifeweb.dto.jwt.TokenDto;
import com.bethefirst.lifeweb.dto.member.request.FindPasswordDto;
import com.bethefirst.lifeweb.dto.member.request.*;
import com.bethefirst.lifeweb.dto.member.response.MemberInfoDto;
import com.bethefirst.lifeweb.service.member.interfaces.MemberService;
import com.bethefirst.lifeweb.service.member.interfaces.MemberSnsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("isAuthenticated() and (( #memberId == principal.memberId ) or hasRole('ADMIN'))")
    public ResponseEntity<?> update(@PathVariable Long memberId,
                             @Valid @RequestBody UpdateMemberDto updateMemberDto) {



        memberService.updateMemberInfo(updateMemberDto, memberId);

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
    @DeleteMapping("/{memberId}")
    @PreAuthorize("isAuthenticated() and (( #memberId == principal.memberId ) or hasRole('ADMIN'))")
    public ResponseEntity<?> withdraw(@PathVariable Long memberId){

        memberService.withdraw(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /** 회원 단건 조회 */
    @GetMapping("/{memberId}")
    @PreAuthorize("isAuthenticated() and (( #memberId == principal.memberId ) or hasRole('ADMIN'))")
    public MemberInfoDto read(@PathVariable Long memberId) {

        return memberService.getMember(memberId);
    }

    /** 회원 전체 조회 */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<MemberInfoDto> list(@RequestBody MemberSearchRequirements requirements,
                                    @PageableDefault(sort = "id", size = 20, direction = Sort.Direction.DESC)Pageable pageable){
        return memberService.getMemberList(requirements, pageable);
    }


    /** 이미지 수정 */
    @PutMapping("/{memberId}/image")
    @PreAuthorize("isAuthenticated() and (( #memberId == principal.memberId ) or hasRole('ADMIN'))")
    public ResponseEntity<?> updateMemberImage(@PathVariable Long memberId,
                                               MultipartFile fileName){


        memberService.updateMemberImage(fileName,memberId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/members/" + memberId));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /** 비밀번호 변경 */
    @PutMapping("/{memberId}/password")
    @PreAuthorize("isAuthenticated() and (( #memberId == principal.memberId ) or hasRole('ADMIN'))")
    public ResponseEntity<?> updatePassword(@PathVariable Long memberId,
                                            @RequestBody UpdatePasswordDto updatePasswordDto){

        memberService.updatePassword(updatePasswordDto, memberId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/members/" + memberId));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /** 회원 SNS 등록 */
    @PostMapping("/sns")
    @PreAuthorize("isAuthenticated() and (( #createMemberSnsDto.memberId == principal.memberId ) or hasRole('ADMIN'))")
    public ResponseEntity<?> create(@RequestBody CreateMemberSnsDto createMemberSnsDto){

        //회원 SNS 생성
        memberSnsService.createMemberSns(createMemberSnsDto);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    /** 회원 SNS 삭제 */
    @DeleteMapping("/sns/{memberSnsId}")
    @PreAuthorize("isAuthenticated() and (( #deleteMemberSnsDto.memberId == principal.memberId ) or hasRole('ADMIN'))")
    public ResponseEntity<?> delete(@PathVariable Long memberSnsId,
                                    @RequestBody DeleteMemberSnsDto deleteMemberSnsDto){

        //회원 SNS 삭제
        memberSnsService.deleteMemberSns(memberSnsId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /** 닉네임 중복 체크 */
    @GetMapping("/nickname")
    public void existNickname(String nickname){
        memberService.existsNickname(nickname);
    }

    /** 이메일 중복 체크 */
    @GetMapping("/email")
    public void existEmail(String email){
        memberService.existsEmail(email);
    }


    /** 비밀번호 찾기 */
    @PutMapping("/password")
    public void findPassword(@RequestBody FindPasswordDto findPasswordDto){
        memberService.findPassword(findPasswordDto.getEmail());
    }
}


