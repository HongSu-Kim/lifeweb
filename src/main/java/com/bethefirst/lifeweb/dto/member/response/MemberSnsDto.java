package com.bethefirst.lifeweb.dto.member.response;

import com.bethefirst.lifeweb.entity.member.MemberSns;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSnsDto {

	private Long snsId; //SNS PK
	private Long memberSnsId; //회원 SNS PK
	private String snsName; //SNS 이름
	private String snsUrl;//SNS주소

	public MemberSnsDto(MemberSns memberSns) {
		this.snsId = memberSns.getSns().getId() == null ? null : memberSns.getSns().getId() ;
		this.memberSnsId = memberSns.getId() == null ? null : memberSns.getId();
		this.snsName = memberSns.getSns().getName() == null ? null : memberSns.getSns().getName();
		this.snsUrl = memberSns.getSnsUrl() == null ? null : memberSns.getSnsUrl();
	}

}
