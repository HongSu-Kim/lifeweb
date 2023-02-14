package com.bethefirst.lifeweb.dto.member.response;

import com.bethefirst.lifeweb.entity.member.Sns;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnsDto {

	private Long id;//SNSID
	private String name;//SNS이름

	public SnsDto(Sns sns) {
		id = sns.getId();
		name = sns.getName();
	}

}
