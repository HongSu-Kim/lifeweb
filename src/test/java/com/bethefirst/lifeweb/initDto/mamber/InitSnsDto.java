package com.bethefirst.lifeweb.initDto.mamber;

import com.bethefirst.lifeweb.dto.member.SnsDto;

import java.util.Arrays;
import java.util.List;

public class InitSnsDto {

	public SnsDto getSnsDto() {
		return getSnsDtoList().get(0);
	}

	public List<SnsDto> getSnsDtoList() {
		return Arrays.asList(
				new SnsDto(1L, "naver"),
				new SnsDto(2L, "instagram"),
				new SnsDto(3L, "youtube")
		);
	}

}
