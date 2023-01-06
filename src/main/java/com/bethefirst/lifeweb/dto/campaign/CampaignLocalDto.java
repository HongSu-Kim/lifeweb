package com.bethefirst.lifeweb.dto.campaign;

import com.bethefirst.lifeweb.entity.campaign.CampaignLocal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CampaignLocalDto {

	private String campaign_local_id;//캠페인지역ID

	private String localName;//지역

	private String address;//주소
	private String latitude;//위도
	private String longitude;//경도
	private String visitNotice;//방문주의사항

	public CampaignLocalDto(CampaignLocal campaignLocal) {

		localName = campaignLocal.getLocal().getName();

		address = campaignLocal.getAddress();
		latitude = campaignLocal.getLatitude();
		longitude = campaignLocal.getLongitude();
		visitNotice = campaignLocal.getVisitNotice();

	}

}
