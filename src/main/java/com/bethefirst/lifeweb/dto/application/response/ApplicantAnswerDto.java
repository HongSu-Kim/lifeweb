package com.bethefirst.lifeweb.dto.application.response;

import com.bethefirst.lifeweb.entity.application.ApplicantAnswer;
import com.bethefirst.lifeweb.entity.application.Applicant;
import com.bethefirst.lifeweb.entity.application.ApplicationQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicantAnswerDto {

	private Long id;//신청자답변ID

//	private ApplicantDto applicantDto;//신청자
//	private ApplicationQuestionDto applicationQuestionDto;//신청서질문
	private Long applicationQuestionId;//질문ID

	private String answer;//답변

	public ApplicantAnswerDto(ApplicantAnswer applicantAnswer) {

		id = applicantAnswer.getId();

//		applicantDto = new ApplicantDto(applicantAnswer.getApplicant());
//		applicationQuestionDto = new ApplicationQuestionDto(applicantAnswer.getApplicationQuestion());

		this.answer = applicantAnswer.getAnswer();

	}

	public ApplicantAnswerDto(Long applicationQuestionId, String answer) {
		this.applicationQuestionId = applicationQuestionId;
		this.answer = answer;
	}

	public ApplicantAnswerDto(Long answerId, Long applicationQuestionId, String answer) {
		this.id = answerId;
		this.applicationQuestionId = applicationQuestionId;
		this.answer = answer;
	}

	/** 신청자답변 생성 */
	public ApplicantAnswer createAnswer(Applicant applicant, ApplicationQuestion applicationQuestion) {
		return new ApplicantAnswer(applicant, applicationQuestion, answer);
	}

}
