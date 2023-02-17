package com.bethefirst.lifeweb.repository.application;

import com.bethefirst.lifeweb.entity.application.Applicant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long>, ApplicantRepositoryQueryDsl {

	/** 신청자 조회 */
	@Override
	@EntityGraph(attributePaths = { "member", "application", "applicantAnswerList" })
	Optional<Applicant> findById(Long applicantId);

	/** 회원ID와 캠페인ID가 같은 신청서를 조회 */
	Optional<Applicant> findByMemberIdAndApplicationCampaignId(Long memberId, Long campaignId);

}
