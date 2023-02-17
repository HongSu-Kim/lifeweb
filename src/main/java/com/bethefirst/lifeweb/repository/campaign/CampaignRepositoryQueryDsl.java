package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.dto.campaign.request.CampaignSearchRequirements;
import com.bethefirst.lifeweb.entity.campaign.Campaign;
import org.springframework.data.domain.Page;

public interface CampaignRepositoryQueryDsl {

	/** 캠페인 리스트 조회 */
	Page<Campaign> findAllBySearchRequirements(CampaignSearchRequirements searchRequirements);

}
