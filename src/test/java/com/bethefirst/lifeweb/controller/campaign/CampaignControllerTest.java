package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.ControllerTest;
import com.bethefirst.lifeweb.CustomRestDocumentationRequestBuilders;
import com.bethefirst.lifeweb.dto.campaign.request.CreateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.request.UpdateCampaignDto;
import com.bethefirst.lifeweb.dto.campaign.request.UpdateCampaignPickDto;
import com.bethefirst.lifeweb.entity.application.ApplicantStatus;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import com.bethefirst.lifeweb.entity.application.QuestionType;
import com.bethefirst.lifeweb.entity.member.Role;
import com.bethefirst.lifeweb.initDto.campaign.InitCampaignDto;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.mockito.BDDMockito.*;//given,willReturn
import static org.springframework.http.HttpHeaders.*;//AUTHORIZATION,LOCATION
import static org.springframework.http.MediaType.*;//MULTIPART_FORM_DATA
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;//get,post,multipart...
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;//status
import static org.springframework.restdocs.request.RequestDocumentation.*;//pathParameters,queryParameters,requestParts
import static org.springframework.restdocs.payload.PayloadDocumentation.*;//requestFields,responseFields
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static com.bethefirst.lifeweb.CustomJsonFieldType.*;

@WebMvcTest(CampaignController.class)
class CampaignControllerTest extends ControllerTest {

	@MockBean
	private CampaignService campaignService;

	private String urlTemplate = "/campaigns";
	private InitCampaignDto campaignDto = new InitCampaignDto();

