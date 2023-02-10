package com.bethefirst.lifeweb.initDto.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignCategoryDto;
import com.bethefirst.lifeweb.dto.campaign.LocalDto;

import java.util.Arrays;
import java.util.List;

public class InitLocalDto {

	public LocalDto getLocalDto() {
		return getLocalDtoList().get(0);
	}

	public List<LocalDto> getLocalDtoList() {
		return Arrays.asList(
				new LocalDto(1L, "서울"),
				new LocalDto(2L, "경기/인천"),
				new LocalDto(3L, "대전/충청"),
				new LocalDto(4L, "대구/경북"),
				new LocalDto(5L, "부산/경남"),
				new LocalDto(6L, "광주/전라"),
				new LocalDto(7L, "다른지역")
		);
	}

}
