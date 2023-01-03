package com.bethefirst.lifeweb.dto.member;

import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.member.Role;
import com.bethefirst.lifeweb.entity.member.SnsUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

	private Long id;//회원ID
	private Role role;//권한
	private String email;//이메일
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

	private List<String> urlList;//SNS주소

	public MemberDto(Member member) {

		id = member.getId();
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

		urlList = member.getSnsUrlList()
				.stream().map(SnsUrl::getUrl)
				.collect(Collectors.toList());

	}

}
