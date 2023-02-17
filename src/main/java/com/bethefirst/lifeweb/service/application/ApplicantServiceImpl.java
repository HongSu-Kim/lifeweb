package com.bethefirst.lifeweb.service.application;

import com.bethefirst.lifeweb.dto.application.request.ApplicantSearchRequirements;
import com.bethefirst.lifeweb.dto.application.request.CreateApplicantDto;
import com.bethefirst.lifeweb.dto.application.request.UpdateApplicantDto;
import com.bethefirst.lifeweb.dto.application.response.ApplicantAnswerDto;
import com.bethefirst.lifeweb.dto.application.response.ApplicantDto;
import com.bethefirst.lifeweb.entity.application.*;
import com.bethefirst.lifeweb.entity.application.ApplicationQuestion;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.repository.application.ApplicationAnswerRepository;
import com.bethefirst.lifeweb.repository.application.ApplicantRepository;
import com.bethefirst.lifeweb.repository.application.ApplicationRepository;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.service.application.interfaces.ApplicantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ApplicantServiceImpl implements ApplicantService {

	private final ApplicantRepository applicantRepository;
	private final ApplicationAnswerRepository applicationAnswerRepository;
	private final MemberRepository memberRepository;
	private final ApplicationRepository applicationRepository;

	/** 신청자 생성 */
	@Override
	public Long createApplicant(Long memberId, CreateApplicantDto createApplicationDto) {
		
		// 신청자 저장
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. " + memberId));
		Application application = applicationRepository.findById(createApplicationDto.getApplicationId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인입니다. " + createApplicationDto.getApplicationId()));

		Applicant applicant = createApplicationDto.createApplicant(member, application);

		Long applicantId = applicantRepository.save(applicant).getId();

		// 신청자답변 저장
		List<ApplicationQuestion> applicationQuestionList = application.getApplicationQuestionList();
		List<ApplicantAnswerDto> applicantAnswerDtoList = createApplicationDto.getApplicantAnswerDtoList();

		if (!CollectionUtils.isEmpty(applicationQuestionList)) {
			// 신청자답변의 입력값이 부족한 경우
			if (applicationQuestionList.size() > createApplicationDto.getApplicationQuestionId().size()) {
				throw new IllegalArgumentException("신청서질문ID는 필수 입력 값입니다.");
			} else if (applicationQuestionList.size() > createApplicationDto.getAnswer().size()) {
				throw new IllegalArgumentException("답변은 필수 입력 값입니다.");
			}

			for (ApplicationQuestion applicationQuestion : applicationQuestionList) {
				for (ApplicantAnswerDto applicantAnswerDto : applicantAnswerDtoList) {
					if (applicantAnswerDto.getId().equals(applicationQuestion.getId())) {
						applicationAnswerRepository.save(applicantAnswerDto.createAnswer(applicant, applicationQuestion));
						break;
					}
				}
			}
		}

		return applicantId;
	}

	/** 신청자 조회 */
	@Transactional(readOnly = true)
	@Override
	public ApplicantDto getApplicantDto(Long applicantId) {
		return new ApplicantDto(applicantRepository.findById(applicantId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청자입니다. " + applicantId)));
	}

	/** 신청자 리스트 조회 */
	@Transactional(readOnly = true)
	@Override
	public Page<ApplicantDto> getApplicantDtoList(ApplicantSearchRequirements searchRequirements) {
		return applicantRepository.findAllBySearchRequirements(searchRequirements).map(ApplicantDto::new);
	}

	/** 신청자 수정 */
	@Override
	public void updateApplicant(Long applicantId, UpdateApplicantDto updateApplicantDto) {
		
		Applicant application = applicantRepository.findById(applicantId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청자입니다. " + applicantId));

		//  캠페인 상태 != 신청 || 신청서 상태 == 선정 일 떄 수정 불가
		if (!application.getApplication().getCampaign().getStatus().equals(CampaignStatus.APPLICATION)) {
			throw new IllegalArgumentException("캠페인 신청기간에만 수정할 수 있습니다");
		} else if (application.getStatus().equals(ApplicantStatus.SELECT)) {
			throw new IllegalArgumentException("선정된 신청자는 수정할 수 없습니다.");
		}

		// 신청자 수정
		application.updateApplicant(updateApplicantDto.getMemo());

		// 신청자답변 수정
		List<ApplicantAnswer> applicantAnswerList = application.getApplicantAnswerList();
		List<ApplicantAnswerDto> applicantAnswerDtoList = updateApplicantDto.getAnswerDtoList();

		if (!CollectionUtils.isEmpty(applicantAnswerList)) {
			// 신청자답변의 입력값이 부족한 경우
			if (applicantAnswerList.size() > updateApplicantDto.getApplicationQuestionId().size()) {
				throw new IllegalArgumentException("신청서질문ID는 필수 입력 값입니다.");
			} else if (applicantAnswerList.size() > updateApplicantDto.getAnswer().size()) {
				throw new IllegalArgumentException("답변은 필수 입력 값입니다.");
			}

			for (ApplicantAnswer applicantAnswer : applicantAnswerList) {
				for (ApplicantAnswerDto applicantAnswerDto : applicantAnswerDtoList) {
					if (applicantAnswerDto.getId().equals(applicantAnswer.getId())) {
						applicantAnswer.updateApplicationAnswer(applicantAnswerDto.getAnswer());
						break;
					}
				}
			}
		}

	}

	/** 신청자 상태 수정 */
	@Override
	public void updateStatus(Long applicantId, ApplicantStatus status) {
		applicantRepository.findById(applicantId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청자입니다. " + applicantId))
				.updateApplicantStatus(status);
	}

	/** 신청자 삭제 */
	@Override
	public void deleteApplicant(Long applicationId) {
		applicantRepository.deleteById(applicationId);
	}

}
