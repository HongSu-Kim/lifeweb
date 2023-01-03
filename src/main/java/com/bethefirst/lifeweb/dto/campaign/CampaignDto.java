package com.bethefirst.lifeweb.dto.campaign;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.campaign.CampaignImage;
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
	private String localName;//지역이름
	private String typeName;//타입이름

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

	private List<String> snsList;//SNS
	private List<String> imageList;//이미지

	public CampaignDto(Campaign campaign) {

		id = campaign.getId();

		categoryName = campaign.getCampaignCategory().getName();
		localName = campaign.getLocal().getName();
		typeName = campaign.getCampaignType().getName();

		title = campaign.getTitle();
		fileName = campaign.getFileName();
//		field = campaign.getField();
		created = campaign.getCreated();
		reviewNotice = campaign.getReviewNotice();
		guideline = campaign.getGuideline();
		receiveStartDate = campaign.getReceiveStartDate();
		receiveEndDate = campaign.getReceiveEndDate();
//		StartDate = campaign.getStartDate();
//		EndDate = campaign.getEndDate();
		keywords = campaign.getKeywords();
		address = campaign.getAddress();
		latitude = campaign.getLatitude();
		longitude = campaign.getLongitude();


		snsList = campaign.getCampaignChannelList()
				.stream().map(campaignChannel -> campaignChannel.getSns().getName())
				.collect(Collectors.toList());
		imageList = campaign.getCampaignImageList()
				.stream().map(CampaignImage::getFileName)
				.collect(Collectors.toList());

	}

}
