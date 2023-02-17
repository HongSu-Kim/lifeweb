package com.bethefirst.lifeweb.dto.application.response;

import com.bethefirst.lifeweb.entity.application.Application;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {

	private Long id;//신청서ID

	private List<ApplicationQuestionDto> applicationQuestionDtoList;//신청서질문


	public ApplicationDto(Application application) {

		id = application.getId();

		applicationQuestionDtoList = application.getApplicationQuestionList()
				.stream().map(ApplicationQuestionDto::new)
				.collect(Collectors.toList());

	}

}
