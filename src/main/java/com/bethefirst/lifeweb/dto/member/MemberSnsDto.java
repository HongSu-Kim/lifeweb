package com.bethefirst.lifeweb.dto.member;

import com.bethefirst.lifeweb.entity.member.MemberSns;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSnsDto {

	private Long snsId; //SNS PK
	private String snsName; //SNS 이름
	private String snsUrl;//SNS주소

	public MemberSnsDto(Long snsId, String snsName, String snsUrl) {
		this.snsId = snsId;
		this.snsName = snsName;
		this.snsUrl = snsUrl;
	}

	public MemberSnsDto(MemberSns memberSns) {
		this(memberSns.getSns().getId(),memberSns.getSns().getName(), memberSns.getSnsUrl());
	}
}
