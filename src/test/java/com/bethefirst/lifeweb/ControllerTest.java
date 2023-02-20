package com.bethefirst.lifeweb;

import com.bethefirst.lifeweb.dto.CustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Attributes.Attribute;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.beans.PropertyDescriptor;
import java.util.*;

import static com.bethefirst.lifeweb.RestDocsConfig.field;

@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
@Slf4j
public abstract class ControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected RestDocumentationResultHandler restDocs;
	@Autowired
	protected ObjectMapper objectMapper;
	protected CustomUser user;


	@BeforeEach
	void setUp(final WebApplicationContext context,
			   final RestDocumentationContextProvider provider) {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(MockMvcRestDocumentation.documentationConfiguration(provider)) // rest docs 설정 주입
				.alwaysDo(MockMvcResultHandlers.print()) // andDo(print()) 코드 포함
				.alwaysDo(restDocs) // pretty 패턴과 문서 디렉토리 명 정해준것 적용
				.addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
				.build();
	}

	protected String getJwt(String role, Long memberId) {

		user = new CustomUser("username", "password", Arrays.asList(new SimpleGrantedAuthority(role)), memberId);
		PreAuthenticatedAuthenticationToken auth = new PreAuthenticatedAuthenticationToken(user, null, user.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);
		return Jwts.builder()
				.setSubject(user.getUsername())
				.claim("memberId", user.getMemberId())
				.claim("role", user.getAuthorities())
				.compact();
	}

//	protected MockHttpServletRequestBuilder createParamsRequest(
//			MockHttpServletRequestBuilder request,
//			Object dto) {
//
//		//Request에 DTO의 모든 속성,값 정보 추가
//		final PropertyDescriptor[] getterDescriptors = ReflectUtils.getBeanGetters(dto.getClass());
//		for (PropertyDescriptor pd : getterDescriptors) {
//			try {
//				//파라미터로 전달한 인스턴스에 대해 getter함수를 호출한 값 얻어옴
//				Object invoke = pd.getReadMethod().invoke(dto);
//				if (invoke instanceof List<?>) {
//					((List<?>) invoke).forEach(o -> param(request,pd.getName(), o));
//				} else {
//					param(request, pd.getName(), invoke);
//				}
//			} catch (Exception e) {
//				log.error(e.toString());
//			}
//		}
//		return request;
//	}
//
//	private MockHttpServletRequestBuilder param(
//			MockHttpServletRequestBuilder request,
//			String name,
//			Object value) {
//
//		if ((value instanceof String || value instanceof Number || value instanceof Boolean || value instanceof Enum<?>)
//				&& (!String.valueOf(value).equals("null"))) {
//			request.param(name, String.valueOf(value));
//		}
//		return request;
//	}

	protected MockHttpServletRequestBuilder createMultiPartRequest(
			MockMultipartHttpServletRequestBuilder multipartRequest,
			Object dto) {

		final PropertyDescriptor[] getterDescriptors = ReflectUtils.getBeanGetters(dto.getClass());
		for (PropertyDescriptor pd : getterDescriptors) {
			try {
				Object invoke = pd.getReadMethod().invoke(dto);

				if (invoke instanceof List<?>) {
					((List<?>) invoke).forEach(o -> fileOrPart(multipartRequest,pd.getName(), o));

				} else {
					fileOrPart(multipartRequest, pd.getName(), invoke);
				}
			} catch (Exception e) {
				log.error(e.toString());
			}
		}

		return multipartRequest;
	}

	private MockMultipartHttpServletRequestBuilder fileOrPart(
			MockMultipartHttpServletRequestBuilder multipartRequest,
			String name,
			Object value) {

		if ((value instanceof MockMultipartFile)
				&& (!((MockMultipartFile) value).isEmpty())) {
			multipartRequest.file((MockMultipartFile) value);

		} else if (value instanceof String || value instanceof Number || value instanceof Boolean || value instanceof Enum<?>) {
			if (!String.valueOf(value).equals("null")) {
				multipartRequest.part(new MockPart(name, (String.valueOf(value)).getBytes()));
			} else {
				multipartRequest.part(new MockPart(name, ("").getBytes()));
			}
		}

		return multipartRequest;
	}

	protected Map<String, String> toMap(Object dto) {

		Map map = new HashMap<>();

		final PropertyDescriptor[] getterDescriptors = ReflectUtils.getBeanGetters(dto.getClass());
		for (PropertyDescriptor pd : getterDescriptors) {
			try {
				Object invoke = pd.getReadMethod().invoke(dto);
				if (invoke instanceof String || invoke instanceof Number || invoke instanceof Boolean || invoke instanceof Enum<?>) {
					map.put(pd.getName(), invoke);
				} else {
					List list = (List<?>) invoke;
					if (list.get(0) instanceof String || list.get(0) instanceof Number || list.get(0) instanceof Boolean || list.get(0) instanceof Enum<?>) {
						map.put(pd.getName(), invoke);
					}
				}
			} catch (Exception e) {
				log.error(e.toString());
			}
		}

		return map;
	}

	protected Attribute type(Object value) {
		return field("type", value.toString());
	}

	protected Attribute role(Object value) {
		return field("role", value.toString());
	}

}
