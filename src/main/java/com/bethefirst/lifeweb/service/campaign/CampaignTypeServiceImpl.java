package com.bethefirst.lifeweb.service.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignTypeDto;
import com.bethefirst.lifeweb.entity.campaign.CampaignType;
import com.bethefirst.lifeweb.repository.campaign.CampaignTypeRepository;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignTypeService;
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
public class CampaignTypeServiceImpl implements CampaignTypeService {

	private final CampaignTypeRepository campaignTypeRepository;

	/** 캠페인타입 생성 */
	@Override
	public void createCampaignType(String campaignTypeName) {
		campaignTypeRepository.save(CampaignType.createCampaignType(campaignTypeName));
	}

	/** 캠페인타입 리스트 조회 */
	@Transactional(readOnly = true)
	@Override
	public List<CampaignTypeDto> getCampaignTypeDtoList() {
		return campaignTypeRepository.findAll().stream().map(CampaignTypeDto::new).collect(Collectors.toList());
	}

	/** 캠페인타입 수정 */
	@Override
	public void updateCampaignType(Long campaignTypeId, String campaignTypeName) {
		campaignTypeRepository.findById(campaignTypeId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인타입입니다. " + campaignTypeId))
				.updateCampaignType(campaignTypeName);
	}

	/** 캠페인타입 삭제 */
	@Override
	public void deleteCampaignType(Long campaignTypeId) {
		campaignTypeRepository.deleteById(campaignTypeId);
	}

}
