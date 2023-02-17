package com.bethefirst.lifeweb.dto.campaign.response;

import com.bethefirst.lifeweb.entity.campaign.Local;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalDto {

	private Long id;
	private String name;

	public LocalDto(Local local) {
		id = local.getId();
		name = local.getName();
	}

}
