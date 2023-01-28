package com.bethefirst.lifeweb.repository.application;

import com.bethefirst.lifeweb.dto.application.ApplicationSearchRequirements;
import com.bethefirst.lifeweb.entity.application.Application;
import org.springframework.data.domain.Page;

public interface ApplicationRepositoryQueryDsl {

	/** 신청서 리스트 조회 */
	Page<Application>  findAllBySearchRequirements(ApplicationSearchRequirements searchRequirements);

}
