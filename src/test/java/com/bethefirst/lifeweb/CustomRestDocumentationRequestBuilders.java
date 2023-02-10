package com.bethefirst.lifeweb;

import org.springframework.http.HttpMethod;
import org.springframework.restdocs.generate.RestDocumentationGenerator;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class CustomRestDocumentationRequestBuilders {

	public static MockMultipartHttpServletRequestBuilder multipart(HttpMethod httpMethod, String urlTemplate, Object... urlVariables) {
		return (MockMultipartHttpServletRequestBuilder) MockMvcRequestBuilders.multipart(httpMethod, urlTemplate, urlVariables)
				.requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate);
	}

}
