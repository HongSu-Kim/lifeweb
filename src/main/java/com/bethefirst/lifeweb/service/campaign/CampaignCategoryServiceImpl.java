package com.bethefirst.lifeweb.service.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignCategoryDto;
import com.bethefirst.lifeweb.entity.campaign.CampaignCategory;
import com.bethefirst.lifeweb.repository.campaign.CampaignCategoryRepository;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CampaignCategoryServiceImpl implements CampaignCategoryService {

	private final CampaignCategoryRepository campaignCategoryRepository;

	/** 캠페인카테고리 생성 */
	@Override
	public void createCampaignCategory(String campaignCategoryName) {
		campaignCategoryRepository.save(CampaignCategory.createCampaignCategory(campaignCategoryName));
	}

	/** 캠페인카테고리 리스트 조회 */
	@Transactional(readOnly = true)
	@Override
	public List<CampaignCategoryDto> getCampaignCategoryDtoList() {
		return campaignCategoryRepository.findAll().stream().map(CampaignCategoryDto::new).collect(Collectors.toList());
	}

	/** 캠페인카테고리 수정 */
	@Override
	public void updateCampaignCategory(Long campaignCategoryId, String campaignCategoryName) {
		campaignCategoryRepository.findById(campaignCategoryId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인카테고리입니다. " + campaignCategoryId))
				.updateCampaignCategory(campaignCategoryName);
	}

	/** 캠페인카테고리 삭제 */
	@Override
	public void deleteCampaignCategory(Long campaignCategoryId) {
		campaignCategoryRepository.deleteById(campaignCategoryId);
	}

}
