package com.bethefirst.lifeweb.initDto.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignCategoryDto;

import java.util.Arrays;
import java.util.List;

public class InitCampaignCategoryDto {

	public CampaignCategoryDto getCampaignCategoryDto() {
		return getCampaignCategoryDtoList().get(0);
	}

	public List<CampaignCategoryDto> getCampaignCategoryDtoList() {
		return Arrays.asList(
				new CampaignCategoryDto(1L, "맛집"),
				new CampaignCategoryDto(2L, "뷰티"),
				new CampaignCategoryDto(3L, "숙박"),
				new CampaignCategoryDto(4L, "문화"),
				new CampaignCategoryDto(5L, "배달"),
				new CampaignCategoryDto(6L, "테이크아웃"),
				new CampaignCategoryDto(7L, "기타")
		);
	}

}
