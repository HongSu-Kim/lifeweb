package com.bethefirst.lifeweb.dto.campaign.request;

import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignStatusDto {

	@NotNull(message = "상태는 필수 입력 값입니다.")
	private CampaignStatus status;

}
