package com.bethefirst.lifeweb.dto.member.request;


import com.bethefirst.lifeweb.entity.member.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@Slf4j
public class UpdateMemberDto {

    private String name; //회원이름
    private String nickname; //회원 닉네임
    private String gender; //성별
    private LocalDate birth; //생일
    private String tel; //전화번호
    private String postcode; //우편번호
    private String address; //주소
    private String detailAddress; //상세주소
    private String extraAddress; ////참고사항

    public void updateMember(Member member){
        member.updateMember(name, nickname, gender, birth,
                tel, postcode, address, detailAddress, extraAddress);
    }



}
