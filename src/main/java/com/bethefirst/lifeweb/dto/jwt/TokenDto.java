package com.bethefirst.lifeweb.dto.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {

    private String token;

    public TokenDto(String token) {
        this.token = token;
    }
}
