package com.bethefirst.lifeweb.controller.application;

import com.bethefirst.lifeweb.ControllerTest;
import com.bethefirst.lifeweb.dto.application.request.CreateApplicationQuestionDto;
import com.bethefirst.lifeweb.entity.application.QuestionType;
import com.bethefirst.lifeweb.entity.member.Role;
import com.bethefirst.lifeweb.initDto.application.InitApplicationDto;
import com.bethefirst.lifeweb.service.application.interfaces.ApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest extends ControllerTest {

	@MockBean
	ApplicationService applicationService;

	String urlTemplate = "/applications";
	InitApplicationDto applicationDto = new InitApplicationDto();

	@Test
	void 신청서_조회() throws Exception {

		given(applicationService.getApplicationDto(1L)).willReturn(applicationDto.getApplicationDto());

		mockMvc.perform(get(urlTemplate + "/{applicationId}", 1L)
						.header(AUTHORIZATION, getJwt(Role.USER.name(), 1L))
				)
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("applicationId").attributes(type(NUMBER)).description("신청서ID")
								),
								responseFields(
										fieldWithPath("id").type(NUMBER).description("신청서ID"),
										fieldWithPath("applicationQuestionDtoList.[].id").type(NUMBER).description("질문ID").optional(),
										fieldWithPath("applicationQuestionDtoList.[].question").type(STRING).description("질문").optional(),
										fieldWithPath("applicationQuestionDtoList.[].type").type(QuestionType.class).description("유형").optional(),
										fieldWithPath("applicationQuestionDtoList.[].items").type(STRING).description("항목").optional()
								)
						)
				);
	}

	@Test
	void 신청서_질문_추가() throws Exception {

		CreateApplicationQuestionDto dto = applicationDto.getCreateApplicationQuestionDto();
		willDoNothing().given(applicationService).createApplicationQuestion(1L, dto);

		mockMvc.perform(put(urlTemplate + "/{applicationId}/question", 1L)
						.content(objectMapper.writeValueAsString(toMap(dto)))
						.contentType(APPLICATION_JSON)
						.header(AUTHORIZATION, getJwt(Role.ADMIN.name(), 1L))
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("applicationId").attributes(type(NUMBER)).description("신청서ID")
								),
								requestFields(
										fieldWithPath("question").type(ARRAY_STRING).description("질문"),
										fieldWithPath("type").type(ARRAY).description("유형"),
										fieldWithPath("items").type(ARRAY_STRING).description("항목")
								),
								responseHeaders(
										headerWithName(LOCATION).description(LOCATION)
								)
						)
				);
	}

}