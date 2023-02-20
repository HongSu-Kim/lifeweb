package com.bethefirst.lifeweb.dto.campaign.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignTypeNameDto {

	@NotEmpty(message = "타입명은 필수 입력 값입니다.")
	private String campaignTypeName;

}
