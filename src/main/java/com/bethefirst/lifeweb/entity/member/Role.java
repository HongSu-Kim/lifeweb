package com.bethefirst.lifeweb.entity.member;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {//권한-admin,user

    ADMIN("ROLE_ADMIN"),//관리자
    USER("ROLE_USER");//사용자

    private String value;

}
