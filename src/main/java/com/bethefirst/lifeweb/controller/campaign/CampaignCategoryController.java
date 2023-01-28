package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignCategoryDto;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("campaign-categories")
@RequiredArgsConstructor
@Slf4j
public class CampaignCategoryController {

	private final CampaignCategoryService campaignCategoryService;

	/** 캠페인카테고리 생성 */
	@PostMapping
	public void create(String campaignCategoryName) {
		campaignCategoryService.createCampaignCategory(campaignCategoryName);
	}

	/** 캠페인카테고리 리스트 조회 */
	@GetMapping
	public List<CampaignCategoryDto> list() {
		return campaignCategoryService.getCampaignCategoryDtoList();
	}

	/** 캠페인카테고리 수정 */
	@PutMapping("/{campaignCategoryId}")
	public void update(@PathVariable Long campaignCategoryId, String campaignCategoryName) {
		campaignCategoryService.updateCampaignCategory(campaignCategoryId, campaignCategoryName);
	}

	/** 캠페인카테고리 삭제 */
	@DeleteMapping("/{campaignCategoryId}")
	public void delete(@PathVariable Long campaignCategoryId) {
		campaignCategoryService.deleteCampaignCategory(campaignCategoryId);
	}

}
