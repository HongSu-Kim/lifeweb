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
public class MemberUpdateDto {

    private String name;
    private String nickname;
    private String gender;
    private LocalDate birth;
    private String tel;
    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;

    public void updateMember(Member member){
        member.updateMember(name, nickname, gender, birth,
                tel, postcode, address, detailAddress, extraAddress);
    }



}
