package com.bethefirst.lifeweb.entity.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewStatus {//상태-progress,complete,incomplete,cancel

	PROGRESS("진행"),//진행
	COMPLETE("완료"),//완료
	INCOMPLETE("미완료"),//미완료
	CANCEL("취소");//취소


	private String value;

}
