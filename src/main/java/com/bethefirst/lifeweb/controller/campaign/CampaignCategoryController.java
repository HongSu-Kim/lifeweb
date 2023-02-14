package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
	public ResponseEntity<?> create(@Valid @NotEmpty(message = "카테고리명은 필수 입력 값입니다.") String campaignCategoryName) {

		// 캠페인카테고리 생성
		campaignCategoryService.createCampaignCategory(campaignCategoryName);

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
									@Valid @NotEmpty(message = "카테고리명은 필수 입력 값입니다.") String campaignCategoryName) {

		// 캠페인카테고리 수정
		campaignCategoryService.updateCampaignCategory(campaignCategoryId, campaignCategoryName);

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
