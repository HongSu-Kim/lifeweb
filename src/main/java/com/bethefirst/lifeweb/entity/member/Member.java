package com.bethefirst.lifeweb.entity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {//회원

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;//회원ID PK

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

	@OneToMany(mappedBy = "member")
	private List<SnsUrl> snsUrlList = new ArrayList<>();//SNS주소

}
