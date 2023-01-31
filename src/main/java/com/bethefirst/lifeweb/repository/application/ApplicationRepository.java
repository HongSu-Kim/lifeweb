package com.bethefirst.lifeweb.repository.application;

import com.bethefirst.lifeweb.entity.application.Application;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>, ApplicationRepositoryQueryDsl {

	/** 신청서 조회 */
	@Override
	@EntityGraph(attributePaths = { "member", "campaign" })
//	@EntityGraph(attributePaths = { "member", "campaign", "applicationAnswerList" })
	Optional<Application> findById(Long applicationId);

	/** 회원ID와 캠페인ID가 같은 신청서를 조회 */
	Optional<Application> findByMember_IdAndCampaign_Id(Long memberId,Long campaignId);
}
