package com.bethefirst.lifeweb.entity.member;

import com.bethefirst.lifeweb.dto.member.MemberUpdateDto;
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


	@OneToMany(mappedBy = "member")
	private List<MemberSns> memberSnsList = new ArrayList<>();//회원SNS



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
	public void updateMemberByUpdateDto(MemberUpdateDto memberUpdateDto){

		this.name = memberUpdateDto.getName() == null ? null : memberUpdateDto.getName();
		this.nickname = memberUpdateDto.getNickname() == null ? null : memberUpdateDto.getNickname();
		this.gender = memberUpdateDto.getGender() == null ? null : memberUpdateDto.getGender();
		this.birth = memberUpdateDto.getBirth() == null ? null : memberUpdateDto.getBirth();
		this.tel = memberUpdateDto.getTel() == null ? null : memberUpdateDto.getTel();
		this.postcode = memberUpdateDto.getPostcode() == null ? null : memberUpdateDto.getPostcode();
		this.address = memberUpdateDto.getAddress() == null ? null : memberUpdateDto.getAddress();
		this.detailAddress = memberUpdateDto.getDetailAddress() == null ? null : memberUpdateDto.getDetailAddress();
		this.extraAddress = memberUpdateDto.getExtraAddress() == null ? null : memberUpdateDto.getExtraAddress();

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
