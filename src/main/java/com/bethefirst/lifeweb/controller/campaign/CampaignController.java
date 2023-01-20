package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignDto;
import com.bethefirst.lifeweb.dto.campaign.CreateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.SearchRequirements;
import com.bethefirst.lifeweb.dto.campaign.UpdateCampaignDto;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("campaigns")
@Slf4j
public class CampaignController {

	private final CampaignService campaignService;

	/** 캠페인 생성 */
	@PostMapping
	public void create(CreateCampaignDto createCampaignDto) {
		campaignService.createCampaign(createCampaignDto);
	}

	/** 캠페인 조회 */
	@GetMapping("/{campaignId}")
	public CampaignDto detail(@PathVariable Long campaignId) {
		return campaignService.getCampaignDto(campaignId);
	}

	/** 캠페인 리스트 조회 */
	@GetMapping
	public Page<CampaignDto> list(SearchRequirements searchRequirements) {
		return campaignService.getCampaignDtoList(searchRequirements);
	}

	/** 캠페인 수정 */
	@PutMapping("/{campaignId}")
	public void update(@PathVariable Long campaignId, UpdateCampaignDto updateCampaignDto) {
		campaignService.updateCampaign(campaignId, updateCampaignDto);
	}
	
	/** 캠페인 상태 변경 */
	@PutMapping("/{campaignId}/status")
	public void update(@PathVariable Long campaignId, CampaignStatus status) {
		campaignService.updateStatus(campaignId, status);
	}

}
