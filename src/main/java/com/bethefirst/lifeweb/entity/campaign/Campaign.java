package com.bethefirst.lifeweb.entity.campaign;

import com.bethefirst.lifeweb.dto.campaign.CreateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.UpdateCampaignDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EntityListeners(AuditingEntityListener.class)
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

//	@CreatedDate
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

	@OneToOne(mappedBy = "campaign")
	private CampaignSns campaignSns;//캠페인SNS

	@OneToMany(mappedBy = "campaign")
	private List<CampaignImage> campaignImageList = new ArrayList<>();//캠페인이미지

	@OneToMany(mappedBy = "campaign")
	private List<ApplicationQuestion> applicationQuestionList = new ArrayList<>();//신청서질문

	private Campaign(CampaignCategory campaignCategory, CampaignType campaignType,
					 Boolean special, String title, String fileName, String provision,
					 String reviewNotice, String guideline,
					 LocalDate applicationStartDate, LocalDate applicationEndDate,
					 LocalDate filingStartDate, LocalDate filingEndDate, String keywords) {

		this.campaignCategory = campaignCategory;
		this.campaignType = campaignType;

		this.special = special;
		this.title = title;
		this.fileName = fileName;
		this.provision = provision;
		this.created = LocalDateTime.now();
		this.reviewNotice = reviewNotice;
		this.guideline = guideline;
		this.applicationStartDate = applicationStartDate;
		this.applicationEndDate = applicationEndDate;
		this.filingStartDate = filingStartDate;
		this.filingEndDate = filingEndDate;
		this.keywords = keywords;
		this.status = CampaignStatus.STAND;

	}

	/** 캠페인 생성 */
	public static Campaign createCampaign(CampaignCategory campaignCategory, CampaignType campaignType, CreateCampaignDto dto) {

		return new Campaign(campaignCategory, campaignType,
				dto.getSpecial(), dto.getTitle(), dto.getFileName(), dto.getProvision(),
				dto.getReviewNotice(), dto.getGuideline(),
				dto.getApplicationStartDate(), dto.getApplicationEndDate(),
				dto.getFilingStartDate(), dto.getFilingEndDate(), dto.getKeywords());

	}

	/** 캠페인 수정 */
	public void update(CampaignCategory campaignCategory, CampaignType campaignType, UpdateCampaignDto dto) {

		this.campaignCategory = campaignCategory;
		this.campaignType = campaignType;

		this.special = dto.getSpecial();
		this.title = dto.getTitle();
		this.fileName = dto.getFileName();
		this.provision = dto.getProvision();
		this.reviewNotice = dto.getReviewNotice();
		this.guideline = dto.getGuideline();
		this.applicationStartDate = dto.getApplicationStartDate();
		this.applicationEndDate = dto.getApplicationEndDate();
		this.filingStartDate = dto.getFilingStartDate();
		this.filingEndDate = dto.getFilingEndDate();
		this.keywords = dto.getKeywords();
		this.status = dto.getStatus();

	}

	/** 캠페인 상태 변경 */
	public void updateStatus(CampaignStatus status) {
		this.status = status;
	}

}
