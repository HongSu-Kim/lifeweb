package com.bethefirst.lifeweb.service.application.interfaces;

import com.bethefirst.lifeweb.dto.application.request.CreateApplicationQuestionDto;
import com.bethefirst.lifeweb.dto.application.response.ApplicantDto;
import com.bethefirst.lifeweb.dto.application.response.ApplicationDto;
import com.bethefirst.lifeweb.dto.application.response.ApplicationQuestionDto;
import com.bethefirst.lifeweb.entity.application.Application;
import com.bethefirst.lifeweb.entity.campaign.Campaign;

import java.util.List;

public interface ApplicationService {

	/** 신청서 생성 */
	void createApplication(Campaign campaign, List<ApplicationQuestionDto> applicationQuestionDtoList);

	/** 신청서 조회 */
	ApplicationDto getApplicationDto(Long applicationId);

	/** 신청서 수정 */
	void updateApplication(Application application, List<ApplicationQuestionDto> applicationQuestionDtoList);

	/** 신청서 질문 추가 */
	void createApplicationQuestion(Long applicationId, CreateApplicationQuestionDto createApplicationQuestionDto);

}
