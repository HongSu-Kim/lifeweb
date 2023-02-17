package com.bethefirst.lifeweb.entity.application;

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
public class Applicant {//신청자

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "applicant_id")
	private Long id;//신청자ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;//회원 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_id")
	private Application application;//캠페인 FK

	private String memo;//메모
	private LocalDateTime created;//신청일

	@Enumerated(EnumType.STRING)
	private ApplicantStatus status;//상태


	@OneToMany(mappedBy = "applicant", cascade = CascadeType.REMOVE)
	private List<ApplicantAnswer> applicantAnswerList = new ArrayList<>();//신청서대답


	public Applicant(Member member, Application application, String memo) {
		this.member = member;
		this.application = application;
		this.memo = memo;
		this.created = LocalDateTime.now();
		this.status = ApplicantStatus.UNSELECT;
	}

	/** 신청자 수정 */
	public void updateApplicant(String memo) {
		this.memo = memo;
	}

	/** 신청자 상태 수정 */
	public void updateApplicantStatus(ApplicantStatus status) {
		this.status = status;
	}

}
