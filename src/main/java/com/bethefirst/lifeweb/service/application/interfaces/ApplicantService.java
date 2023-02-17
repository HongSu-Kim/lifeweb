package com.bethefirst.lifeweb.service.application.interfaces;

import com.bethefirst.lifeweb.dto.application.response.ApplicantDto;
import com.bethefirst.lifeweb.dto.application.request.ApplicantSearchRequirements;
import com.bethefirst.lifeweb.dto.application.request.CreateApplicantDto;
import com.bethefirst.lifeweb.dto.application.request.UpdateApplicantDto;
import com.bethefirst.lifeweb.entity.application.ApplicantStatus;
import org.springframework.data.domain.Page;

public interface ApplicantService {

	/** 신청자 생성 */
	Long createApplicant(Long memberId, CreateApplicantDto createApplicantDto);

	/** 신청자 조회 */
	ApplicantDto getApplicantDto(Long applicantId);

	/** 신청자 리스트 조회 */
	Page<ApplicantDto> getApplicantDtoList(ApplicantSearchRequirements searchRequirements);

	/** 신청자 수정 */
	void updateApplicant(Long applicantId, UpdateApplicantDto updateApplicantDto);

	/** 신청자 상태 수정 */
	void updateStatus(Long applicantId, ApplicantStatus status);

	/** 신청자 삭제 */
	void deleteApplicant(Long applicantId);

}
