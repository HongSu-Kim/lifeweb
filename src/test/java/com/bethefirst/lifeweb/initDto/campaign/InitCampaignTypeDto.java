package com.bethefirst.lifeweb.initDto.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignTypeDto;

import java.util.Arrays;
import java.util.List;

public class InitCampaignTypeDto {

	public CampaignTypeDto getCampaignTypeDto() {
		return getCampaignTypeDtoList().get(0);
	}

	public List<CampaignTypeDto> getCampaignTypeDtoList() {
		return Arrays.asList(
				new CampaignTypeDto(1L, "방문형"),
				new CampaignTypeDto(2L, "배송형"),
				new CampaignTypeDto(3L, "기자단"),
				new CampaignTypeDto(4L, "방문기자단")
		);
	}

}
