package com.bethefirst.lifeweb.entity.member;

import com.bethefirst.lifeweb.entity.application.Applicant;
import com.bethefirst.lifeweb.entity.review.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Member {//회원

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;//회원ID PK

	@Enumerated(EnumType.STRING)
	private Role role;//권한

	private String email;//이메일
	private String pwd;//비밀번호
	private String fileName;//프로필이미지
	private String name; //이름
	private String nickname;//닉네임
	private String gender;//성별
	private LocalDate birth;//생년월일
	private String tel;//전화번호
	private String postcode;//우편번호
	private String address;//주소
	private String detailAddress;//상세주소
	private String extraAddress;//참고사항
	private int point;//포인트


	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<MemberSns> memberSnsList = new ArrayList<>();//회원SNS

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Review> reviewList = new ArrayList<>(); //회원 리뷰

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Applicant> applicationList = new ArrayList<>(); //회원 신청서

	protected Member(Role role, String email, String pwd, String nickname) {

		this.role = role;
		this.email = email;
		this.pwd = pwd;
		this.nickname = nickname;
		this.point = 0;
	}

	//회원 생성
	public static Member createMember(PasswordEncoder passwordEncoder,String email, String pwd,
							   String nickname){
		return new Member(Role.USER, email, passwordEncoder.encode(pwd), nickname);

	}

	//관리자 생성
	public static Member createAdmin(PasswordEncoder passwordEncoder, String email, String pwd,
									  String nickname){
		return new Member(Role.ADMIN , email , passwordEncoder.encode(pwd), nickname);
	}


	//== 정보 수정 == /
	public void updateMember(String name, String nickname, String gender, LocalDate birth,
							 String tel, String postcode, String address, String detailAddress, String extraAddress){

		this.name = name;
		this.nickname = nickname;
		this.gender = gender;
		this.birth = birth;
		this.tel = tel;
		this.postcode = postcode;
		this.address = address;
		this.detailAddress = detailAddress;
		this.extraAddress = extraAddress;

	}

	//회원 이미지 수정
	public void updateFileName(String fileName){
		this.fileName = fileName;
	}



	public void updatePassword(PasswordEncoder passwordEncoder, String pwd){
		this.pwd = passwordEncoder.encode(pwd);
	}


	//비밀번호 변경, 회원 탈퇴 시, 비밀번호를 확인하며, 이때 비밀번호의 일치여부를 판단하는 메서드입니다.
	public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
		return passwordEncoder.matches(checkPassword, getPwd());
	}

}
