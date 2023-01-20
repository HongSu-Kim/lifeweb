package com.bethefirst.lifeweb.dto.campaign;

import com.bethefirst.lifeweb.entity.campaign.CampaignSns;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CampaignSnsDto {

	private String snsName;//SNS
	private int headcount;//모집인원

	public CampaignSnsDto(CampaignSns campaignSns) {
		snsName = campaignSns.getSns().getName();
		headcount = campaignSns.getHeadcount();
	}

	public CampaignSnsDto(String snsName, int headcount) {
		this.snsName = snsName;
		this.headcount = headcount;
	}

}
