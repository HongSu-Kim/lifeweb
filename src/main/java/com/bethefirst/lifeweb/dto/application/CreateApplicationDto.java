package com.bethefirst.lifeweb.dto.application;

import com.bethefirst.lifeweb.entity.application.Application;
import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.member.Member;
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
public class CreateApplicationDto {

	@NotNull(message = "캠페인ID는 필수 입력 값입니다.")
	private Long campaignId;//캠페인

	private String memo;//메모
	
	private List<Long> applicationQuestionId;//신청서질문
	private List<String> answer;//답변

	public List<ApplicationAnswerDto> getApplicationAnswerDtoList() {
		List<ApplicationAnswerDto> list = new ArrayList<>();
		for (int i = 0; i < answer.size(); i++) {
			list.add(new ApplicationAnswerDto(applicationQuestionId.get(i), answer.get(i)));
		}
		return list;
	}

	/** 신청서 생성 */
	public Application createApplication(Member member, Campaign campaign) {
		return new Application(member, campaign, memo);
	}

}
