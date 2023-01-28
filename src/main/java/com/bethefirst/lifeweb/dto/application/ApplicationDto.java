package com.bethefirst.lifeweb.dto.application;

import com.bethefirst.lifeweb.dto.campaign.CampaignDto;
import com.bethefirst.lifeweb.dto.member.MemberDto;
import com.bethefirst.lifeweb.entity.application.Application;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationDto {

	private Long id;//신청서ID

	private MemberDto memberDto;//회원
	private CampaignDto campaignDto;//캠페인

	private String memo;//메모
	private LocalDateTime created;//신청일

	private List<ApplicationAnswerDto> applicationAnswerDtoList;//신청서답변

	public ApplicationDto(Application application) {

		id = application.getId();

		memberDto = new MemberDto(application.getMember());
		campaignDto = new CampaignDto(application.getCampaign());

		memo = application.getMemo();
		created = application.getCreated();

		applicationAnswerDtoList = application.getApplicationAnswerList()
				.stream().map(ApplicationAnswerDto::new)
				.collect(Collectors.toList());

	}

}
