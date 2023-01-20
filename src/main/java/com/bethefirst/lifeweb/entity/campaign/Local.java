package com.bethefirst.lifeweb.entity.campaign;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Local {//지역

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "local_id")
	private Long id;//지역ID PK

	private String name;//지역이름

	@OneToMany(mappedBy = "local")
	private List<CampaignLocal> campaignLocalList = new ArrayList<>();//캠페인지역

}
