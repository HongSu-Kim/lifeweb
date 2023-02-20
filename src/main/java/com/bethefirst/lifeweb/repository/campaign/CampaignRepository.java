package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long>, CampaignRepositoryQueryDsl {

	/** 캠페인 조회 */
	@Override
	@EntityGraph(attributePaths = { "campaignCategory", "campaignType", "sns", "campaignLocal.local", "campaignImageList", "application" })
	Optional<Campaign> findById(Long campaignId);
	
	/** 캠페인 PICK 수정 */
	@Modifying
	@Query("update Campaign c " +
			"set c.pick = :pick " +
			"where c.id in :campaignIdList")
	void updatePick(@Param("pick") Boolean pick,
					@Param("campaignIdList") List<Long> campaignIdList);

	/** 캠페인 PICK 카운트 */
	Long countByPick(boolean pick);

}
