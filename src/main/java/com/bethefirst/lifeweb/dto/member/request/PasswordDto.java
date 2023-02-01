package com.bethefirst.lifeweb.dto.member.request;

import com.bethefirst.lifeweb.entity.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class PasswordDto {

    private String newPassword;
    private String confirmPassword;

    public void updatePassword(PasswordEncoder passwordEncoder, Member member){
        member.updatePassword(passwordEncoder, newPassword);
    }
}
