package com.bethefirst.lifeweb.entity.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicantStatus {//상태-select,unselect

	SELECT("선정"),//선정
	UNSELECT("미선정");//미선정

	private String value;

}
