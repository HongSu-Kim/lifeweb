package com.bethefirst.lifeweb.dto.application.request;

import com.bethefirst.lifeweb.dto.application.response.ApplicantAnswerDto;
import com.bethefirst.lifeweb.entity.application.Applicant;
import com.bethefirst.lifeweb.entity.application.Application;
import com.bethefirst.lifeweb.entity.member.Member;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateApplicantDto {

	@NotNull(message = "신청서ID는 필수 입력 값입니다.")
	private Long applicationId;//신청서ID

	private String memo;//메모
	
	private List<Long> applicationQuestionId;//신청서질문ID
	private List<String> answer;//답변

	public List<ApplicantAnswerDto> getApplicantAnswerDtoList() {
		List<ApplicantAnswerDto> list = new ArrayList<>();
		for (int i = 0; i < answer.size(); i++) {
			list.add(new ApplicantAnswerDto(applicationQuestionId.get(i), answer.get(i)));
		}
		return list;
	}

	/** 신청자 생성 */
	public Applicant createApplicant(Member member, Application application) {
		return new Applicant(member, application, memo);
	}

}
