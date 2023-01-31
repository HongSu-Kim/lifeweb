package com.bethefirst.lifeweb.controller.application;

import com.bethefirst.lifeweb.dto.application.ApplicationDto;
import com.bethefirst.lifeweb.dto.application.ApplicationSearchRequirements;
import com.bethefirst.lifeweb.dto.application.CreateApplicationDto;
import com.bethefirst.lifeweb.dto.application.UpdateApplicationDto;
import com.bethefirst.lifeweb.entity.application.ApplicationStatus;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import com.bethefirst.lifeweb.service.application.interfaces.ApplicationService;
import com.bethefirst.lifeweb.util.security.SecurityUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("applications")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {

	private final ApplicationService applicationService;

	/** 신청서 생성 */
	@PostMapping
	public void create(@Valid @RequestBody CreateApplicationDto createApplicationDto) {

		Long memberId = SecurityUtil.getCurrentMemberId()
				.orElseThrow(() -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

		applicationService.createApplication(memberId, createApplicationDto);
	}

	/** 신청서 조회 */
	@GetMapping("/{applicationId}")
	public ApplicationDto detail(@PathVariable Long applicationId) {
		return applicationService.getApplicationDto(applicationId);
	}

	/** 신청서 리스트 조회 */
	@GetMapping
	public Page<ApplicationDto> list(@RequestBody ApplicationSearchRequirements searchRequirements) {
		return applicationService.getApplicationDtoList(searchRequirements);
	}

	/** 신청서 수정 */
	@PutMapping("/{applicationId}")
	public void update(@PathVariable Long applicationId, @Valid @RequestBody UpdateApplicationDto updateApplicationDto) {
		applicationService.updateApplication(applicationId, updateApplicationDto);
	}

	/** 신청서 상태 수정 */
	@PutMapping("/{applicationId}/status")
	public void update(@PathVariable Long applicationId, @Valid @NotEmpty(message = "상태는 필수 입력 값입니다.") ApplicationStatus status) {
		applicationService.updateStatus(applicationId, status);
	}

	/** 신청서 삭제 */
	@DeleteMapping("/{applicationId}")
	public void delete(@PathVariable Long applicationId) {
		applicationService.deleteApplication(applicationId);
	}

}
