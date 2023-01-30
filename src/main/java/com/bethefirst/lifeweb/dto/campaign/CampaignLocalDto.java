package com.bethefirst.lifeweb.dto.campaign;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.campaign.CampaignLocal;
import com.bethefirst.lifeweb.entity.campaign.Local;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CampaignLocalDto {

	private Long localId;//지역ID
	private String localName;//지역이름

	private String address;//주소
	private String latitude;//위도
	private String longitude;//경도
	private String visitNotice;//방문주의사항

	public CampaignLocalDto(CampaignLocal campaignLocal) {

		localId = campaignLocal.getLocal().getId();
		localName = campaignLocal.getLocal().getName();

		address = campaignLocal.getAddress();
		latitude = campaignLocal.getLatitude();
		longitude = campaignLocal.getLongitude();
		visitNotice = campaignLocal.getVisitNotice();

	}

	public CampaignLocalDto(Long localId, String address, String latitude, String longitude, String visitNotice) {

		this.localId = localId;

		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.visitNotice = visitNotice;

	}

	/** 캠페인지역 생성 */
	public CampaignLocal createCampaignLocal(Campaign campaign, Local local) {
		return new CampaignLocal(campaign, local, address, latitude, longitude, visitNotice);
	}

	/** 캠페인지역 수정 */
	public void updateCampaignLocal(CampaignLocal campaignLocal, Local local) {
		campaignLocal.updateCampaignLocal(local, address, latitude, longitude, visitNotice);
	}

}
