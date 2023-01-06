package com.bethefirst.lifeweb.dto.campaign;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.campaign.CampaignImage;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CampaignDto {

	private Long id;//캠페인ID

	private String categoryName;//카테고리명
	private String typeName;//타입이름

	private Boolean special;//스페셜
	private String title;//제목
	private String fileName;//대표이미지
	private String provision;//제공내역
	private LocalDateTime created;//등록일
	private String reviewNotice;//리뷰주의사항
	private String guideline;//가이드라인
	private LocalDate applicationStartDate;//신청시작일
	private LocalDate applicationEndDate;//신청종료일
	private LocalDate filingStartDate;//등록시작일
	private LocalDate filingEndDate;//등록종료일
	private String keywords;//키워드
	private CampaignStatus status;//진행상태

	private CampaignLocalDto campaignLocalDto;//캠페인지역
	private List<String> imageList;//이미지
	private List<String> snsList;//SNS

	public CampaignDto(Campaign campaign) {

		id = campaign.getId();

		categoryName = campaign.getCampaignCategory().getName();
		typeName = campaign.getCampaignType().getName();

		special = campaign.getSpecial();
		title = campaign.getTitle();
		fileName = campaign.getFileName();
		provision = campaign.getProvision();
		created = campaign.getCreated();
		reviewNotice = campaign.getReviewNotice();
		guideline = campaign.getGuideline();
		applicationStartDate = campaign.getApplicationStartDate();
		applicationEndDate = campaign.getApplicationEndDate();
		filingStartDate = campaign.getFilingStartDate();
		filingEndDate = campaign.getFilingEndDate();
		keywords = campaign.getKeywords();
		status = campaign.getStatus();

		campaignLocalDto = campaign.getCampaignLocal() == null ? null : new CampaignLocalDto(campaign.getCampaignLocal());
		imageList = campaign.getCampaignImageList()
				.stream().map(CampaignImage::getFileName)
				.collect(Collectors.toList());
		snsList = campaign.getCampaignSnsList()
				.stream().map(campaignChannel -> campaignChannel.getSns().getName())
				.collect(Collectors.toList());

	}

}
