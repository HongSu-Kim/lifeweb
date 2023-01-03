package com.bethefirst.lifeweb.entity.campaign;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Sns {//SNS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sns_id")
	private Long id;//SNSID PK

	private String name;//SNS이름

	@OneToMany(mappedBy = "sns")
	private List<CampaignSns> campaignSnsList = new ArrayList<>();//캠페인타입

}
