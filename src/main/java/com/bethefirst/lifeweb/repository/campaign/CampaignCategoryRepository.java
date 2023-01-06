package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.entity.campaign.CampaignCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignCategoryRepository extends JpaRepository<CampaignCategory, Long> {
	Optional<CampaignCategory> findByName(String categoryName);
}
