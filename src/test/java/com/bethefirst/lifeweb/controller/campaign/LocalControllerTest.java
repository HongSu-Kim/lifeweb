package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.ControllerTest;
import com.bethefirst.lifeweb.initDto.campaign.InitLocalDto;
import com.bethefirst.lifeweb.service.campaign.interfaces.LocalService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.mockito.BDDMockito.*;//given,willReturn
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;//get,post,multipart...
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;//status
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;//responseHeaders
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;//headerWithName
import static org.springframework.restdocs.request.RequestDocumentation.*;//pathParameters,queryParameters,requestParts
import static org.springframework.restdocs.payload.PayloadDocumentation.*;//requestFields,responseFields
import static org.springframework.restdocs.payload.JsonFieldType.*;

@WebMvcTest(LocalController.class)
class LocalControllerTest extends ControllerTest {

	@MockBean
	LocalService localService;

	String urlTemplate = "/locals";
	InitLocalDto localDto = new InitLocalDto();

	@Test
	void 지역_생성() throws Exception {

		willDoNothing().given(localService).createLocal("new localName");

		mockMvc.perform(post(urlTemplate)
						.content(objectMapper.writeValueAsString(Map.of(
								"localName", "new localName"
						)))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								requestFields(
										fieldWithPath("localName").attributes(type(STRING)).description("지역이름")
								),
								responseHeaders(
										headerWithName(HttpHeaders.LOCATION).description("Location")
								)
						)
				);
	}

	@Test
	void 지역_리스트_조회() throws Exception {

		given(localService.getLocalDtoList()).willReturn(localDto.getLocalDtoList());

		mockMvc.perform(get(urlTemplate))
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								responseFields(
										fieldWithPath("content.[].id").type(NUMBER).description("지역ID"),
										fieldWithPath("content.[].name").type(STRING).description("지역이름")
								)
						)
				);
	}

	@Test
	void 지역_수정() throws Exception {

		willDoNothing().given(localService).updateLocal(1L, "updated localName");

		mockMvc.perform(put(urlTemplate + "/{localId}", 1L)
						.content(objectMapper.writeValueAsString(Map.of(
								"localName", "updated localName"
						)))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isCreated())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("localId").attributes(type(NUMBER)).description("지역ID")
								),
								requestFields(
										fieldWithPath("localName").type(STRING).description("지역이름")
								),
								responseHeaders(
										headerWithName(HttpHeaders.LOCATION).description("Location")
								)
						)
				);
	}

	@Test
	void 지역_삭제() throws Exception {

		willDoNothing().given(localService).deleteLocal(1L);

		mockMvc.perform(delete(urlTemplate + "/{localId}", 1L))
				.andExpect(status().isNoContent())
				.andDo(
						restDocs.document(
								pathParameters(
										parameterWithName("localId").attributes(type(NUMBER)).description("지역ID")
								)
						)
				);
	}

}