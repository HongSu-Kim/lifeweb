package com.bethefirst.lifeweb.dto.application.response;

import com.bethefirst.lifeweb.entity.application.Application;
import com.bethefirst.lifeweb.entity.application.ApplicationQuestion;
import com.bethefirst.lifeweb.entity.application.QuestionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationQuestionDto {

	private Long id;//질문ID
	private String question;//질문
	private QuestionType type;//유형
	private String items;//항목

	public ApplicationQuestionDto(ApplicationQuestion applicationQuestion) {
		id = applicationQuestion.getId();
		this.question = applicationQuestion.getQuestion();
		type = applicationQuestion.getType();
		items = applicationQuestion.getItems() == null ? null : applicationQuestion.getItems();
	}

	public ApplicationQuestionDto(Long applicationQuestionId, String question, QuestionType type, String items) {
		this.id = applicationQuestionId;
		this.question = question;
		this.type = type;
		this.items = items;
	}

	public ApplicationQuestionDto(String question, QuestionType type, String items) {
		this.question = question;
		this.type = type;
		this.items = items;
	}

	/** 질문 생성 */
	public ApplicationQuestion createApplicationQuestion(Application application) {
		return new ApplicationQuestion(application, question, type,
					type == QuestionType.RADIO || type == QuestionType.CHECKBOX ? items : null);
	}

	/** 질문 수정 */
	public void updateApplicationQuestion(ApplicationQuestion applicationQuestion) {
		applicationQuestion.updateApplicationQuestion(this.question, type,
				items == null ? null : items);
	}

}
