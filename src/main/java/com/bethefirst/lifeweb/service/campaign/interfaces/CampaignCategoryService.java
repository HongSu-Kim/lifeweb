package com.bethefirst.lifeweb.service.campaign.interfaces;

import com.bethefirst.lifeweb.dto.campaign.CampaignCategoryDto;

import java.util.List;

public interface CampaignCategoryService {

	/** 캠페인카테고리 생성 */
	void createCampaignCategory(String campaignCategoryName);

	/** 캠페인카테고리 리스트 조회 */
	List<CampaignCategoryDto> getCampaignCategoryDtoList();

	/** 캠페인카테고리 수정 */
	void updateCampaignCategory(Long campaignCategoryId, String campaignCategoryName);

	/** 캠페인카테고리 삭제 */
	void deleteCampaignCategory(Long campaignCategoryId);

}
