package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.entity.campaign.CampaignSns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignSnsRepository extends JpaRepository<CampaignSns, Long> {
}
