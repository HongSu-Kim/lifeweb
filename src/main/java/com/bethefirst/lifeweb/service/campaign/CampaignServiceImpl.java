package com.bethefirst.lifeweb.service.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignDto;
import com.bethefirst.lifeweb.dto.campaign.CreateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.SearchRequirements;
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

	// 캠페인 생성
	public void createCampaign(CreateCampaignDto createCampaignDto) throws IOException {

		CampaignCategory campaignCategory = campaignCategoryRepository.findByName(createCampaignDto.getCategoryName()).orElse(null);
		CampaignType campaignType = campaignTypeRepository.findByName(createCampaignDto.getTypeName()).orElse(null);
		Campaign campaign = new Campaign(campaignCategory, campaignType, createCampaignDto);

		// 캠페인 저장
		campaignRepository.save(campaign);

		// 캠페인지역 저장
		if (createCampaignDto.getLocalName() != null) {
			Local local = localRepository.findByName(createCampaignDto.getLocalName()).orElse(null);
			CampaignLocal campaignLocal = new CampaignLocal(campaign, local, createCampaignDto);

			campaignLocalRepository.save(campaignLocal);
		}
		
		// 캠페인이미지 저장
		// 이미지 파일 저장
		List<String> fileNameList = ImageUtil.store(createCampaignDto.getUploadFile(), "campaign");
		// DB에 이미지이름 저장
		fileNameList.forEach(fileName -> campaignImageRepository.save(new CampaignImage(campaign, fileName)));

		// 캠페인SNS 저장
		List<Sns> snsList = snsRepository.findAllByNameIn(createCampaignDto.getSnsName());
		snsList.forEach(sns -> campaignSnsRepository.save(new CampaignSns(campaign, sns)));
		
	}
	
	// 캠페인 조회
	public CampaignDto getCampaignDto(Long campaignId) {
		return new CampaignDto(campaignRepository.findById(campaignId).orElse(null));
	}

	// 캠페인 리스트 조회
	public Page<CampaignDto> campaignDtoList(SearchRequirements searchRequirements) {
		return campaignRepository.findAllBySearchRequirements(searchRequirements).map(CampaignDto::new);
	}
	
	// 캠페인 수정
	public void updateCampaign() {

	}

}
