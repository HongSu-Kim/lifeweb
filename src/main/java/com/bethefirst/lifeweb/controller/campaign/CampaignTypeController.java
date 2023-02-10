package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignTypeService;
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
@RequestMapping("campaign-types")
@RequiredArgsConstructor
@Slf4j
public class CampaignTypeController {

	private final CampaignTypeService campaignTypeService;

	/** 캠페인타입 생성 */
	@PostMapping
	public ResponseEntity<?> create(@Valid @NotEmpty(message = "타입명은 필수 입력 값입니다.") String campaignTypeName) {

		// 캠페인타입 생성
		campaignTypeService.createCampaignType(campaignTypeName);

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/campaign-types"));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 캠페인타입 리스트 조회 */
	@GetMapping
	public Map<String, ?> readAll() {
		return Map.of("content", campaignTypeService.getCampaignTypeDtoList());
	}

	/** 캠페인타입 수정 */
	@PutMapping("/{campaignTypeId}")
	public ResponseEntity<?> update(@PathVariable Long campaignTypeId,
									@Valid @NotEmpty(message = "타입명은 필수 입력 값입니다.") String campaignTypeName) {

		// 캠페인타입 수정
		campaignTypeService.updateCampaignType(campaignTypeId, campaignTypeName);

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/campaign-types"));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 캠페인타입 삭제 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{campaignTypeId}")
	public void delete(@PathVariable Long campaignTypeId) {
		campaignTypeService.deleteCampaignType(campaignTypeId);
	}

}
