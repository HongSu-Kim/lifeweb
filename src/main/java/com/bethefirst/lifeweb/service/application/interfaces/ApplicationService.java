package com.bethefirst.lifeweb.service.application.interfaces;

import com.bethefirst.lifeweb.dto.application.ApplicationDto;
import com.bethefirst.lifeweb.dto.application.ApplicationSearchRequirements;
import com.bethefirst.lifeweb.dto.application.CreateApplicationDto;
import com.bethefirst.lifeweb.dto.application.UpdateApplicationDto;
import org.springframework.data.domain.Page;

public interface ApplicationService {

	/** 신청서 생성 */
	void createApplication(Long memberId, CreateApplicationDto createApplicationDto);

	/** 신청서 조회 */
	ApplicationDto getApplicationDto(Long applicationId);

	/** 신청서 리스트 조회 */
	Page<ApplicationDto> getApplicationDtoList(ApplicationSearchRequirements searchRequirements);

	/** 신청서 수정 */
	void updateApplication(Long applicationId, UpdateApplicationDto updateApplicationDto);

	/** 신청서 삭제 */
	void deleteApplication(Long applicationId);

}
