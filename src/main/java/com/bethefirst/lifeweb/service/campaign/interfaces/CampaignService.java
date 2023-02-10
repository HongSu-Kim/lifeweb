package com.bethefirst.lifeweb.service.campaign.interfaces;

import com.bethefirst.lifeweb.dto.campaign.CampaignDto;
import com.bethefirst.lifeweb.dto.campaign.CreateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.CampaignSearchRequirements;
import com.bethefirst.lifeweb.dto.campaign.UpdateCampaignDto;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import org.springframework.data.domain.Page;

public interface CampaignService {

	/** 캠페인 생성 */
	Long createCampaign(CreateCampaignDto createCampaignDto);

	/** 캠페인 조회 */
	CampaignDto getCampaignDto(Long campaignId);

	/** 캠페인 리스트 조회 */
	Page<CampaignDto> getCampaignDtoPage(CampaignSearchRequirements searchRequirements);

	/** 캠페인 수정 */
	void updateCampaign(Long campaignId, UpdateCampaignDto campaignDto);

	/** 캠페인 상태 변경 */
	void updateStatus(Long campaignId, CampaignStatus status);

}
