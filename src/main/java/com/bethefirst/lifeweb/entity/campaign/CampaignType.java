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
public class CampaignType {//캠페인타입-방문형,배송형,기자단,방문기자단

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_type_id")
	private Long id;//캠페인타입ID PK

	private String name;//타입이름

	@OneToMany(mappedBy = "campaignType")
	private List<Campaign> campaignList = new ArrayList<>();//캠페인

}
