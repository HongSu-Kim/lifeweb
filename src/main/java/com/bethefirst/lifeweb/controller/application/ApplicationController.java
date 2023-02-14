package com.bethefirst.lifeweb.controller.application;

import com.bethefirst.lifeweb.dto.application.ApplicationDto;
import com.bethefirst.lifeweb.dto.application.ApplicationSearchRequirements;
import com.bethefirst.lifeweb.dto.application.CreateApplicationDto;
import com.bethefirst.lifeweb.dto.application.UpdateApplicationDto;
import com.bethefirst.lifeweb.entity.application.ApplicationStatus;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import com.bethefirst.lifeweb.service.application.interfaces.ApplicationService;
import com.bethefirst.lifeweb.util.security.SecurityUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("applications")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {

	private final ApplicationService applicationService;

	/** 신청서 생성 */
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody CreateApplicationDto createApplicationDto) {

		Long memberId = SecurityUtil.getCurrentMemberId()
				.orElseThrow(() -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

		// 신청서 생성
		Long applicationId = applicationService.createApplication(memberId, createApplicationDto);

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/applications/" + applicationId));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 신청서 조회 */
	@GetMapping("/{applicationId}")
	public Map<String, ?> read(@PathVariable Long applicationId) {
		return Map.of("content", applicationService.getApplicationDto(applicationId));
	}

	/** 신청서 리스트 조회 */
	@GetMapping
	public Page<ApplicationDto> readAll(@RequestBody ApplicationSearchRequirements searchRequirements) {
		return applicationService.getApplicationDtoList(searchRequirements);
	}

	/** 신청서 수정 */
	@PutMapping("/{applicationId}")
	public ResponseEntity<?> update(@PathVariable Long applicationId,
									@Valid @RequestBody UpdateApplicationDto updateApplicationDto) {

		// 신청서 수정
		applicationService.updateApplication(applicationId, updateApplicationDto);

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/applications/" + applicationId));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 신청서 상태 수정 */
	@PutMapping("/{applicationId}/status")
	public ResponseEntity<?> updateStatus(@PathVariable Long applicationId,
										  @Valid @NotEmpty(message = "상태는 필수 입력 값입니다.") ApplicationStatus status) {

		// 신청서 상태 수정
		applicationService.updateStatus(applicationId, status);

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/applications/" + applicationId));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 신청서 삭제 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{applicationId}")
	public void delete(@PathVariable Long applicationId) {
		applicationService.deleteApplication(applicationId);
	}

}
