package com.bethefirst.lifeweb.service.campaign;

import com.bethefirst.lifeweb.dto.campaign.*;
import com.bethefirst.lifeweb.entity.campaign.*;
import com.bethefirst.lifeweb.entity.member.Sns;
import com.bethefirst.lifeweb.repository.campaign.*;
import com.bethefirst.lifeweb.repository.member.SnsRepository;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignService;
import com.bethefirst.lifeweb.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class CampaignServiceImpl implements CampaignService {

	private final CampaignRepository campaignRepository;
	private final CampaignLocalRepository campaignLocalRepository;
	private final CampaignCategoryRepository campaignCategoryRepository;
	private final CampaignTypeRepository campaignTypeRepository;
	private final LocalRepository localRepository;
	private final CampaignImageRepository campaignImageRepository;
	private final CampaignSnsRepository campaignSnsRepository;
	private final SnsRepository snsRepository;
	private final ApplicationQuestionRepository applicationQuestionRepository;
	private final ImageUtil imageUtil;

	/** 캠페인 생성 */
	@Override
	public void createCampaign(CreateCampaignDto createCampaignDto) {

		// 캠페인 저장
		CampaignCategory campaignCategory = campaignCategoryRepository.findByName(createCampaignDto.getCategoryName())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다. " + createCampaignDto.getCategoryName()));
		CampaignType campaignType = campaignTypeRepository.findByName(createCampaignDto.getTypeName())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 타입입니다. " + createCampaignDto.getTypeName()));
		Campaign campaign = Campaign.createCampaign(campaignCategory, campaignType, createCampaignDto);

		campaignRepository.save(campaign);

		// 캠페인지역 저장
		if (createCampaignDto.getLocalName() != null) {
			Local local = localRepository.findByName(createCampaignDto.getLocalName())
					.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역입니다. " + createCampaignDto.getLocalName()));
			CampaignLocal campaignLocal = CampaignLocal.createCampaignLocal(campaign, local, createCampaignDto);

			campaignLocalRepository.save(campaignLocal);
		}

		// 캠페인이미지 저장
		try {
			// 이미지 파일 저장
			List<String> fileNameList = imageUtil.store(createCampaignDto.getUploadFile(), "campaign");
			// DB에 이미지이름 저장
			fileNameList.forEach(fileName -> campaignImageRepository.save(CampaignImage.createCampaignImage(campaign, fileName)));
		} catch (IOException e) {
			throw new RuntimeException("이미지 저장에 실패했습니다.");
		}

		// 캠페인SNS 저장
		Sns sns = snsRepository.findByName(createCampaignDto.getSnsName())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 SNS입니다. " + createCampaignDto.getSnsName()));

		campaignSnsRepository.save(CampaignSns.createCampaignSns(campaign, sns, createCampaignDto.getHeadcount()));


		// 신청서질문 저장
		createCampaignDto.getApplicationQuestionDtoList()
				.forEach(applicationQuestionDto -> applicationQuestionRepository.save(ApplicationQuestion.createApplicationQuestion(campaign, applicationQuestionDto)));

	}

	/** 캠페인 조회 */
	@Transactional(readOnly = true)
	@Override
	public CampaignDto getCampaignDto(Long campaignId) {
		return new CampaignDto(campaignRepository.findById(campaignId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인입니다. " + campaignId)));
	}

	/** 캠페인 리스트 조회 */
	@Transactional(readOnly = true)
	@Override
	public Page<CampaignDto> getCampaignDtoList(SearchRequirements searchRequirements) {
		return campaignRepository.findAllBySearchRequirements(searchRequirements).map(CampaignDto::new);
	}

	/** 캠페인 수정 */
	@Override
	public void updateCampaign(Long campaignId, UpdateCampaignDto updateCampaignDto) {

		//캠페인 수정
		Campaign campaign = campaignRepository.findById(campaignId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인입니다. " + campaignId));
		CampaignCategory campaignCategory = campaignCategoryRepository.findByName(updateCampaignDto.getCategoryName())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다. " + updateCampaignDto.getCategoryName()));
		CampaignType campaignType = campaignTypeRepository.findByName(updateCampaignDto.getTypeName())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 타입입니다. " + updateCampaignDto.getTypeName()));

		campaign.update(campaignCategory, campaignType, updateCampaignDto);

		//캠페인지역 수정
		CampaignLocal campaignLocal = campaignLocalRepository.findById(campaign.getCampaignLocal().getId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인지역입니다. " + campaign.getCampaignLocal().getId()));
		Local local = localRepository.findByName(updateCampaignDto.getLocalName())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역입니다. " + updateCampaignDto.getLocalName()));

		campaignLocal.update(campaign, local, updateCampaignDto);

		//캠페인SNS 수정
		CampaignSns campaignSns = campaignSnsRepository.findById(campaign.getCampaignSns().getId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인SNS입니다. " + campaign.getCampaignSns().getId()));
		Sns sns = snsRepository.findByName(updateCampaignDto.getSnsName())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 SNS입니다. " + updateCampaignDto.getSnsName()));

		campaignSns.update(campaign, sns, updateCampaignDto.getHeadcount());

		//캠페인이미지 수정


		//신청서질문 수정
		List<ApplicationQuestion> applicationQuestionList = campaign.getApplicationQuestionList();
		List<ApplicationQuestionDto> applicationQuestionDtoList = updateCampaignDto.getApplicationQuestionDtoList();

		for (ApplicationQuestion applicationQuestion : applicationQuestionList) {
			boolean result = false;
			for (ApplicationQuestionDto applicationQuestionDto : applicationQuestionDtoList) {
				// 신청서질문 update
				if (applicationQuestionDto.getApplicationQuestionId().equals(applicationQuestion.getId())) {
					applicationQuestion.update(applicationQuestionDto);
					result = true;
					break;
				}
			}
			// 신청서질문 delete
			if (!result) {
				applicationQuestionRepository.delete(applicationQuestion);
			}
		}
		// 신청서질문 insert
		applicationQuestionDtoList.stream().filter(applicationQuestionDto -> applicationQuestionDto.getApplicationQuestionId() == 0)
				.forEach(applicationQuestionDto -> applicationQuestionRepository.save(ApplicationQuestion.createApplicationQuestion(campaign, applicationQuestionDto)));

	}

	/** 캠페인 상태 변경 */
	@Override
	public void updateStatus(Long campaignId, CampaignStatus status) {

		Campaign campaign = campaignRepository.findById(campaignId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인입니다. " + campaignId));

		campaign.updateStatus(status);

	}

}
