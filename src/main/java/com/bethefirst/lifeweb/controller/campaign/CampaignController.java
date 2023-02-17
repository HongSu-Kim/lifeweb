package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.dto.campaign.response.CampaignDto;
import com.bethefirst.lifeweb.dto.campaign.request.CampaignSearchRequirements;
import com.bethefirst.lifeweb.dto.campaign.request.CreateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.request.UpdateCampaignDto;
import com.bethefirst.lifeweb.exception.ForbiddenException;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignService;
import com.bethefirst.lifeweb.util.security.SecurityUtil;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

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
	public Page<CampaignDto> readAll(CampaignSearchRequirements searchRequirements,
									 @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		if (searchRequirements.getMemberId() != null) {
			Optional<Long> currentMemberId = SecurityUtil.getCurrentMemberId();
			Long memberId = currentMemberId
					.orElseThrow(() -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));
			if (!searchRequirements.getMemberId().equals(memberId)) {
				throw new ForbiddenException("권한 없음");
			}
		}

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
	
//	/** 캠페인 상태 변경 */
//	@PutMapping("/{campaignId}/status")
//	public void updateStatus(@PathVariable Long campaignId, @Valid @NotEmpty(message = "상태는 필수 입력 값입니다.") CampaignStatus status) {
//		campaignService.updateStatus(campaignId, status);
//	}

}
