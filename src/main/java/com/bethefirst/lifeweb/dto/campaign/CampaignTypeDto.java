package com.bethefirst.lifeweb.dto.campaign;

import com.bethefirst.lifeweb.entity.campaign.CampaignType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignTypeDto {

	private Long id;
	private String name;

	public CampaignTypeDto(CampaignType campaignType) {
		id = campaignType.getId();
		name = campaignType.getName();
	}

}
