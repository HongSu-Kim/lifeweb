package com.bethefirst.lifeweb.entity.application;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {//신청서

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

	private String memo;//메모
	private LocalDateTime created;//신청일

	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;//상태

	@OneToMany(mappedBy = "application", cascade = CascadeType.REMOVE)
	private List<ApplicationAnswer> applicationAnswerList = new ArrayList<>();

	public Application(Member member, Campaign campaign, String memo) {
		this.member = member;
		this.campaign = campaign;
		this.memo = memo;
		this.created = LocalDateTime.now();
		this.status = ApplicationStatus.UNSELECT;
	}

	/** 신청서 수정 */
	public void updateApplication(String memo) {
		this.memo = memo;
	}

	/** 신청서 상태 수정 */
	public void updateApplicationStatus(ApplicationStatus status) {
		this.status = status;
	}

}
