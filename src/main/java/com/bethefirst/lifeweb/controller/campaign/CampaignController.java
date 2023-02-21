package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.dto.campaign.request.*;
import com.bethefirst.lifeweb.dto.campaign.response.CampaignDto;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("campaigns")
@RequiredArgsConstructor
@Slf4j
public class CampaignController {

	private final CampaignService campaignService;

	/** 캠페인 생성 */
	@PostMapping
	public ResponseEntity<?> create(@Valid CreateCampaignDto createCampaignDto) {

		// 캠페인 생성
		Long campaignId = campaignService.createCampaign(createCampaignDto);

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/campaigns/" + campaignId));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 캠페인 조회 */
	@GetMapping("/{campaignId}")
	public CampaignDto read(@PathVariable Long campaignId) {
		return campaignService.getCampaignDto(campaignId);
	}

	/** 캠페인 리스트 조회 */
	@GetMapping
	@PreAuthorize("(!isAuthenticated() and #searchRequirements.memberId == null) " +
			"or (isAuthenticated() and ((#searchRequirements.memberId == principal.memberId) or hasRole('ADMIN')))")
	public Page<CampaignDto> readAll(CampaignSearchRequirements searchRequirements,
									 @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		searchRequirements.setPageable(pageable);
		return campaignService.getCampaignDtoPage(searchRequirements);
	}

	/** 캠페인 수정 */
	@PutMapping("/{campaignId}")
	public ResponseEntity<?> update(@PathVariable Long campaignId,
									@Valid UpdateCampaignDto updateCampaignDto) {

		// 캠페인 수정
		campaignService.updateCampaign(campaignId, updateCampaignDto);

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/campaigns/" + campaignId));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/** 캠페인 상태 변경 */
	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping("/{campaignId}/status")
	public void updateStatus(@PathVariable Long campaignId,
							 @Valid @RequestBody CampaignStatusDto campaignStatusDto) {
		campaignService.updateStatus(campaignId, campaignStatusDto.getStatus());
	}

	/** 캠페인 PICK 체크 */
	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping("/pick")
	public void updatePick(@RequestBody UpdateCampaignPickDto updateCampaignPickDto) {
		campaignService.updatePick(updateCampaignPickDto);
	}

}
