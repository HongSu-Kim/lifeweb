package com.bethefirst.lifeweb.dto.campaign;

import com.bethefirst.lifeweb.entity.campaign.ApplicationQuestion;
import com.bethefirst.lifeweb.entity.campaign.QuestionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationQuestionDto {

	private Long applicationQuestionId;//질문
	private String question;//질문
	private QuestionType type;//유형
	private String items;//항목

	public ApplicationQuestionDto(ApplicationQuestion applicationQuestion) {
		applicationQuestionId = applicationQuestion.getId();
		question = applicationQuestion.getQuestion();
		type = applicationQuestion.getType();
		items = applicationQuestion.getItems();
	}

	public ApplicationQuestionDto(Long applicationQuestionId, String question, QuestionType type, String items) {
		this.applicationQuestionId = applicationQuestionId;
		this.question = question;
		this.type = type;
		this.items = items;
	}

	public ApplicationQuestionDto(String question, QuestionType type, String items) {
		this.question = question;
		this.type = type;
		this.items = items;
	}

}
