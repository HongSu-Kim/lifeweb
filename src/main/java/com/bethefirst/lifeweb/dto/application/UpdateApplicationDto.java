package com.bethefirst.lifeweb.dto.application;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateApplicationDto {

	@NotNull(message = "신청서ID는 필수 입력 값입니다.")
	private Long applicationId;//신청서ID
	private String memo;//메모

	private List<Long> applicationAnswerId;//답변ID
	private List<Long> applicationQuestionId;//질문ID
	private List<String> answer;//답변

	public List<ApplicationAnswerDto> getApplicationAnswerDtoList() {
		List<ApplicationAnswerDto> list = new ArrayList<>();
		for (int i = 0; i < answer.size(); i++) {
			list.add(new ApplicationAnswerDto(applicationAnswerId.get(i), applicationQuestionId.get(i), answer.get(i)));
		}
		return list;
	}

}
