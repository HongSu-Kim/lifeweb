package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long>, CampaignRepositoryQueryDsl {

	@Override
	@EntityGraph(attributePaths = { "campaignCategory", "campaignType" })
	Optional<Campaign> findById(Long aLong);

}
