package com.bethefirst.lifeweb.controller.application;

import com.bethefirst.lifeweb.ControllerTest;
import com.bethefirst.lifeweb.dto.application.request.CreateApplicantDto;
import com.bethefirst.lifeweb.dto.application.request.UpdateApplicantDto;
import com.bethefirst.lifeweb.dto.application.request.UpdateApplicantStatusDto;
import com.bethefirst.lifeweb.entity.application.ApplicantStatus;
import com.bethefirst.lifeweb.entity.member.Role;
import com.bethefirst.lifeweb.initDto.application.InitApplicantDto;
import com.bethefirst.lifeweb.service.application.interfaces.ApplicantService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;

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

@WebMvcTest(ApplicantController.class)
class ApplicantControllerTest extends ControllerTest {

	@MockBean
	ApplicantService applicationService;

	String urlTemplate = "/applicants";
	InitApplicantDto applicantDto = new InitApplicantDto();

	@Test
	void 신청자_생성() throws Exception {

		CreateApplicantDto dto = applicantDto.getCreateApplicantDto();
		given(applicationService.createApplicant(1L, dto)).willReturn(1L);

		mockMvc.perform(post(urlTemplate)
						.content(objectMapper.writeValueAsString(toMap(dto)))
						.contentType(APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, getJwt(Role.USER.name(), 1L))
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								requestFields(
										fieldWithPath("applicationId").type(NUMBER).description("신청서ID"),
										fieldWithPath("memo").type(STRING).description("메모"),
										fieldWithPath("applicationQuestionId").type(ARRAY_NUMBER).description("신청서질문ID").optional().optional(),
										fieldWithPath("answer").type(ARRAY_STRING).description("답변").optional().optional()
								),
								responseHeaders(
										headerWithName(LOCATION).description(LOCATION)
								)
						)
				);
	}

	@Test
	void 신청자_조회() throws Exception {

		given(applicationService.getApplicantDto(1L)).willReturn(applicantDto.getApplicantDto());

		mockMvc.perform(get(urlTemplate + "/{applicantId}", 1L)
						.header(AUTHORIZATION, getJwt(Role.USER.name(), 1L))
				)
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("applicantId").attributes(type(NUMBER)).description("신청자ID")
								),
								responseFields(
										fieldWithPath("id").type(NUMBER).description("신청자ID"),
										fieldWithPath("memo").type(STRING).description("메모"),
										fieldWithPath("created").type(LOCAL_DATE_TIME).description("신청일"),
										fieldWithPath("applicantAnswerDtoList[].id").type(NUMBER).description("신청자답변ID").optional(),
										fieldWithPath("applicantAnswerDtoList[].applicationQuestionId").type(NUMBER).description("질문ID").optional(),
										fieldWithPath("applicantAnswerDtoList[].answer").type(STRING).description("답변").optional()
								)
						)
				);
	}

	@Test
	void 신청자_리스트_조회() throws Exception {

		given(applicationService.getApplicantDtoList(applicantDto.getSearchRequirements())).willReturn(applicantDto.getApplicantDtoPage());

		mockMvc.perform(get(urlTemplate)
						.param("page", "1")
						.param("size", "10")
						.param("sort", "created,desc")
						.header(AUTHORIZATION, getJwt(Role.USER.name(), 1L))
				)
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								queryParameters(
										parameterWithName("page").attributes(type(NUMBER)).description("페이지").optional(),
										parameterWithName("size").attributes(type(NUMBER)).description("사이즈").optional(),
										parameterWithName("sort").attributes(type(STRING)).description("정렬기준,정렬방향").optional(),
										parameterWithName("memberId").attributes(type(NUMBER)).description("맴버ID").optional(),
										parameterWithName("campaignId").attributes(type(NUMBER)).description("캠페인ID").optional(),
										parameterWithName("status").attributes(type(ApplicantStatus.class)).description("상태").optional()
								),
								relaxedResponseFields(
										fieldWithPath("content").type(ARRAY).description("리스트"),
										fieldWithPath("content.[].id").type(NUMBER).description("신청자ID"),
										fieldWithPath("content.[].memo").type(STRING).description("메모"),
										fieldWithPath("content.[].created").type(LOCAL_DATE_TIME).description("신청일"),
										fieldWithPath("content.[].applicantAnswerDtoList[].id").type(NUMBER).description("신청자답변ID").optional(),
										fieldWithPath("content.[].applicantAnswerDtoList[].applicationQuestionId").type(NUMBER).description("질문ID").optional(),
										fieldWithPath("content.[].applicantAnswerDtoList[].answer").type(STRING).description("답변").optional(),
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
	void 신청자_수정() throws Exception {

		UpdateApplicantDto dto = applicantDto.getUpdateApplicantDto();
		willDoNothing().given(applicationService).updateApplicant(1L, dto);

		mockMvc.perform(put(urlTemplate + "/{applicantId}", 1L)
						.content(objectMapper.writeValueAsString(toMap(dto)))
						.contentType(APPLICATION_JSON)
						.header(AUTHORIZATION, getJwt(Role.USER.name(), 1L))
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("applicantId").attributes(type(NUMBER)).description("신청자ID")
								),
								requestFields(
										fieldWithPath("applicantId").type(NUMBER).description("신청자ID"),
										fieldWithPath("memo").type(STRING).description("메모"),
										fieldWithPath("applicantAnswerId").type(ARRAY_NUMBER).description("신청서질문ID").optional(),
										fieldWithPath("applicationQuestionId").type(ARRAY_NUMBER).description("신청서질문ID").optional(),
										fieldWithPath("answer").type(ARRAY_STRING).description("답변").optional()
								),
								responseHeaders(
										headerWithName(LOCATION).description(LOCATION)
								)
						)
				);
	}

	@Test
	void 신청자_상태_수정() throws Exception {

		UpdateApplicantStatusDto dto = applicantDto.getUpdateApplicantStatusDto();
		willDoNothing().given(applicationService).updateStatus(dto);

		mockMvc.perform(put(urlTemplate + "/status")
						.content(objectMapper.writeValueAsString(toMap(dto)))
						.contentType(APPLICATION_JSON)
						.header(AUTHORIZATION, getJwt(Role.ADMIN.name(), 1L))
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								requestFields(
										fieldWithPath("campaignId").type(NUMBER).description("캠페인ID"),
										fieldWithPath("selectApplicantId").type(ARRAY_NUMBER).description("선정할 신청자ID"),
										fieldWithPath("unselectApplicantId").type(ARRAY_NUMBER).description("비선정할 신청자ID")
								)
						)
				);
	}

	@Test
	void 신청자_삭제() throws Exception {

		willDoNothing().given(applicationService).deleteApplicant(1L);

		mockMvc.perform(delete(urlTemplate + "/{applicantId}", 1L)
						.header(AUTHORIZATION, getJwt(Role.USER.name(), 1L))
				)
				.andExpect(status().isNoContent())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("applicantId").attributes(type(NUMBER)).description("신청자ID")
								)
						)
				);
	}

}