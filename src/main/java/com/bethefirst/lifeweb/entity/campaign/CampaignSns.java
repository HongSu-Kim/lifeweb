package com.bethefirst.lifeweb.entity.campaign;

import com.bethefirst.lifeweb.entity.member.Sns;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampaignSns {//캠페인SNS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_sns_id")
	private Long id;//캠페인SNSID PK

	@OneToOne
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;//캠페인 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sns_id")
	private Sns sns;//SNS FK

	private int headcount;//모집인원

	private CampaignSns(Campaign campaign, Sns sns, int headcount) {
		this.campaign = campaign;
		this.sns = sns;
		this.headcount = headcount;
	}

	public static CampaignSns createCampaignSns(Campaign campaign, Sns sns, int headcount) {
		return new CampaignSns(campaign, sns, headcount);
	}

	public void update(Campaign campaign, Sns sns, Integer headcount) {

		this.campaign = campaign;
		this.sns = sns;
		this.headcount = headcount;

	}
}
