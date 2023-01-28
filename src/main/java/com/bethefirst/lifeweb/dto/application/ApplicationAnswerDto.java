package com.bethefirst.lifeweb.dto.application;

import com.bethefirst.lifeweb.dto.campaign.ApplicationQuestionDto;
import com.bethefirst.lifeweb.entity.application.ApplicationAnswer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationAnswerDto {

	private Long id;//신청서답변ID

	private ApplicationDto applicationDto;//신청서
	private ApplicationQuestionDto applicationQuestionDto;//신청서질문
	private Long applicationQuestionId;//신청서질문ID

	private String answer;//답변

	public ApplicationAnswerDto(ApplicationAnswer applicationAnswer) {

		id = applicationAnswer.getId();

		applicationDto = new ApplicationDto(applicationAnswer.getApplication());
		applicationQuestionDto = new ApplicationQuestionDto(applicationAnswer.getApplicationQuestion());

		answer = applicationAnswer.getAnswer();

	}

	public ApplicationAnswerDto(Long applicationQuestionId, String answer) {
		this.applicationQuestionId = applicationQuestionId;
		this.answer = answer;
	}

	public ApplicationAnswerDto(Long applicationAnswerId, Long applicationQuestionId, String answer) {
		this.id = applicationAnswerId;
		this.applicationQuestionId = applicationQuestionId;
		this.answer = answer;
	}

}
