package com.bethefirst.lifeweb.entity.campaign;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampaignCategory {//캠페인카테고리

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_category_id")
	private Long id;//캠페인카테고리ID PK

	private String name;//카테고리명

	@OneToMany(mappedBy = "campaignCategory")
	private List<Campaign> campaignList = new ArrayList<>();//캠페인

	private CampaignCategory(String name) {
		this.name = name;
	}

	/** 캠페인카테고리 생성 */
	public static CampaignCategory createCampaignCategory(String name) {
		return new CampaignCategory(name);
	}

	/** 캠페인카테고리 수정 */
	public void updateCampaignCategory(String name) {
		this.name = name;
	}

}
