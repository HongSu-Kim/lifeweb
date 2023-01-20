package com.bethefirst.lifeweb.entity.campaign;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampaignImage {//캠페인이미지

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_image_id")
	private Long id;//캠페인이미지ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;//캠페인 FK

	private String fileName;//이미지이름

	private CampaignImage(Campaign campaign, String fileName) {
		this.campaign = campaign;
		this.fileName = fileName;
	}

	public static CampaignImage createCampaignImage(Campaign campaign, String fileName) {
		return new CampaignImage(campaign, fileName);
	}

}
