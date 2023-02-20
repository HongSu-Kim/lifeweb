package com.bethefirst.lifeweb.dto.application.request;

import com.bethefirst.lifeweb.dto.application.response.ApplicationQuestionDto;
import com.bethefirst.lifeweb.entity.application.QuestionType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateApplicationQuestionDto {

	private List<String> question = new ArrayList<>();//질문
	private List<QuestionType> type;//유형
	private List<String> items;//항목

	public List<ApplicationQuestionDto> getApplicationQuestionDtoList() {
		List<ApplicationQuestionDto> list = new ArrayList<>();
		for (int i = 0; i < question.size(); i++) {
			list.add(new ApplicationQuestionDto(question.get(i), type.get(i),
					type.get(i) == QuestionType.RADIO || type.get(i) == QuestionType.CHECKBOX ? items.get(i) : null));
		}
		return list;
	}

}
