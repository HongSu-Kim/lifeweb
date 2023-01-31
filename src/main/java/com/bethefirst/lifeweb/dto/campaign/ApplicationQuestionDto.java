package com.bethefirst.lifeweb.dto.campaign;

import com.bethefirst.lifeweb.entity.campaign.ApplicationQuestion;
import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.campaign.QuestionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationQuestionDto {

	private Long id;//질문ID
	private String question;//질문
	private QuestionType type;//유형
	private List<String> items;//항목

	public ApplicationQuestionDto(ApplicationQuestion applicationQuestion) {
		id = applicationQuestion.getId();
		question = applicationQuestion.getQuestion();
		type = applicationQuestion.getType();
		items = applicationQuestion.getItems() == null ? null : Arrays.asList(applicationQuestion.getItems().split("#"));
	}

	public ApplicationQuestionDto(Long applicationQuestionId, String question, QuestionType type, List<String> items) {
		this.id = applicationQuestionId;
		this.question = question;
		this.type = type;
		this.items = items;
	}

	public ApplicationQuestionDto(String question, QuestionType type, List<String> items) {
		this.question = question;
		this.type = type;
		this.items = items;
	}

	/** 신청서질문 생성 */
	public ApplicationQuestion createApplicationQuestion(Campaign campaign) {
		return new ApplicationQuestion(campaign, question, type,
					type == QuestionType.RADIO || type == QuestionType.CHECKBOX ? String.join("#" , items) : null);
	}

	/** 신청서질문 수정 */
	public void updateApplicationQuestion(ApplicationQuestion applicationQuestion) {
		applicationQuestion.updateApplicationQuestion(question, type,
				items == null ? null : String.join("#", items));
	}

}
