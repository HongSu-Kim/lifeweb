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
	void ?????????_??????() throws Exception {

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
										partWithName("categoryId").attributes(type(NUMBER)).description("????????????ID"),
										partWithName("typeId").attributes(type(NUMBER)).description("??????ID"),
										partWithName("snsId").attributes(type(NUMBER)).description("SNSID"),
										partWithName("special").attributes(type(BOOLEAN)).description("?????????"),
										partWithName("title").attributes(type(STRING)).description("??????"),
										partWithName("uploadFile").attributes(type(MULTIPART_FILE)).description("??????????????? ??????"),
										partWithName("provision").attributes(type(STRING)).description("????????????"),
										partWithName("reviewNotice").attributes(type(STRING)).description("??????????????????"),
										partWithName("guideline").attributes(type(STRING)).description("???????????????"),
										partWithName("keywords").attributes(type(STRING)).description("?????????"),
										partWithName("applicationStartDate").attributes(type(LOCAL_DATE)).description("???????????????"),
										partWithName("applicationEndDate").attributes(type(LOCAL_DATE)).description("???????????????"),
										partWithName("filingStartDate").attributes(type(LOCAL_DATE)).description("???????????????"),
										partWithName("filingEndDate").attributes(type(LOCAL_DATE)).description("???????????????"),
										partWithName("headcount").attributes(type(NUMBER)).description("????????????"),
										partWithName("localId").attributes(type(NUMBER)).description("??????ID").optional(),
										partWithName("address").attributes(type(STRING)).description("??????").optional(),
										partWithName("latitude").attributes(type(STRING)).description("??????").optional(),
										partWithName("longitude").attributes(type(STRING)).description("??????").optional(),
										partWithName("visitNotice").attributes(type(STRING)).description("??????????????????").optional(),
										partWithName("uploadFileList").attributes(type(MULTIPART_FILE)).description("?????????").optional(),
										partWithName("question").attributes(type(STRING)).description("??????").optional(),
										partWithName("type").attributes(type(STRING)).description("??????").optional(),
										partWithName("items").attributes(type(STRING)).description("??????").optional()
								),
								responseHeaders(
										headerWithName(LOCATION).description(LOCATION)
								)
						)
				);
	}

	@Test
	void ?????????_??????() throws Exception {

		given(campaignService.getCampaignDto(1L)).willReturn(campaignDto.getCampaignDto());

		mockMvc.perform(get(urlTemplate + "/{campaignId}", 1L))
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("campaignId").attributes(type(NUMBER)).description("?????????ID")
								),
								responseFields(
										fieldWithPath("id").type(NUMBER).description("?????????ID"),
										fieldWithPath("campaignCategoryDto.id").type(NUMBER).description("????????????ID"),
										fieldWithPath("campaignCategoryDto.name").type(STRING).description("??????????????????"),
										fieldWithPath("campaignTypeDto.id").type(NUMBER).description("??????ID"),
										fieldWithPath("campaignTypeDto.name").type(STRING).description("????????????"),
										fieldWithPath("snsDto.id").type(NUMBER).description("SNSID"),
										fieldWithPath("snsDto.name").type(STRING).description("SNS??????"),
										fieldWithPath("special").type(BOOLEAN).description("?????????"),
										fieldWithPath("pick").type(BOOLEAN).description("???"),
										fieldWithPath("title").type(STRING).description("??????"),
										fieldWithPath("fileName").type(STRING).description("???????????????"),
										fieldWithPath("provision").type(STRING).description("????????????"),
										fieldWithPath("created").type(LOCAL_DATE_TIME).description("?????????"),
										fieldWithPath("reviewNotice").type(STRING).description("??????????????????"),
										fieldWithPath("guideline").type(STRING).description("???????????????"),
										fieldWithPath("keywords").type(STRING).description("?????????"),
										fieldWithPath("applicationStartDate").type(LOCAL_DATE).description("???????????????"),
										fieldWithPath("applicationEndDate").type(LOCAL_DATE).description("???????????????"),
										fieldWithPath("filingStartDate").type(LOCAL_DATE).description("???????????????"),
										fieldWithPath("filingEndDate").type(LOCAL_DATE).description("???????????????"),
										fieldWithPath("headcount").type(NUMBER).description("????????????"),
										fieldWithPath("status").type(CampaignStatus.class).description("????????????"),
										fieldWithPath("campaignLocalDto.localId").type(NUMBER).description("??????ID").optional(),
										fieldWithPath("campaignLocalDto.localName").type(STRING).description("????????????").optional(),
										fieldWithPath("campaignLocalDto.address").type(STRING).description("??????").optional(),
										fieldWithPath("campaignLocalDto.latitude").type(STRING).description("??????").optional(),
										fieldWithPath("campaignLocalDto.longitude").type(STRING).description("??????").optional(),
										fieldWithPath("campaignLocalDto.visitNotice").type(STRING).description("??????????????????").optional(),
										fieldWithPath("imageList[]").type(ARRAY_STRING).description("?????????").optional()
								)
						)
				);
	}

	@Test
	void ?????????_?????????_??????() throws Exception {

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
										parameterWithName("page").attributes(type(NUMBER)).description("?????????").optional(),
										parameterWithName("size").attributes(type(NUMBER)).description("?????????").optional(),
										parameterWithName("sort").attributes(type(STRING)).description("????????????,????????????").optional(),
										parameterWithName("categoryId").attributes(type(NUMBER)).description("????????????ID").optional(),
										parameterWithName("typeId").attributes(type(NUMBER)).description("??????ID").optional(),
										parameterWithName("snsId").attributes(type(NUMBER)).description("SNSID").optional(),
										parameterWithName("special").attributes(type(BOOLEAN)).description("?????????").optional(),
										parameterWithName("pick").attributes(type(BOOLEAN)).description("???").optional(),
										parameterWithName("campaignStatus").attributes(type(CampaignStatus.class)).description("???????????????").optional(),
										parameterWithName("localId").attributes(type(NUMBER)).description("??????ID").optional(),
										parameterWithName("applicantStatus").attributes(type(ApplicantStatus.class)).description("???????????????").optional(),
										parameterWithName("memberId").attributes(type(NUMBER)).description("??????ID").optional()
								),
								relaxedResponseFields(
										fieldWithPath("content").type(ARRAY).description("?????????"),
										fieldWithPath("content.[].id").type(NUMBER).description("?????????ID"),
										fieldWithPath("content.[].campaignCategoryDto.id").type(NUMBER).description("????????????ID"),
										fieldWithPath("content.[].campaignCategoryDto.name").type(STRING).description("??????????????????"),
										fieldWithPath("content.[].campaignTypeDto.id").type(NUMBER).description("??????ID"),
										fieldWithPath("content.[].campaignTypeDto.name").type(STRING).description("????????????"),
										fieldWithPath("content.[].snsDto.id").type(NUMBER).description("SNSID"),
										fieldWithPath("content.[].snsDto.name").type(STRING).description("SNS??????"),
										fieldWithPath("content.[].special").type(BOOLEAN).description("?????????"),
										fieldWithPath("content.[].pick").type(BOOLEAN).description("???"),
										fieldWithPath("content.[].title").type(STRING).description("??????"),
										fieldWithPath("content.[].fileName").type(STRING).description("???????????????"),
										fieldWithPath("content.[].provision").type(STRING).description("????????????"),
										fieldWithPath("content.[].created").type(LOCAL_DATE_TIME).description("?????????"),
										fieldWithPath("content.[].reviewNotice").type(STRING).description("??????????????????"),
										fieldWithPath("content.[].guideline").type(STRING).description("???????????????"),
										fieldWithPath("content.[].keywords").type(STRING).description("?????????"),
										fieldWithPath("content.[].applicationStartDate").type(LOCAL_DATE).description("???????????????"),
										fieldWithPath("content.[].applicationEndDate").type(LOCAL_DATE).description("???????????????"),
										fieldWithPath("content.[].filingStartDate").type(LOCAL_DATE).description("???????????????"),
										fieldWithPath("content.[].filingEndDate").type(LOCAL_DATE).description("???????????????"),
										fieldWithPath("content.[].headcount").type(NUMBER).description("????????????"),
										fieldWithPath("content.[].status").type(CampaignStatus.class).description("????????????"),
										fieldWithPath("content.[].campaignLocalDto.localId").type(NUMBER).description("??????ID").optional(),
										fieldWithPath("content.[].campaignLocalDto.localName").type(STRING).description("????????????").optional(),
										fieldWithPath("content.[].campaignLocalDto.address").type(STRING).description("??????").optional(),
										fieldWithPath("content.[].campaignLocalDto.latitude").type(STRING).description("??????").optional(),
										fieldWithPath("content.[].campaignLocalDto.longitude").type(STRING).description("??????").optional(),
										fieldWithPath("content.[].campaignLocalDto.visitNotice").type(STRING).description("??????????????????").optional(),
										fieldWithPath("content.[].imageList[]").type(ARRAY_STRING).description("?????????").optional(),
										fieldWithPath("pageable").type(Pageable.class).description("?????????"),
										fieldWithPath("last").type(BOOLEAN).description("??????????????????"),
										fieldWithPath("totalPages").type(NUMBER).description("???????????????"),
										fieldWithPath("totalElements").type(NUMBER).description("??????????????????"),
										fieldWithPath("size").type(NUMBER).description("?????????"),
										fieldWithPath("number").type(NUMBER).description("?????????"),
										fieldWithPath("sort").type(Sort.class).description("??????"),
										fieldWithPath("first").type(BOOLEAN).description("????????????"),
										fieldWithPath("numberOfElements").type(NUMBER).description("????????????"),
										fieldWithPath("empty").type(BOOLEAN).description("empty")
								)
						)
				);
	}

	@Test
	void ?????????_??????() throws Exception {

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
									parameterWithName("campaignId").attributes(type(NUMBER)).description("?????????ID")
								),
								requestParts(
										partWithName("categoryId").attributes(type(NUMBER)).description("????????????ID"),
										partWithName("typeId").attributes(type(NUMBER)).description("??????ID"),
										partWithName("snsId").attributes(type(NUMBER)).description("SNSID"),
										partWithName("special").attributes(type(BOOLEAN)).description("?????????"),
										partWithName("title").attributes(type(STRING)).description("??????"),
										partWithName("uploadFile").attributes(type(MULTIPART_FILE)).description("??????????????? ??????").optional(),
										partWithName("provision").attributes(type(STRING)).description("????????????"),
										partWithName("reviewNotice").attributes(type(STRING)).description("??????????????????"),
										partWithName("guideline").attributes(type(STRING)).description("???????????????"),
										partWithName("keywords").attributes(type(STRING)).description("?????????"),
										partWithName("applicationStartDate").attributes(type(LOCAL_DATE)).description("???????????????"),
										partWithName("applicationEndDate").attributes(type(LOCAL_DATE)).description("???????????????"),
										partWithName("filingStartDate").attributes(type(LOCAL_DATE)).description("???????????????"),
										partWithName("filingEndDate").attributes(type(LOCAL_DATE)).description("???????????????"),
										partWithName("headcount").attributes(type(NUMBER)).description("????????????"),
										partWithName("localId").attributes(type(NUMBER)).description("??????ID").optional(),
										partWithName("address").attributes(type(STRING)).description("??????").optional(),
										partWithName("latitude").attributes(type(STRING)).description("??????").optional(),
										partWithName("longitude").attributes(type(STRING)).description("??????").optional(),
										partWithName("visitNotice").attributes(type(STRING)).description("??????????????????").optional(),
										partWithName("campaignImageId").attributes(type(NUMBER)).description("?????? ????????? ID").optional(),
										partWithName("uploadFileList").attributes(type(MULTIPART_FILE)).description("????????? ????????? ??????").optional(),
										partWithName("applicationQuestionId").attributes(type(NUMBER)).description("??????ID").optional(),
										partWithName("question").attributes(type(NUMBER)).description("??????").optional(),
										partWithName("type").attributes(type(QuestionType.class)).description("??????").optional(),
										partWithName("items").attributes(type(ARRAY)).description("??????").optional()
								),
								responseHeaders(
										headerWithName(LOCATION).description(LOCATION)
								)
						)
				);
	}

	@Test
	void ?????????_??????_??????() throws Exception {

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
										parameterWithName("campaignId").attributes(type(NUMBER)).description("?????????ID")
								),
								requestFields(
										fieldWithPath("status").type(CampaignStatus.class).description("???????????????")
								)
						)
				);
	}

	@Test
	void ?????????_PICK_??????() throws Exception {

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
										fieldWithPath("newCampaignId").type(ARRAY_NUMBER).description("pick ????????? ?????????ID"),
										fieldWithPath("oldCampaignId").type(ARRAY_NUMBER).description("pick ????????? ?????????ID")
								)
						)
				);
	}

}