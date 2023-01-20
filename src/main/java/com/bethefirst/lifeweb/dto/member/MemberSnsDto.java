package com.bethefirst.lifeweb.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSnsDto {

	private Long id; //SNS PK
	private String snsName;//SNS
	private String snsUrl;//SNS주소

	public MemberSnsDto(Long id, String snsName, String snsUrl) {
		this.id = id;
		this.snsName = snsName;
		this.snsUrl = snsUrl;
	}
}
