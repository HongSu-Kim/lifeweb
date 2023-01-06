package com.bethefirst.lifeweb.dto.campaign;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateCampaignDto {

	private String categoryName;//카테고리명
	private String typeName;//타입이름

	private Boolean special;//스페셜
	private String title;//제목
	private String fileName;//대표이미지
	private String provision;//제공내역
	private String reviewNotice;//리뷰주의사항
	private String guideline;//가이드라인
	private LocalDate applicationStartDate;//신청시작일
	private LocalDate applicationEndDate;//신청종료일
	private LocalDate filingStartDate;//등록시작일
	private LocalDate filingEndDate;//등록종료일
	private String keywords;//키워드

	private String localName;//지역

	private String address;//주소
	private String latitude;//위도
	private String longitude;//경도
	private String visitNotice;//방문주의사항

	private List<MultipartFile> uploadFile;//이미지
	private List<String> snsName;//SNS
	private List<Integer> headcount;//SNS

}
