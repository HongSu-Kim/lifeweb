package com.bethefirst.lifeweb.entity.application;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.member.Sns;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_id")
	private Long id;//신청서ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;//회원 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;//캠페인 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sns_id")
	private Sns sns;//SNS FK

	private String memo;//메모
	private LocalDateTime created;//신청일

}
