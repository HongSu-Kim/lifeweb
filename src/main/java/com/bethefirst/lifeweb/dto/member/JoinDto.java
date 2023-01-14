package com.bethefirst.lifeweb.dto.member;

import com.bethefirst.lifeweb.entity.member.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String pwd;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "/^[\\w\\Wㄱ-ㅎㅏ-ㅣ가-힣]{2,15}$/",
            message = "닉네임은 특수문자를 제외한 2자 ~ 15자여야 합니다. ")
    private String nickname;

}
