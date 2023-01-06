package com.bethefirst.lifeweb.entity.campaign;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CampaignImage {//캠페인이미지

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_image_id")
	private Long id;//캠페인이미지ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;//캠페인 FK

	private String fileName;//이미지이름

	public CampaignImage(Campaign campaign, String fileName) {
		this.campaign = campaign;
		this.fileName = fileName;
	}

}
