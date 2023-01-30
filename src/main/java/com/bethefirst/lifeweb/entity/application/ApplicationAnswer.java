package com.bethefirst.lifeweb.entity.application;

import com.bethefirst.lifeweb.entity.campaign.ApplicationQuestion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationAnswer {//신청서답변

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_answer_id")
	private Long id;//신청서답변ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_id")
	private Application application;//신청서 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_question_id")
	private ApplicationQuestion applicationQuestion;//신청서질문 FK

	private String answer;//답변

	public ApplicationAnswer(Application application, ApplicationQuestion applicationQuestion, String answer) {
		this.application = application;
		this.applicationQuestion = applicationQuestion;
		this.answer = answer;
	}

	/** 신청서답변 수정 */
	public void updateApplicationAnswer(String answer) {
		this.answer = answer;
	}
}
