package com.bethefirst.lifeweb.entity.campaign;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_local_id")
	private Long id;//캠페인지역ID PK

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;//캠페인 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "local_id")
	private Local local;//지역 FK

	private String address;//주소
	private String latitude;//위도
	private String longitude;//경도
	private String visitNotice;//방문주의사항

}
