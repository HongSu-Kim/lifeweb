package com.bethefirst.lifeweb.dto.member;

import com.bethefirst.lifeweb.entity.member.MemberSns;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSnsDto {

	private String snsName;//SNS
	private String snsUrl;//SNS주소

	public MemberSnsDto(MemberSns memberSns) {
		snsName = memberSns.getSns().getName();
		snsUrl = memberSns.getSnsUrl();
	}

}
