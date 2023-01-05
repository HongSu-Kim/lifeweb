package com.bethefirst.lifeweb.entity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

}
