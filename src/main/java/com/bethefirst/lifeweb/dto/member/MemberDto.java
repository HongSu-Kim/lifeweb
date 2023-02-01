package com.bethefirst.lifeweb.dto.member;

import com.bethefirst.lifeweb.dto.member.response.MemberSnsDto;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.member.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MemberDto {


	private Role role;//권한

	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식에 맞지 않습니다.")
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
			message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
	private String pwd;//비밀번호

	private String fileName;//프로필이미지

	private String nickname;//닉네임
	private String gender;//성별
	private LocalDate birth;//생년월일
	private String tel;//전화번호
	private String postcode;//우편번호
	private String address;//주소
	private String detailAddress;//상세주소
	private String extraAddress;//참고사항
	private int point;//포인트

	private List<MemberSnsDto> memberSnsList;//SNS주소

	public MemberDto(Member member) {


		role = member.getRole();
		email = member.getEmail();
		pwd = member.getPwd();
		fileName = member.getFileName();
		nickname = member.getNickname();
		gender = member.getGender();
		birth = member.getBirth();
		tel = member.getTel();
		postcode = member.getPostcode();
		address = member.getAddress();
		detailAddress = member.getDetailAddress();
		extraAddress = member.getExtraAddress();
		point = member.getPoint();

		memberSnsList = member.getMemberSnsList()
				.stream().map(MemberSnsDto::new)
				.collect(Collectors.toList());

	}

}
