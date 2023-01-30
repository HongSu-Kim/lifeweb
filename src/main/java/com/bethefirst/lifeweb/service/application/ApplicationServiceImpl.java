package com.bethefirst.lifeweb.service.application;

import com.bethefirst.lifeweb.dto.application.*;
import com.bethefirst.lifeweb.entity.application.Application;
import com.bethefirst.lifeweb.entity.application.ApplicationAnswer;
import com.bethefirst.lifeweb.entity.campaign.ApplicationQuestion;
import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.repository.application.ApplicationAnswerRepository;
import com.bethefirst.lifeweb.repository.application.ApplicationRepository;
import com.bethefirst.lifeweb.repository.campaign.ApplicationQuestionRepository;
import com.bethefirst.lifeweb.repository.campaign.CampaignRepository;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.service.application.interfaces.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

	private final ApplicationRepository applicationRepository;
	private final ApplicationAnswerRepository applicationAnswerRepository;
	private final MemberRepository memberRepository;
	private final CampaignRepository campaignRepository;
	private final ApplicationQuestionRepository applicationQuestionRepository;

	/** 신청서 생성 */
	@Override
	public void createApplication(Long memberId, CreateApplicationDto createApplicationDto) {
		
		// 신청서 저장
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. " + memberId));
		Campaign campaign = campaignRepository.findById(createApplicationDto.getCampaignId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인입니다. " + createApplicationDto.getCampaignId()));

		Application application = createApplicationDto.createApplication(member, campaign);

		applicationRepository.save(application);

		// 신청서답변 저장
		for (ApplicationQuestion applicationQuestion : campaign.getApplicationQuestionList()) {
			for (ApplicationAnswerDto applicationAnswerDto : createApplicationDto.getApplicationAnswerDtoList()) {
				if (applicationAnswerDto.getId().equals(applicationQuestion.getId())) {
					applicationAnswerRepository.save(applicationAnswerDto.createApplicationAnswer(application, applicationQuestion));
					break;
				}
			}
		}
		
	}

	/** 신청서 조회 */
	@Transactional(readOnly = true)
	@Override
	public ApplicationDto getApplicationDto(Long applicationId) {
		return new ApplicationDto(applicationRepository.findById(applicationId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청서입니다. " + applicationId)));
	}

	/** 신청서 리스트 조회 */
	@Transactional(readOnly = true)
	@Override
	public Page<ApplicationDto> getApplicationDtoList(ApplicationSearchRequirements searchRequirements) {
		return applicationRepository.findAllBySearchRequirements(searchRequirements).map(ApplicationDto::new);
	}

	/** 신청서 수정 */
	@Override
	public void updateApplication(Long applicationId, UpdateApplicationDto updateApplicationDto) {

		// 신청서 수정
		Application application = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청서입니다. " + applicationId));

		application.updateApplication(updateApplicationDto.getMemo());

		// 신청서답변 수정
		for (ApplicationAnswer applicationAnswer : application.getApplicationAnswerList()) {
			for (ApplicationAnswerDto applicationAnswerDto : updateApplicationDto.getApplicationAnswerDtoList()) {
				if (applicationAnswerDto.getId().equals(applicationAnswer.getId())) {
					applicationAnswer.updateApplicationAnswer(applicationAnswerDto.getAnswer());
					break;
				}
			}
		}

	}

	/** 신청서 삭제 */
	@Override
	public void deleteApplication(Long applicationId) {
		applicationRepository.deleteById(applicationId);
	}

}
