package com.bethefirst.lifeweb.entity.campaign;

import com.bethefirst.lifeweb.dto.campaign.CreateCampaignDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Campaign {//캠페인

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_id")
	private Long id;//캠페인ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_category_id")
	private CampaignCategory campaignCategory;//캠페인카테고리 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_type_id")
	private CampaignType campaignType;//캠페인타입 FK

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

	@Enumerated(EnumType.STRING)
	private CampaignStatus status;//상태

	@OneToOne(mappedBy = "campaign")
	private CampaignLocal campaignLocal;//캠페인지역

	@OneToMany(mappedBy = "campaign")
	private List<CampaignImage> campaignImageList = new ArrayList<>();//캠페인이미지

	@OneToMany(mappedBy = "campaign")
	private List<CampaignSns> campaignSnsList = new ArrayList<>();//캠페인채널

	@OneToMany(mappedBy = "campaign")
	private List<ApplicationQuestion> applicationQuestionList = new ArrayList<>();//신청서질문

	public Campaign(CampaignCategory campaignCategory, CampaignType campaignType, CreateCampaignDto createCampaignDto) {

		this.campaignCategory = campaignCategory;
		this.campaignType = campaignType;

		this.special = createCampaignDto.getSpecial();
		this.title = createCampaignDto.getTitle();
		this.fileName = createCampaignDto.getFileName();
		this.provision = createCampaignDto.getProvision();
		this.created = LocalDateTime.now();
		this.reviewNotice = createCampaignDto.getReviewNotice();
		this.guideline = createCampaignDto.getGuideline();
		this.applicationStartDate = createCampaignDto.getApplicationStartDate();
		this.applicationEndDate = createCampaignDto.getApplicationEndDate();
		this.filingStartDate = createCampaignDto.getFilingStartDate();
		this.filingEndDate = createCampaignDto.getFilingEndDate();
		this.keywords = createCampaignDto.getKeywords();
		this.status = CampaignStatus.STAND;

	}

}
