package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.entity.campaign.CampaignType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignTypeRepository extends JpaRepository<CampaignType, Long> {
	Optional<CampaignType> findByName(String typeName);
}
