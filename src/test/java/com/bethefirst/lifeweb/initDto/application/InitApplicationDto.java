package com.bethefirst.lifeweb.initDto.application;

import com.bethefirst.lifeweb.dto.application.request.CreateApplicationQuestionDto;
import com.bethefirst.lifeweb.dto.application.response.ApplicationDto;
import com.bethefirst.lifeweb.dto.application.response.ApplicationQuestionDto;
import com.bethefirst.lifeweb.entity.application.QuestionType;

import java.util.Arrays;
import java.util.List;

public class InitApplicationDto {

	public ApplicationDto getApplicationDto() {
		return new ApplicationDto(1L, applicationQuestionDtoList);
	}

	public CreateApplicationQuestionDto getCreateApplicationQuestionDto() {
		return new CreateApplicationQuestionDto(Arrays.asList("테스트 질문1", "테스트 질문2"), Arrays.asList(QuestionType.TEXT, QuestionType.CHECKBOX), Arrays.asList("", "111,222,333"));
	}

	private List<ApplicationQuestionDto> applicationQuestionDtoList = Arrays.asList(
			new ApplicationQuestionDto(1L, "단답형 질문", QuestionType.TEXT, ""),
			new ApplicationQuestionDto(2L , "복수선택 질문", QuestionType.CHECKBOX, "항목1,항목2,항목3")
	);

}
