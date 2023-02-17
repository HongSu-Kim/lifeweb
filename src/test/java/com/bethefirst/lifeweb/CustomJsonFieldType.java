package com.bethefirst.lifeweb;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CustomJsonFieldType {

	LOCAL_DATE("LocalDate : yyyy-mm-dd"),
	LOCAL_DATE_TIME("LocalDateTime"),
	ARRAY_NUMBER("Array<Number>"),
	ARRAY_STRING("Array<String>"),
	MULTIPART_FILE("MultipartFile"),
	ARRAY_MULTIPART_FILE("Array<MultipartFile>");

	private String string;

	@Override
	public String toString() {
		return this.string;
	}

}
