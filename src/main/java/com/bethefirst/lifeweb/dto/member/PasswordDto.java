package com.bethefirst.lifeweb.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {

    private String newPassword;
    private String confirmPassword;
}
