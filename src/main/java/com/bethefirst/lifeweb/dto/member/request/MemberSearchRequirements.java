package com.bethefirst.lifeweb.dto.member.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchRequirements { //회원 검색조건

    private String email;
    private String nickname;
    private String name;

}
