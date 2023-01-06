package com.bethefirst.lifeweb.entity.campaign;

import com.bethefirst.lifeweb.dto.campaign.CreateCampaignDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class CampaignLocal {//캠페인카테고리

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

	public CampaignLocal(Campaign campaign, Local local, CreateCampaignDto createCampaignDto) {

		this.campaign = campaign;
		this.local = local;

		this.address = createCampaignDto.getAddress();
		this.latitude = createCampaignDto.getLatitude();
		this.longitude = createCampaignDto.getLongitude();
		this.visitNotice = createCampaignDto.getVisitNotice();

	}

}
