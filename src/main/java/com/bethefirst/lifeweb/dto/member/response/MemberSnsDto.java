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
	private String name; //SNS 이름
	private String url;//SNS주소

	public MemberSnsDto(MemberSns memberSns) {
		this.snsId = memberSns.getSns().getId() == null ? null : memberSns.getSns().getId() ;
		this.memberSnsId = memberSns.getId() == null ? null : memberSns.getId();
		this.name = memberSns.getSns().getName() == null ? null : memberSns.getSns().getName();
		this.url = memberSns.getUrl() == null ? null : memberSns.getUrl();
	}

}
