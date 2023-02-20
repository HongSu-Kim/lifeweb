package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.ControllerTest;
import com.bethefirst.lifeweb.entity.member.Role;
import com.bethefirst.lifeweb.initDto.campaign.InitCampaignCategoryDto;
import com.bethefirst.lifeweb.service.campaign.interfaces.CampaignCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.mockito.BDDMockito.*;//given,willReturn
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;//get,post,multipart...
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;//status
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;//responseHeaders
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;//headerWithName
import static org.springframework.restdocs.request.RequestDocumentation.*;//pathParameters,queryParameters,requestParts
import static org.springframework.restdocs.payload.PayloadDocumentation.*;//requestFields,responseFields
import static org.springframework.restdocs.payload.JsonFieldType.*;

@WebMvcTest(CampaignCategoryController.class)
class CampaignCategoryControllerTest extends ControllerTest {

	@MockBean
	CampaignCategoryService campaignCategoryService;

	String urlTemplate = "/campaign-categories";
	InitCampaignCategoryDto campaignCategoryDto = new InitCampaignCategoryDto();

	@Test
	void 캠페인카테고리_생성() throws Exception {

		willDoNothing().given(campaignCategoryService).createCampaignCategory("new campaignCategoryName");

		mockMvc.perform(post(urlTemplate)
						.content(objectMapper.writeValueAsString(Map.of(
								"campaignCategoryName", "new campaignCategoryName"
						)))
						.contentType(MediaType.APPLICATION_JSON)
						.header(AUTHORIZATION, getJwt(Role.ADMIN.name(), 1L))
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								requestFields(
										fieldWithPath("campaignCategoryName").attributes(type(STRING)).description("카테고리이름")
								),
								responseHeaders(
										headerWithName(HttpHeaders.LOCATION).description("Location")
								)
						)
				);
	}

	@Test
	void 캠페인카테고리_리스트_조회() throws Exception {

		given(campaignCategoryService.getCampaignCategoryDtoList()).willReturn(campaignCategoryDto.getCampaignCategoryDtoList());

		mockMvc.perform(get(urlTemplate))
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								responseFields(
										fieldWithPath("content.[].id").type(NUMBER).description("카테고리ID"),
										fieldWithPath("content.[].name").type(STRING).description("카테고리이름")
								)
						)
				);
	}

	@Test
	void 캠페인카테고리_수정() throws Exception {

		willDoNothing().given(campaignCategoryService).updateCampaignCategory(1L, "updated campaignCategoryName");

		mockMvc.perform(put(urlTemplate + "/{campaignCategoryId}", 1L)
						.content(objectMapper.writeValueAsString(Map.of(
								"campaignCategoryName", "updated campaignCategoryName"
						)))
						.contentType(MediaType.APPLICATION_JSON)
						.header(AUTHORIZATION, getJwt(Role.ADMIN.name(), 1L))
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("campaignCategoryId").attributes(type(NUMBER)).description("카테고리ID")
								),
								requestFields(
										fieldWithPath("campaignCategoryName").type(STRING).description("카테고리이름")
								),
								responseHeaders(
										headerWithName(HttpHeaders.LOCATION).description("Location")
								)
						)
				);
	}

	@Test
	void 캠페인카테고리_삭제() throws Exception {

		willDoNothing().given(campaignCategoryService).deleteCampaignCategory(1L);

		mockMvc.perform(delete(urlTemplate + "/{campaignCategoryId}", 1L)
						.header(AUTHORIZATION, getJwt(Role.ADMIN.name(), 1L))
				)
				.andExpect(status().isNoContent())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("campaignCategoryId").attributes(type(NUMBER)).description("카테고리ID")
								)
						)
				);
	}

}