package com.bethefirst.lifeweb.initDto.campaign;

import com.bethefirst.lifeweb.dto.campaign.request.CampaignSearchRequirements;
import com.bethefirst.lifeweb.dto.campaign.request.CreateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.request.UpdateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.request.UpdateCampaignPickDto;
import com.bethefirst.lifeweb.dto.campaign.response.CampaignDto;
import com.bethefirst.lifeweb.dto.campaign.response.CampaignLocalDto;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import com.bethefirst.lifeweb.entity.application.QuestionType;
import com.bethefirst.lifeweb.initDto.InitMockMultipartFile;
import com.bethefirst.lifeweb.initDto.mamber.InitSnsDto;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitCampaignDto {

	InitCampaignCategoryDto campaignCategoryDto = new InitCampaignCategoryDto();
	InitCampaignTypeDto campaignTypeDto = new InitCampaignTypeDto();
	InitSnsDto snsDto = new InitSnsDto();
	InitMockMultipartFile mockMultipartFile = new InitMockMultipartFile();

	public CreateCampaignDto getCreateCampaignDto() {
		return new CreateCampaignDto(1L, 1L, 1L,
				true, "title", mockMultipartFile.getMockMultipartFile(), null,
				"provision", "reviewNotice", "guideline", "keyword1#keyword2#keyword3",
				LocalDate.now().toString().substring(0, 10), LocalDate.now().plusDays(7).toString().substring(0, 10),
				LocalDate.now().plusDays(8).toString().substring(0, 10), LocalDate.now().plusDays(14).toString().substring(0, 10),
				15, 1L, "address", "37.4954841", "127.0333574", "visitNotice",
				mockMultipartFile.getMultipartFileList(), Arrays.asList("테스트 질문1", "테스트 질문2"), Arrays.asList(QuestionType.TEXT, QuestionType.CHECKBOX), Arrays.asList("", "111,222,333"));
	}

	public CampaignDto getCampaignDto() {
		return getCampaignDtoList().get(0);
	}

	public CampaignSearchRequirements getSearchRequirements() {
		return CampaignSearchRequirements.builder()
				.pageable(pageable)
				.build();
	}
	public Page<CampaignDto> getCampaignDtoPage() {
		return new PageImpl(getSubList(), pageable, getCampaignDtoList().size());
	}

	private List<CampaignDto> getSubList() {
		if (getCampaignDtoList().size() < getPage() * getSize()) {
			return new ArrayList<>();
		} else if (getCampaignDtoList().size() - getPage() * getSize() < getSize()) {
			return getCampaignDtoList().subList(getPage() * getSize(), getCampaignDtoList().size());
		} else {
			return getCampaignDtoList().subList(getPage() * getSize(), getSize());
		}
	}

	public List<CampaignDto> getCampaignDtoList() {

		List<CampaignDto> list = new ArrayList<>();

		for (int i = 0; i < 15; i++) {
			list.add(new CampaignDto((long) i, campaignCategoryDto.getCampaignCategoryDto(), campaignTypeDto.getCampaignTypeDto(), snsDto.getSnsDto(),
					true, false, "캠페인 제목", "대표이미지.jpg", "제공내역",
					LocalDateTime.now(), "리뷰주의사항", "가이드라인", "keyword1#keyword2#keyword3",
					LocalDate.now(), LocalDate.now().plusDays(7),
					LocalDate.now().plusDays(8), LocalDate.now().plusDays(14),
					15, CampaignStatus.STAND,
					campaignLocalDto, imageList));
		}

		return list;
	}

	public UpdateCampaignDto getUpdateCampaignDto() {
		return new UpdateCampaignDto(1L, 1L, 1L,
				true, "title", mockMultipartFile.getMockMultipartFile(), null,
				"provision", "reviewNotice", "guideline", "keyword1#keyword2#keyword3",
				LocalDate.now().toString().substring(0, 10), LocalDate.now().plusDays(7).toString().substring(0, 10),
				LocalDate.now().plusDays(8).toString().substring(0, 10), LocalDate.now().plusDays(14).toString().substring(0, 10),
				15, 1L, "address", "37.4954841", "127.0333574", "visitNotice",
				Arrays.asList(1L, 2L), mockMultipartFile.getMultipartFileList(),
				Arrays.asList(1L, 2L), Arrays.asList("테스트 질문1", "테스트 질문2"), Arrays.asList(QuestionType.TEXT, QuestionType.CHECKBOX), Arrays.asList(null, "111,222,333"));
	}

	public UpdateCampaignPickDto getUpdatePickDto() {
		return new UpdateCampaignPickDto(Arrays.asList(1L, 2L), Arrays.asList(3L, 4L));
	}

	private List<String> imageList = Arrays.asList("이미지1.jpg", "이미지2.jpg", "이미지3.jpg");

	private CampaignLocalDto campaignLocalDto = new CampaignLocalDto(1L, "서울", "서울특별시 강남구 역삼1동",
				"37.4954841", "127.0333574", "방문주의사항");

	private Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "created"));

	private int getPage() {
		return pageable.getPageNumber();
	}

	private int getSize() {
		return pageable.getPageSize();
	}

}
