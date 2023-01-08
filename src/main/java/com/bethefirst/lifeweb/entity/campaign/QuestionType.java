package com.bethefirst.lifeweb.entity.campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuestionType {//타입-text,textarea,radio,checkbox

	TEXT("단답형"),//단답형
	TEXTAREA("장문형"),//장문형
    RADIO("단일선택"),//단일선택
	CHECKBOX("복수선택");//복수선택

	private String value;

}
