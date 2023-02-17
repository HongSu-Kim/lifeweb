package com.bethefirst.lifeweb.repository.application;

import com.bethefirst.lifeweb.dto.application.request.ApplicantSearchRequirements;
import com.bethefirst.lifeweb.entity.application.Applicant;
import org.springframework.data.domain.Page;

public interface ApplicantRepositoryQueryDsl {

	/** 신청자 리스트 조회 */
	Page<Applicant>  findAllBySearchRequirements(ApplicantSearchRequirements searchRequirements);

}
