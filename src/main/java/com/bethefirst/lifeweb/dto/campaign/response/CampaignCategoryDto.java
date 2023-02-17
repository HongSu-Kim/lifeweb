package com.bethefirst.lifeweb.dto.campaign.response;

import com.bethefirst.lifeweb.entity.campaign.CampaignCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCategoryDto {

	private Long id;
	private String name;

	public CampaignCategoryDto(CampaignCategory campaignCategory) {
		id = campaignCategory.getId();
		name = campaignCategory.getName();
	}

}
