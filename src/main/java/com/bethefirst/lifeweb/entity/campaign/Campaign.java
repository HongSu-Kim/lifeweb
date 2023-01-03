package com.bethefirst.lifeweb.entity.campaign;

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
	private CampaignCategory campaignCategory;//캠페인카테고리ID FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "local_id")
	private Local local;//지역ID FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_type_id")
	private CampaignType campaignType;//캠페인타입ID FK

	private String title;//제목
	private String fileName;//대표이미지
	private String field;//제공내역 ---
	private LocalDateTime created;//등록일
	private String reviewNotice;//리뷰주의사항
	private String guideline;//가이드라인
	private LocalDate receiveStartDate;//신청시작일
	private LocalDate receiveEndDate;//신청종료일
	private LocalDate StartDate;//등록시작일 ---
	private LocalDate EndDate;//등록종료일 ---
	private String keywords;//키워드
	private String address;//주소
	private String latitude;//위도
	private String longitude;//경도
	private String visitNotice;//방문주의사항

	@OneToMany(mappedBy = "campaign")
	private List<CampaignSns> campaignChannelList = new ArrayList<>();//캠페인채널

	@OneToMany(mappedBy = "campaign")
	private List<CampaignImage> campaignImageList = new ArrayList<>();//캠페인이미지

}
