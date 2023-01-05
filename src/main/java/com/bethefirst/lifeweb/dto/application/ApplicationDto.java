package com.bethefirst.lifeweb.dto.application;

import com.bethefirst.lifeweb.dto.campaign.CampaignDto;
import com.bethefirst.lifeweb.dto.member.MemberDto;
import com.bethefirst.lifeweb.entity.application.Application;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationDto {

	private Long id;//신청서ID

	private MemberDto memberDto;//회원
	private CampaignDto campaignDto;//캠페인
	private String snsName;//SNS

	private String memo;//메모
	private LocalDateTime created;//신청일

	public ApplicationDto(Application application) {

		id = application.getId();

		memberDto = new MemberDto(application.getMember());
		campaignDto = new CampaignDto(application.getCampaign());
		snsName = application.getSns().getName();

		memo = application.getMemo();
		created = application.getCreated();

	}

}
