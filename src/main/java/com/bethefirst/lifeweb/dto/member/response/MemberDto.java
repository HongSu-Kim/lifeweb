package com.bethefirst.lifeweb.dto.member.response;

import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.member.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MemberDto {


	private Role role;//권한

	private String email;

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
