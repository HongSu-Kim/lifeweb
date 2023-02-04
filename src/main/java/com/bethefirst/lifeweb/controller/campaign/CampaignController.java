package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignDto;
import com.bethefirst.lifeweb.dto.campaign.CampaignSearchRequirements;
import com.bethefirst.lifeweb.dto.campaign.CreateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.UpdateCampaignDto;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("campaigns")
@RequiredArgsConstructor
@Slf4j
public class CampaignController {

	private final CampaignService campaignService;

	/** 캠페인 생성 */
	@PostMapping
	public Long create(@Valid CreateCampaignDto createCampaignDto) {
		return campaignService.createCampaign(createCampaignDto);
	}

	/** 캠페인 조회 */
	@GetMapping("/{campaignId}")
	public CampaignDto detail(@PathVariable Long campaignId) {
		return campaignService.getCampaignDto(campaignId);
	}

	/** 캠페인 리스트 조회 */
	@GetMapping
	public Page<CampaignDto> list(@RequestBody CampaignSearchRequirements searchRequirements) {
		return campaignService.getCampaignDtoList(searchRequirements);
	}

	/** 캠페인 수정 */
	@PutMapping("/{campaignId}")
	public void update(@PathVariable Long campaignId, @Valid UpdateCampaignDto updateCampaignDto) {
		campaignService.updateCampaign(campaignId, updateCampaignDto);
	}
	
	/** 캠페인 상태 변경 */
	@PutMapping("/{campaignId}/status")
	public void updateStatus(@PathVariable Long campaignId, @Valid @NotEmpty(message = "상태는 필수 입력 값입니다.") CampaignStatus status) {
		campaignService.updateStatus(campaignId, status);
	}

}
