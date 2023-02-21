package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.dto.campaign.request.CampaignCategoryNameDto;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("campaign-categories")
@RequiredArgsConstructor
@Slf4j
public class CampaignCategoryController {

	private final CampaignCategoryService campaignCategoryService;

	/** 캠페인카테고리 생성 */
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody CampaignCategoryNameDto campaignCategoryNameDto) {

		// 캠페인카테고리 생성
		campaignCategoryService.createCampaignCategory(campaignCategoryNameDto.getCampaignCategoryName());

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/campaign-categories"));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 캠페인카테고리 리스트 조회 */
	@GetMapping
	public Map<String, ?> readAll() {
		return Map.of("content", campaignCategoryService.getCampaignCategoryDtoList());
	}

	/** 캠페인카테고리 수정 */
	@PutMapping("/{campaignCategoryId}")
	public ResponseEntity<?> update(@PathVariable Long campaignCategoryId,
									@Valid @RequestBody CampaignCategoryNameDto campaignCategoryNameDto) {

		// 캠페인카테고리 수정
		campaignCategoryService.updateCampaignCategory(campaignCategoryId, campaignCategoryNameDto.getCampaignCategoryName());

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/campaign-categories"));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 캠페인카테고리 삭제 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{campaignCategoryId}")
	public void delete(@PathVariable Long campaignCategoryId) {
		campaignCategoryService.deleteCampaignCategory(campaignCategoryId);
	}

}
