package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignTypeDto;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignTypeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("campaign-types")
@RequiredArgsConstructor
@Slf4j
public class CampaignTypeController {

	private final CampaignTypeService campaignTypeService;

	/** 캠페인타입 생성 */
	@PostMapping
	public void create(@Valid @NotEmpty(message = "타입명은 필수 입력 값입니다.") String campaignTypeName) {
		campaignTypeService.createCampaignType(campaignTypeName);
	}

	/** 캠페인타입 리스트 조회 */
	@GetMapping
	public List<CampaignTypeDto> list() {
		return campaignTypeService.getCampaignTypeDtoList();
	}

	/** 캠페인타입 수정 */
	@PutMapping("/{campaignTypeId}")
	public void update(@PathVariable Long campaignTypeId, @Valid @NotEmpty(message = "타입명은 필수 입력 값입니다.") String campaignTypeName) {
		campaignTypeService.updateCampaignType(campaignTypeId, campaignTypeName);
	}

	/** 캠페인타입 삭제 */
	@DeleteMapping("/{campaignTypeId}")
	public void delete(@PathVariable Long campaignTypeId) {
		campaignTypeService.deleteCampaignType(campaignTypeId);
	}

}