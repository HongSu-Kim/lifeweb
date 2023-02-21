package com.bethefirst.lifeweb.dto.campaign.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UpdateCampaignPickDto {

	private List<Long> newCampaignId;
	private List<Long> oldCampaignId;

}
