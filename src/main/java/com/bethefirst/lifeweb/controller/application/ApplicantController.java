package com.bethefirst.lifeweb.controller.application;

import com.bethefirst.lifeweb.dto.application.response.ApplicantDto;
import com.bethefirst.lifeweb.dto.application.request.ApplicantSearchRequirements;
import com.bethefirst.lifeweb.dto.application.request.CreateApplicantDto;
import com.bethefirst.lifeweb.dto.application.request.UpdateApplicantDto;
import com.bethefirst.lifeweb.entity.application.ApplicantStatus;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import com.bethefirst.lifeweb.service.application.interfaces.ApplicantService;
import com.bethefirst.lifeweb.util.security.SecurityUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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

@RestController
@RequestMapping("applicants")
@RequiredArgsConstructor
@Slf4j
public class ApplicantController {

	private final ApplicantService applicantService;

	/** 신청자 생성 */
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody CreateApplicantDto createApplicantDto) {

//		Long memberId = SecurityUtil.getCurrentMemberId()
//				.orElseThrow(() -> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));
		Long memberId = 1L;

		// 신청자 생성
		Long applicantId = applicantService.createApplicant(memberId, createApplicantDto);

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/applicants/" + applicantId));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 신청자 조회 */
	@GetMapping("/{applicantId}")
	public ApplicantDto read(@PathVariable Long applicantId) {
		return applicantService.getApplicantDto(applicantId);
	}

	/** 신청자 리스트 조회 */
	@GetMapping
	public Page<ApplicantDto> readAll(ApplicantSearchRequirements searchRequirements,
									  @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		searchRequirements.setPageable(pageable);
		return applicantService.getApplicantDtoList(searchRequirements);
	}

	/** 신청자 수정 */
	@PutMapping("/{applicantId}")
	public ResponseEntity<?> update(@PathVariable Long applicantId,
									@Valid @RequestBody UpdateApplicantDto updateApplicantDto) {

		// 신청자 수정
		applicantService.updateApplicant(applicantId, updateApplicantDto);

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/applicants/" + applicantId));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 신청자 상태 수정 */
	@PutMapping("/{applicantId}/status")
	public ResponseEntity<?> updateStatus(@PathVariable Long applicantId,
										  @Valid @NotEmpty(message = "상태는 필수 입력 값입니다.") ApplicantStatus status) {

		// 신청자 상태 수정
		applicantService.updateStatus(applicantId, status);

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/applicants/" + applicantId));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 신청자 삭제 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{applicantId}")
	public void delete(@PathVariable Long applicantId) {
		applicantService.deleteApplicant(applicantId);
	}

}
