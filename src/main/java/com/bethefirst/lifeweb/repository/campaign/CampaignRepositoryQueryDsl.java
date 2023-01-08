package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.dto.campaign.SearchRequirements;
import com.bethefirst.lifeweb.entity.campaign.Campaign;
import org.springframework.data.domain.Page;

public interface CampaignRepositoryQueryDsl {
	Page<Campaign> findAllBySearchRequirements(SearchRequirements searchRequirements);
}
