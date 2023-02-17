package com.bethefirst.lifeweb.service.application.interfaces;

import com.bethefirst.lifeweb.dto.application.response.ApplicationQuestionDto;
import com.bethefirst.lifeweb.entity.application.Application;
import com.bethefirst.lifeweb.entity.campaign.Campaign;

import java.util.List;

public interface ApplicationService {

	/** 신청서 생성 */
	void createApplication(Campaign campaign, List<ApplicationQuestionDto> applicationQuestionDtoList);

	/** 신청서 수정 */
	void updateApplication(Application application, List<ApplicationQuestionDto> applicationQuestionDtoList);

}
