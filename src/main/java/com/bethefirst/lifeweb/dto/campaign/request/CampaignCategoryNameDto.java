package com.bethefirst.lifeweb.dto.campaign.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCategoryNameDto {

	@NotBlank(message = "카테고리명은 필수 입력 값입니다.")
	private String campaignCategoryName;

}
