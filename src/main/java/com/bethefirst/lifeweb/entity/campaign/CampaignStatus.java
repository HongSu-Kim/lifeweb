package com.bethefirst.lifeweb.entity.campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CampaignStatus {//상태-stand,progress,complete,stop

	STAND("대기"),//대기
	PROGRESS("진행"),//진행
    COMPLETE("완료"),//완료
	STOP("중지");//중지

	private String value;

}
