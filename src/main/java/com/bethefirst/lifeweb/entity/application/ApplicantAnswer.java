package com.bethefirst.lifeweb.entity.application;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantAnswer {//신청자답변

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "applicant_answer_id")
	private Long id;//신청자답변ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_id")
	private Applicant applicant;//신청자 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_question_id")
	private ApplicationQuestion applicationQuestion;//신청서질문 FK

	private String answer;//답변


	public ApplicantAnswer(Applicant applicant, ApplicationQuestion applicationQuestion, String answer) {
		this.applicant = applicant;
		this.applicationQuestion = applicationQuestion;
		this.answer = answer;
	}

	/** 신청자답변 수정 */
	public void updateApplicationAnswer(String answer) {
		this.answer = answer;
	}
}
