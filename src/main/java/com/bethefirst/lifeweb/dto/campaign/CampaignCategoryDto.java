package com.bethefirst.lifeweb.dto.campaign;

import com.bethefirst.lifeweb.entity.campaign.CampaignCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CampaignCategoryDto {

	private Long id;
	private String name;

	public CampaignCategoryDto(CampaignCategory campaignCategory) {
		id = campaignCategory.getId();
		name = campaignCategory.getName();
	}

}
