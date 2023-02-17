package com.bethefirst.lifeweb.dto.application.request;

import com.bethefirst.lifeweb.dto.application.response.ApplicantAnswerDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicantDto {

	@NotNull(message = "신청자ID는 필수 입력 값입니다.")
	private Long applicantId;//신청자ID
	private String memo;//메모

	private List<Long> applicantAnswerId;//신청자답변ID
	private List<Long> applicationQuestionId;//신청서질문ID
	private List<String> answer;//답변

	public List<ApplicantAnswerDto> getAnswerDtoList() {
		List<ApplicantAnswerDto> list = new ArrayList<>();
		for (int i = 0; i < answer.size(); i++) {
			list.add(new ApplicantAnswerDto(applicantAnswerId.get(i), applicationQuestionId.get(i), answer.get(i)));
		}
		return list;
	}

}
