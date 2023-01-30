package com.bethefirst.lifeweb.entity.campaign;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampaignLocal {//캠페인지역

	@Id
	private Long id;

	@MapsId
	@OneToOne
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;//캠페인 PK FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "local_id")
	private Local local;//지역 FK

	private String address;//주소
	private String latitude;//위도
	private String longitude;//경도
	private String visitNotice;//방문주의사항

	public CampaignLocal(Campaign campaign, Local local, String address, String latitude, String longitude, String visitNotice) {

		this.campaign = campaign;
		this.local = local;

		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.visitNotice = visitNotice;

	}

	/** 캠페인지역 수정 */
	public void updateCampaignLocal(Local local, String address, String latitude, String longitude, String visitNotice) {

		this.local = local;

		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.visitNotice = visitNotice;

	}
}
