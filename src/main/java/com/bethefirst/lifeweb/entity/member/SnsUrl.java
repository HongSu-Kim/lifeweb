package com.bethefirst.lifeweb.entity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SnsUrl {//SNS주소

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sns_url_id")
	private Long id;//SNS주소ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;//회원번호 FK

	private String url;//SNS주소

}
