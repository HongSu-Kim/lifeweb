package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.ControllerTest;
import com.bethefirst.lifeweb.CustomRestDocumentationRequestBuilders;
import com.bethefirst.lifeweb.dto.campaign.CampaignDto;
import com.bethefirst.lifeweb.dto.campaign.CreateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.UpdateCampaignDto;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import com.bethefirst.lifeweb.entity.campaign.QuestionType;
import com.bethefirst.lifeweb.initDto.campaign.InitCampaignDto;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignService;
import com.querydsl.core.types.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.*;//given,willReturn
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;//get,post,multipart...
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;//status
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;//responseHeaders
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;//headerWithName
import static org.springframework.restdocs.request.RequestDocumentation.*;//pathParameters,queryParameters,requestParts
import static org.springframework.restdocs.payload.PayloadDocumentation.*;//requestFields,responseFields
import static org.springframework.restdocs.payload.JsonFieldType.*;

@WebMvcTest(CampaignController.class)
class CampaignControllerTest extends ControllerTest {

	@MockBean
	CampaignService campaignService;

	String urlTemplate = "/campaigns";
	InitCampaignDto campaignDto = new InitCampaignDto();

	@Test
	void 캠페인_생성() throws Exception {

		CreateCampaignDto dto = campaignDto.getCreateCampaignDto();
		given(campaignService.createCampaign(dto)).willReturn(0L);

		mockMvc.perform(
				createMultiPartRequest(multipart(urlTemplate), dto)
						.contentType(MediaType.MULTIPART_FORM_DATA)
//						.header(HttpHeaders.AUTHORIZATION, )
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								requestParts(
										partWithName("categoryId").attributes(type(NUMBER)).description("카테고리ID"),
										partWithName("typeId").attributes(type(NUMBER)).description("타입ID"),
										partWithName("snsId").attributes(type(NUMBER)).description("SNSID"),
										partWithName("special").attributes(type(BOOLEAN)).description("스페셜"),
										partWithName("title").attributes(type(STRING)).description("제목"),
										partWithName("uploadFile").attributes(type(MultipartFile.class)).description("대표이미지 파일"),
										partWithName("provision").attributes(type(STRING)).description("제공내역"),
										partWithName("reviewNotice").attributes(type(STRING)).description("리뷰주의사항"),
										partWithName("guideline").attributes(type(STRING)).description("가이드라인"),
										partWithName("keywords").attributes(type(STRING)).description("키워드"),
										partWithName("applicationStartDate").attributes(type(LocalDate.class)).description("신청시작일"),
										partWithName("applicationEndDate").attributes(type(LocalDate.class)).description("신청종료일"),
										partWithName("filingStartDate").attributes(type(LocalDate.class)).description("등록시작일"),
										partWithName("filingEndDate").attributes(type(LocalDate.class)).description("등록종료일"),
										partWithName("headcount").attributes(type(NUMBER)).description("모집인원"),
										partWithName("localId").attributes(type(NUMBER)).description("지역ID").optional(),
										partWithName("address").attributes(type(STRING)).description("주소").optional(),
										partWithName("latitude").attributes(type(STRING)).description("위도").optional(),
										partWithName("longitude").attributes(type(STRING)).description("경도").optional(),
										partWithName("visitNotice").attributes(type(STRING)).description("방문주의사항").optional(),
										partWithName("uploadFileList").attributes(type(MultipartFile.class)).description("이미지").optional(),
										partWithName("question").attributes(type(STRING)).description("질문").optional(),
										partWithName("type").attributes(type(STRING)).description("유형").optional(),
										partWithName("items").attributes(type(STRING)).description("항목").optional()
								),
								responseHeaders(
										headerWithName(HttpHeaders.LOCATION).description("Location")
								)
						)
				);
	}

	@Test
	void 캠페인_조회() throws Exception {

		given(campaignService.getCampaignDto(1L)).willReturn(campaignDto.getCampaignDto());

		mockMvc.perform(get(urlTemplate + "/{campaignId}", 1L))
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("campaignId").attributes(type(NUMBER)).description("캠페인ID")
								),
								responseFields(
										fieldWithPath("id").type(NUMBER).description("캠페인ID"),
										fieldWithPath("campaignCategoryDto.id").type(NUMBER).description("카테고리ID"),
										fieldWithPath("campaignCategoryDto.name").type(STRING).description("카테고리이름"),
										fieldWithPath("campaignTypeDto.id").type(NUMBER).description("타입ID"),
										fieldWithPath("campaignTypeDto.name").type(STRING).description("타입이름"),
										fieldWithPath("snsDto.id").type(NUMBER).description("SNSID"),
										fieldWithPath("snsDto.name").type(STRING).description("SNS이름"),
										fieldWithPath("special").type(BOOLEAN).description("스페셜"),
										fieldWithPath("title").type(STRING).description("제목"),
										fieldWithPath("fileName").type(STRING).description("대표이미지"),
										fieldWithPath("provision").type(STRING).description("제공내역"),
										fieldWithPath("created").type(LocalDateTime.class).description("등록일"),
										fieldWithPath("reviewNotice").type(STRING).description("리뷰주의사항"),
										fieldWithPath("guideline").type(STRING).description("가이드라인"),
										fieldWithPath("keywords").type(STRING).description("키워드"),
										fieldWithPath("applicationStartDate").type(LocalDate.class).description("신청시작일"),
										fieldWithPath("applicationEndDate").type(LocalDate.class).description("신청종료일"),
										fieldWithPath("filingStartDate").type(LocalDate.class).description("등록시작일"),
										fieldWithPath("filingEndDate").type(LocalDate.class).description("등록종료일"),
										fieldWithPath("headcount").type(NUMBER).description("모집인원"),
										fieldWithPath("status").type(CampaignStatus.class).description("진행상태"),
										fieldWithPath("campaignLocalDto.localId").type(NUMBER).description("지역ID").optional(),
										fieldWithPath("campaignLocalDto.localName").type(STRING).description("지역이름").optional(),
										fieldWithPath("campaignLocalDto.address").type(STRING).description("주소").optional(),
										fieldWithPath("campaignLocalDto.latitude").type(STRING).description("위도").optional(),
										fieldWithPath("campaignLocalDto.longitude").type(STRING).description("경도").optional(),
										fieldWithPath("campaignLocalDto.visitNotice").type(STRING).description("방문주의사항").optional(),
										fieldWithPath("imageList[]").type(ARRAY).description("이미지").optional(),
										fieldWithPath("applicationQuestionDtoList[].id").type(NUMBER).description("질문ID").optional(),
										fieldWithPath("applicationQuestionDtoList[].question").type(STRING).description("질문").optional(),
										fieldWithPath("applicationQuestionDtoList[].type").type(QuestionType.class).description("유형").optional(),
										fieldWithPath("applicationQuestionDtoList[].items").type(STRING).description("항목").optional()
								)
						)
				);
	}

	@Test
	void 캠페인_리스트_조회() throws Exception {

		given(campaignService.getCampaignDtoPage(campaignDto.getSearchRequirements())).willReturn(campaignDto.getCampaignDtoPage());

		mockMvc.perform(get(urlTemplate)
						.param("page", "1")
						.param("size", "10")
						.param("sort", "id")
						.param("direction", "DESC")
				)
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								queryParameters(
										parameterWithName("page").attributes(type(NUMBER)).description("페이지"),
										parameterWithName("size").attributes(type(NUMBER)).description("사이즈"),
										parameterWithName("sort").attributes(type(STRING)).description("정렬기준").optional(),
										parameterWithName("direction").attributes(type(Order.class)).description("정렬방향").optional()
//								),
//								relaxedResponseFields(
//										fieldWithPath("content").type(ARRAY).description("리스트")
//										fieldWithPath("content.[].id").type(NUMBER).description("캠페인ID").attributes(field("constraints", "길이 10 이하"))
//										fieldWithPath("categoryName").type(String.class).description("카테고리이름"),
//										fieldWithPath("typeName").type(String.class).description("타입이름"),
//										fieldWithPath("snsName").type(String.class).description("SNS"),
//										fieldWithPath("special").type(Boolean.class).description("스페셜"),
//										fieldWithPath("title").type(String.class).description("제목"),
//										fieldWithPath("fileName").type(String.class).description("대표이미지"),
//										fieldWithPath("provision").type(String.class).description("제공내역"),
//										fieldWithPath("created").type(LocalDateTime.class).description("등록일"),
//										fieldWithPath("reviewNotice").type(String.class).description("리뷰주의사항"),
//										fieldWithPath("guideline").type(String.class).description("가이드라인"),
//										fieldWithPath("keywords").type(List.class).description("키워드"),
//										fieldWithPath("applicationStartDate").type(LocalDate.class).description("신청시작일"),
//										fieldWithPath("applicationEndDate").type(LocalDate.class).description("신청종료일"),
//										fieldWithPath("filingStartDate").type(LocalDate.class).description("등록시작일"),
//										fieldWithPath("filingEndDate").type(LocalDate.class).description("등록종료일"),
//										fieldWithPath("headcount").type(Integer.class).description("모집인원"),
//										fieldWithPath("status").type(CampaignStatus.class).description("진행상태"),
//										fieldWithPath("campaignLocalDto.localId").type(Long.class).description("지역ID").optional(),
//										fieldWithPath("campaignLocalDto.localName").type(String.class).description("지역이름").optional(),
//										fieldWithPath("campaignLocalDto.address").type(String.class).description("주소").optional(),
//										fieldWithPath("campaignLocalDto.latitude").type(String.class).description("위도").optional(),
//										fieldWithPath("campaignLocalDto.longitude").type(String.class).description("경도").optional(),
//										fieldWithPath("campaignLocalDto.visitNotice").type(String.class).description("방문주의사항").optional(),
//										fieldWithPath("imageList").type(String.class).description("이미지").optional(),
//										fieldWithPath("applicationQuestionDtoList[].id").type(Long.class).description("질문ID").optional(),
//										fieldWithPath("applicationQuestionDtoList[].question").type(String.class).description("질문").optional(),
//										fieldWithPath("applicationQuestionDtoList[].type").type(QuestionType.class).description("유형").optional(),
//										fieldWithPath("applicationQuestionDtoList[].items").type(List.class).description("항목").optional()
								)
						)
				);
	}

	@Test
	void 캠페인_수정() throws Exception {

		UpdateCampaignDto dto = campaignDto.getUpdateCampaignDto();
		willDoNothing().given(campaignService).updateCampaign(1L, dto);

		mockMvc.perform(
				createMultiPartRequest(CustomRestDocumentationRequestBuilders.multipart(HttpMethod.PUT, urlTemplate + "/{campaignId}", 1L), dto)
//						.header(HttpHeaders.AUTHORIZATION, )
						.contentType(MediaType.MULTIPART_FORM_DATA)
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								pathParameters(
									parameterWithName("campaignId").attributes(type(NUMBER)).description("캠페인ID")
								),
								requestParts(
										partWithName("categoryId").attributes(type(NUMBER)).description("카테고리ID"),
										partWithName("typeId").attributes(type(NUMBER)).description("타입ID"),
										partWithName("snsId").attributes(type(NUMBER)).description("SNSID"),
										partWithName("special").attributes(type(BOOLEAN)).description("스페셜"),
										partWithName("title").attributes(type(STRING)).description("제목"),
										partWithName("uploadFile").attributes(type(MultipartFile.class)).description("대표이미지 파일").optional(),
										partWithName("provision").attributes(type(STRING)).description("제공내역"),
										partWithName("reviewNotice").attributes(type(STRING)).description("리뷰주의사항"),
										partWithName("guideline").attributes(type(STRING)).description("가이드라인"),
										partWithName("keywords").attributes(type(STRING)).description("키워드"),
										partWithName("applicationStartDate").attributes(type(LocalDate.class)).description("신청시작일"),
										partWithName("applicationEndDate").attributes(type(LocalDate.class)).description("신청종료일"),
										partWithName("filingStartDate").attributes(type(LocalDate.class)).description("등록시작일"),
										partWithName("filingEndDate").attributes(type(LocalDate.class)).description("등록종료일"),
										partWithName("headcount").attributes(type(NUMBER)).description("모집인원"),
										partWithName("localId").attributes(type(NUMBER)).description("지역ID").optional(),
										partWithName("address").attributes(type(STRING)).description("주소").optional(),
										partWithName("latitude").attributes(type(STRING)).description("위도").optional(),
										partWithName("longitude").attributes(type(STRING)).description("경도").optional(),
										partWithName("visitNotice").attributes(type(STRING)).description("방문주의사항").optional(),
										partWithName("campaignImageId").attributes(type(NUMBER)).description("기존 이미지 ID").optional(),
										partWithName("uploadFileList").attributes(type(MultipartFile.class)).description("새로운 이미지 파일").optional(),
										partWithName("applicationQuestionId").attributes(type(NUMBER)).description("질문ID").optional(),
										partWithName("question").attributes(type(NUMBER)).description("질문").optional(),
										partWithName("type").attributes(type(QuestionType.class)).description("유형").optional(),
										partWithName("items").attributes(type(ARRAY)).description("항목").optional()
								),
								responseHeaders(
										headerWithName(HttpHeaders.LOCATION).description("Location")
								)
						)
				);
	}

}