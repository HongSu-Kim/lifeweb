package com.bethefirst.lifeweb.service.campaign.interfaces;

import com.bethefirst.lifeweb.dto.campaign.CampaignTypeDto;

import java.util.List;

public interface CampaignTypeService {

	/** 캠페인타입 생성 */
	void createCampaignType(String campaignTypeName);

	/** 캠페인타입 리스트 조회 */
	List<CampaignTypeDto> getCampaignTypeDtoList();

	/** 캠페인타입 수정 */
	void updateCampaignType(Long campaignTypeId, String campaignTypeName);

	/** 캠페인타입 삭제 */
	void deleteCampaignType(Long campaignTypeId);

}
