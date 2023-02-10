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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CampaignServiceImpl implements CampaignService {

	private final CampaignRepository campaignRepository;
	private final CampaignLocalRepository campaignLocalRepository;
	private final CampaignCategoryRepository campaignCategoryRepository;
	private final CampaignTypeRepository campaignTypeRepository;
	private final LocalRepository localRepository;
	private final CampaignImageRepository campaignImageRepository;
	private final SnsRepository snsRepository;
	private final ApplicationQuestionRepository applicationQuestionRepository;
	private final ImageUtil imageUtil;

	@Value("${image-folder.campaign}")
	private String imageFolder;

	/** 캠페인 생성 */
	@Override
	public Long createCampaign(CreateCampaignDto createCampaignDto) {

		// 캠페인 저장
		CampaignCategory campaignCategory = campaignCategoryRepository.findById(createCampaignDto.getCategoryId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다. " + createCampaignDto.getCategoryId()));
		CampaignType campaignType = campaignTypeRepository.findById(createCampaignDto.getTypeId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 타입입니다. " + createCampaignDto.getTypeId()));
		Sns sns = snsRepository.findById(createCampaignDto.getSnsId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 SNS입니다. " + createCampaignDto.getSnsId()));

		createCampaignDto.setFileName(imageUtil.store(createCampaignDto.getUploadFile(), imageFolder));//이미지 파일 저장

		Campaign campaign = createCampaignDto.createCampaign(campaignCategory, campaignType, sns);

		Long campaignId = campaignRepository.save(campaign).getId();

		// 캠페인지역 저장
		if (createCampaignDto.getLocalId() != null) {
			Local local = localRepository.findById(createCampaignDto.getLocalId())
					.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역입니다. " + createCampaignDto.getLocalId()));

			campaignLocalRepository.save(createCampaignDto.getCampaignLocalDto().createCampaignLocal(campaign, local));
		}

		// 캠페인이미지 저장
		//이미지 파일 저장
		List<String> fileNameList = imageUtil.store(createCampaignDto.getUploadFileList(), imageFolder);
		//DB에 이미지이름 저장
		fileNameList.forEach(fileName -> campaignImageRepository.save(new CampaignImage(campaign, fileName)));

		// 신청서질문 저장
		createCampaignDto.getApplicationQuestionDtoList()
				.forEach(applicationQuestionDto -> applicationQuestionRepository.save(applicationQuestionDto.createApplicationQuestion(campaign)));

		return campaignId;
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
	public Page<CampaignDto> getCampaignDtoPage(CampaignSearchRequirements searchRequirements) {
		return campaignRepository.findAllBySearchRequirements(searchRequirements).map(CampaignDto::new);
	}

	/** 캠페인 수정 */
	@Override
	public void updateCampaign(Long campaignId, UpdateCampaignDto updateCampaignDto) {

		// 캠페인 수정
		Campaign campaign = campaignRepository.findById(campaignId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인입니다. " + campaignId));

		// 상태가 대기 일때만 수정 가능
		if(!campaign.getStatus().equals(CampaignStatus.STAND)) {
			throw new IllegalArgumentException("대기상태에서만 캠페인 수정이 가능합니다.");
		}

		CampaignCategory campaignCategory = campaignCategoryRepository.findById(updateCampaignDto.getCategoryId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다. " + updateCampaignDto.getCategoryId()));
		CampaignType campaignType = campaignTypeRepository.findById(updateCampaignDto.getTypeId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 타입입니다. " + updateCampaignDto.getTypeId()));
		Sns sns = snsRepository.findById(updateCampaignDto.getSnsId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 SNS입니다. " + updateCampaignDto.getSnsId()));

		updateCampaignDto.setFileName(imageUtil.store(updateCampaignDto.getUploadFile(), imageFolder));// 이미지 파일 저장

		updateCampaignDto.updateCampaign(campaign, campaignCategory, campaignType, sns);

		// 캠페인지역 수정
		if (updateCampaignDto.getLocalId() != null) {
			Local local = localRepository.findById(updateCampaignDto.getLocalId())
					.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역입니다. " + updateCampaignDto.getLocalId()));
			//캠페인지역 insert
			if (campaign.getCampaignLocal() == null) {
				campaignLocalRepository.save(updateCampaignDto.getCampaignLocalDto().createCampaignLocal(campaign, local));
			//캠페인지역 update
			} else {
				CampaignLocal campaignLocal = campaign.getCampaignLocal();
				updateCampaignDto.getCampaignLocalDto().updateCampaignLocal(campaignLocal, local);
			}
		}

		// 캠페인이미지 수정
		//캠페인이미지 insert
		imageUtil.store(updateCampaignDto.getUploadFileList(), imageFolder)
				.forEach(fileName -> campaignImageRepository.save(new CampaignImage(campaign, fileName)));
		//캠페인이미지 delete
		if (!CollectionUtils.isEmpty(campaign.getCampaignImageList())) {
			campaign.getCampaignImageList().stream().filter(campaignImage -> {
				for (Long campaignImageId : updateCampaignDto.getCampaignImageId()) {
					if (campaignImage.getId().equals(campaignImageId)) return false;
				}
				return true;
			}).forEach(campaignImageId -> campaignImageRepository.deleteById(campaignId));
		}

		// 신청서질문 수정
		//신청서질문 insert
		if (!CollectionUtils.isEmpty(updateCampaignDto.getApplicationQuestionDtoList())) {
			updateCampaignDto.getApplicationQuestionDtoList().stream().filter(applicationQuestionDto -> applicationQuestionDto.getId() == 0)
					.forEach(applicationQuestionDto -> applicationQuestionRepository.save(applicationQuestionDto.createApplicationQuestion(campaign)));
		}

		if (!CollectionUtils.isEmpty(campaign.getApplicationQuestionList())) {
			for (ApplicationQuestion applicationQuestion : campaign.getApplicationQuestionList()) {
				boolean result = false;
				if (!CollectionUtils.isEmpty(updateCampaignDto.getApplicationQuestionDtoList())) {
					for (ApplicationQuestionDto applicationQuestionDto : updateCampaignDto.getApplicationQuestionDtoList()) {
						//신청서질문 update
						if (applicationQuestionDto.getId().equals(applicationQuestion.getId())) {
							applicationQuestionDto.updateApplicationQuestion(applicationQuestion);
							result = true;
							break;
						}
					}
				}
				//신청서질문 delete
				if (!result) {
					applicationQuestionRepository.delete(applicationQuestion);
				}
			}
		}

	}

	/** 캠페인 상태 변경 */
	@Override
	public void updateStatus(Long campaignId, CampaignStatus status) {
		campaignRepository.findById(campaignId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인입니다. " + campaignId))
				.updateCampaignStatus(status);
	}

}
