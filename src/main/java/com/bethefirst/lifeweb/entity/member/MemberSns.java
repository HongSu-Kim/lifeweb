package com.bethefirst.lifeweb.entity.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class MemberSns {//SNS주소

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_sns_id")
	private Long id;//SNS주소ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;//회원번호 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sns_id")
	private Sns sns;//SNS FK

	private String snsUrl;//SNS주소

	private MemberSns(Member member, Sns sns, String snsUrl) {
		this.member = member;
		this.sns = sns;
		this.snsUrl = snsUrl;

	}

	public static MemberSns createMemberSns(Member member, Sns sns,String snsUrl){
		return new MemberSns(member, sns, snsUrl);
	}

	public void updateMemberSnsByUpdateDto(String snsUrl){
		this.snsUrl = snsUrl;
	}


}
