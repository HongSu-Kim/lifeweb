package com.bethefirst.lifeweb.controller.apllication;

import com.bethefirst.lifeweb.ControllerTest;
import com.bethefirst.lifeweb.controller.application.ApplicationController;
import com.bethefirst.lifeweb.initDto.application.InitApplicationDto;
import com.bethefirst.lifeweb.service.application.interfaces.ApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.*;//given,willReturn
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;//get,post,multipart...
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;//status
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;//responseHeaders
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;//headerWithName
import static org.springframework.restdocs.request.RequestDocumentation.*;//pathParameters,queryParameters,requestParts
import static org.springframework.restdocs.payload.PayloadDocumentation.*;//requestFields,responseFields
import static org.springframework.restdocs.payload.JsonFieldType.*;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest extends ControllerTest {

	@MockBean
	ApplicationService applicationService;

	String urlTemplate = "/applications";
	InitApplicationDto applicationDto = new InitApplicationDto();

//	@Test
//	void 신청서_생성() throws Exception {
//
//		mockMvc.perform(post(urlTemplate)
////					.content(objectMapper.writeValueAsString())
//					.contentType(MediaType.APPLICATION_JSON)
//				)
//				.andExpect(status().isCreated());
//
//	}

	@Test
	void 신청서_조회() throws Exception {

	}

	@Test
	void 신청서_리스트_조회() throws Exception {

	}

	@Test
	void 신청서_수정() throws Exception {

	}

	@Test
	void 신청서_상태_수정() throws Exception {

	}
	@Test

	void 신청서_삭제() throws Exception {

	}

}