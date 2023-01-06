package com.bethefirst.lifeweb.entity.campaign;

import com.bethefirst.lifeweb.entity.member.Sns;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CampaignSns {//캠페인SNS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_sns_id")
	private Long id;//캠페인SNSID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;//캠페인 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sns_id")
	private Sns sns;//SNS FK

	private int headcount;//모집인원

	public CampaignSns(Campaign campaign, Sns sns) {
		this.campaign = campaign;
		this.sns = sns;
		this.headcount = headcount;
	}

}
