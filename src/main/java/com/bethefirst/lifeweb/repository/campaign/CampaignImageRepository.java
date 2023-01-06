package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.entity.campaign.CampaignImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignImageRepository extends JpaRepository<CampaignImage, Long> {
}