	@Test
	void 캠페인_생성() throws Exception {

		CreateCampaignDto dto = campaignDto.getCreateCampaignDto();
		given(campaignService.createCampaign(dto)).willReturn(1L);

		mockMvc.perform(
				createMultiPartRequest(multipart(urlTemplate), dto)
						.contentType(MULTIPART_FORM_DATA)
						.header(AUTHORIZATION, getJwt(Role.ADMIN.name(), 1L))
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								requestHeaders(
										headerWithName(AUTHORIZATION).attributes(role(Role.ADMIN)).description("token")
								),
								requestParts(
										partWithName("categoryId").attributes(type(NUMBER)).description("카테고리ID"),
										partWithName("typeId").attributes(type(NUMBER)).description("타입ID"),
										partWithName("snsId").attributes(type(NUMBER)).description("SNSID"),
										partWithName("special").attributes(type(BOOLEAN)).description("스페셜"),
										partWithName("title").attributes(type(STRING)).description("제목"),
										partWithName("uploadFile").attributes(type(MULTIPART_FILE)).description("대표이미지 파일"),
										partWithName("provision").attributes(type(STRING)).description("제공내역"),
										partWithName("reviewNotice").attributes(type(STRING)).description("리뷰주의사항"),
										partWithName("guideline").attributes(type(STRING)).description("가이드라인"),
										partWithName("keywords").attributes(type(STRING)).description("키워드"),
										partWithName("applicationStartDate").attributes(type(LOCAL_DATE)).description("신청시작일"),
										partWithName("applicationEndDate").attributes(type(LOCAL_DATE)).description("신청종료일"),
										partWithName("filingStartDate").attributes(type(LOCAL_DATE)).description("등록시작일"),
										partWithName("filingEndDate").attributes(type(LOCAL_DATE)).description("등록종료일"),
										partWithName("headcount").attributes(type(NUMBER)).description("모집인원"),
										partWithName("localId").attributes(type(NUMBER)).description("지역ID").optional(),
										partWithName("address").attributes(type(STRING)).description("주소").optional(),
										partWithName("latitude").attributes(type(STRING)).description("위도").optional(),
										partWithName("longitude").attributes(type(STRING)).description("경도").optional(),
										partWithName("visitNotice").attributes(type(STRING)).description("방문주의사항").optional(),
										partWithName("uploadFileList").attributes(type(MULTIPART_FILE)).description("이미지").optional(),
										partWithName("question").attributes(type(STRING)).description("질문").optional(),
										partWithName("type").attributes(type(STRING)).description("유형").optional(),
										partWithName("items").attributes(type(STRING)).description("항목").optional()
								),
								responseHeaders(
										headerWithName(LOCATION).description(LOCATION)
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
										fieldWithPath("pick").type(BOOLEAN).description("픽"),
										fieldWithPath("title").type(STRING).description("제목"),
										fieldWithPath("fileName").type(STRING).description("대표이미지"),
										fieldWithPath("provision").type(STRING).description("제공내역"),
										fieldWithPath("created").type(LOCAL_DATE_TIME).description("등록일"),
										fieldWithPath("reviewNotice").type(STRING).description("리뷰주의사항"),
										fieldWithPath("guideline").type(STRING).description("가이드라인"),
										fieldWithPath("keywords").type(STRING).description("키워드"),
										fieldWithPath("applicationStartDate").type(LOCAL_DATE).description("신청시작일"),
										fieldWithPath("applicationEndDate").type(LOCAL_DATE).description("신청종료일"),
										fieldWithPath("filingStartDate").type(LOCAL_DATE).description("등록시작일"),
										fieldWithPath("filingEndDate").type(LOCAL_DATE).description("등록종료일"),
										fieldWithPath("headcount").type(NUMBER).description("모집인원"),
										fieldWithPath("status").type(CampaignStatus.class).description("진행상태"),
										fieldWithPath("campaignLocalDto.localId").type(NUMBER).description("지역ID").optional(),
										fieldWithPath("campaignLocalDto.localName").type(STRING).description("지역이름").optional(),
										fieldWithPath("campaignLocalDto.address").type(STRING).description("주소").optional(),
										fieldWithPath("campaignLocalDto.latitude").type(STRING).description("위도").optional(),
										fieldWithPath("campaignLocalDto.longitude").type(STRING).description("경도").optional(),
										fieldWithPath("campaignLocalDto.visitNotice").type(STRING).description("방문주의사항").optional(),
										fieldWithPath("imageList[]").type(ARRAY_STRING).description("이미지").optional()
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
						.param("sort", "created,desc")
				)
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								queryParameters(
										parameterWithName("page").attributes(type(NUMBER)).description("페이지").optional(),
										parameterWithName("size").attributes(type(NUMBER)).description("사이즈").optional(),
										parameterWithName("sort").attributes(type(STRING)).description("정렬기준,정렬방향").optional(),
										parameterWithName("categoryId").attributes(type(NUMBER)).description("카테고리ID").optional(),
										parameterWithName("typeId").attributes(type(NUMBER)).description("타입ID").optional(),
										parameterWithName("snsId").attributes(type(NUMBER)).description("SNSID").optional(),
										parameterWithName("special").attributes(type(BOOLEAN)).description("스페셜").optional(),
										parameterWithName("pick").attributes(type(BOOLEAN)).description("픽").optional(),
										parameterWithName("campaignStatus").attributes(type(CampaignStatus.class)).description("캠페인상태").optional(),
										parameterWithName("localId").attributes(type(NUMBER)).description("지역ID").optional(),
										parameterWithName("applicantStatus").attributes(type(ApplicantStatus.class)).description("신청자상태").optional(),
										parameterWithName("memberId").attributes(type(NUMBER)).description("회원ID").optional()
								),
								relaxedResponseFields(
										fieldWithPath("content").type(ARRAY).description("리스트"),
										fieldWithPath("content.[].id").type(NUMBER).description("캠페인ID"),
										fieldWithPath("content.[].campaignCategoryDto.id").type(NUMBER).description("카테고리ID"),
										fieldWithPath("content.[].campaignCategoryDto.name").type(STRING).description("카테고리이름"),
										fieldWithPath("content.[].campaignTypeDto.id").type(NUMBER).description("타입ID"),
										fieldWithPath("content.[].campaignTypeDto.name").type(STRING).description("타입이름"),
										fieldWithPath("content.[].snsDto.id").type(NUMBER).description("SNSID"),
										fieldWithPath("content.[].snsDto.name").type(STRING).description("SNS이름"),
										fieldWithPath("content.[].special").type(BOOLEAN).description("스페셜"),
										fieldWithPath("content.[].pick").type(BOOLEAN).description("픽"),
										fieldWithPath("content.[].title").type(STRING).description("제목"),
										fieldWithPath("content.[].fileName").type(STRING).description("대표이미지"),
										fieldWithPath("content.[].provision").type(STRING).description("제공내역"),
										fieldWithPath("content.[].created").type(LOCAL_DATE_TIME).description("등록일"),
										fieldWithPath("content.[].reviewNotice").type(STRING).description("리뷰주의사항"),
										fieldWithPath("content.[].guideline").type(STRING).description("가이드라인"),
										fieldWithPath("content.[].keywords").type(STRING).description("키워드"),
										fieldWithPath("content.[].applicationStartDate").type(LOCAL_DATE).description("신청시작일"),
										fieldWithPath("content.[].applicationEndDate").type(LOCAL_DATE).description("신청종료일"),
										fieldWithPath("content.[].filingStartDate").type(LOCAL_DATE).description("등록시작일"),
										fieldWithPath("content.[].filingEndDate").type(LOCAL_DATE).description("등록종료일"),
										fieldWithPath("content.[].headcount").type(NUMBER).description("모집인원"),
										fieldWithPath("content.[].status").type(CampaignStatus.class).description("진행상태"),
										fieldWithPath("content.[].campaignLocalDto.localId").type(NUMBER).description("지역ID").optional(),
										fieldWithPath("content.[].campaignLocalDto.localName").type(STRING).description("지역이름").optional(),
										fieldWithPath("content.[].campaignLocalDto.address").type(STRING).description("주소").optional(),
										fieldWithPath("content.[].campaignLocalDto.latitude").type(STRING).description("위도").optional(),
										fieldWithPath("content.[].campaignLocalDto.longitude").type(STRING).description("경도").optional(),
										fieldWithPath("content.[].campaignLocalDto.visitNotice").type(STRING).description("방문주의사항").optional(),
										fieldWithPath("content.[].imageList[]").type(ARRAY_STRING).description("이미지").optional(),
										fieldWithPath("pageable").type(Pageable.class).description("페이징"),
										fieldWithPath("last").type(BOOLEAN).description("마지막페이지"),
										fieldWithPath("totalPages").type(NUMBER).description("전체페이지"),
										fieldWithPath("totalElements").type(NUMBER).description("전체엘리먼트"),
										fieldWithPath("size").type(NUMBER).description("사이즈"),
										fieldWithPath("number").type(NUMBER).description("페이지"),
										fieldWithPath("sort").type(Sort.class).description("정렬"),
										fieldWithPath("first").type(BOOLEAN).description("첫페이지"),
										fieldWithPath("numberOfElements").type(NUMBER).description("엘리먼트"),
										fieldWithPath("empty").type(BOOLEAN).description("empty")
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
						.contentType(MULTIPART_FORM_DATA)
						.header(AUTHORIZATION, getJwt(Role.ADMIN.name(), 1L))
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
										partWithName("uploadFile").attributes(type(MULTIPART_FILE)).description("대표이미지 파일").optional(),
										partWithName("provision").attributes(type(STRING)).description("제공내역"),
										partWithName("reviewNotice").attributes(type(STRING)).description("리뷰주의사항"),
										partWithName("guideline").attributes(type(STRING)).description("가이드라인"),
										partWithName("keywords").attributes(type(STRING)).description("키워드"),
										partWithName("applicationStartDate").attributes(type(LOCAL_DATE)).description("신청시작일"),
										partWithName("applicationEndDate").attributes(type(LOCAL_DATE)).description("신청종료일"),
										partWithName("filingStartDate").attributes(type(LOCAL_DATE)).description("등록시작일"),
										partWithName("filingEndDate").attributes(type(LOCAL_DATE)).description("등록종료일"),
										partWithName("headcount").attributes(type(NUMBER)).description("모집인원"),
										partWithName("localId").attributes(type(NUMBER)).description("지역ID").optional(),
										partWithName("address").attributes(type(STRING)).description("주소").optional(),
										partWithName("latitude").attributes(type(STRING)).description("위도").optional(),
										partWithName("longitude").attributes(type(STRING)).description("경도").optional(),
										partWithName("visitNotice").attributes(type(STRING)).description("방문주의사항").optional(),
										partWithName("campaignImageId").attributes(type(NUMBER)).description("기존 이미지 ID").optional(),
										partWithName("uploadFileList").attributes(type(MULTIPART_FILE)).description("새로운 이미지 파일").optional(),
										partWithName("applicationQuestionId").attributes(type(NUMBER)).description("질문ID").optional(),
										partWithName("question").attributes(type(NUMBER)).description("질문").optional(),
										partWithName("type").attributes(type(QuestionType.class)).description("유형").optional(),
										partWithName("items").attributes(type(ARRAY)).description("항목").optional()
								),
								responseHeaders(
										headerWithName(LOCATION).description(LOCATION)
								)
						)
				);
	}

	@Test
	void 캠페인_상태_변경() throws Exception {

		willDoNothing().given(campaignService).updateStatus(1L, CampaignStatus.FILING);

		mockMvc.perform(put(urlTemplate + "/{campaignId}/status", 1L)
						.content(objectMapper.writeValueAsString(Map.of(
								"status", CampaignStatus.FILING.name()
						)))
						.contentType(MediaType.APPLICATION_JSON)
						.header(AUTHORIZATION, getJwt(Role.ADMIN.name(), 1L))
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("campaignId").attributes(type(NUMBER)).description("캠페인ID")
								),
								requestFields(
										fieldWithPath("status").type(CampaignStatus.class).description("캠페인상태")
								)
						)
				);
	}

	@Test
	void 캠페인_PICK_체크() throws Exception {

		UpdateCampaignPickDto dto = campaignDto.getUpdatePickDto();
		willDoNothing().given(campaignService).updatePick(dto);

		mockMvc.perform(put(urlTemplate + "/pick")
						.content(objectMapper.writeValueAsString(dto))
						.contentType(MediaType.APPLICATION_JSON)
						.header(AUTHORIZATION, getJwt(Role.ADMIN.name(), 1L))
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								requestFields(
										fieldWithPath("newCampaignId").type(ARRAY_NUMBER).description("pick 선택할 캠페인ID"),
										fieldWithPath("oldCampaignId").type(ARRAY_NUMBER).description("pick 해제할 캠페인ID")
								)
						)
				);
	}

}