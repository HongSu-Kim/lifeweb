package com.bethefirst.lifeweb.entity.campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CampaignStatus {//상태-stand,application,filing,complete

	STAND("대기"),//대기
	APPLICATION("신청"),//신청
	FILING("등록"),//등록
    COMPLETE("완료");//완료

	private String value;

}
