package com.bethefirst.lifeweb.dto.campaign;

import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignSearchRequirements {//캠페인 검색 조건

	private Pageable pageable;//페이징
	private Long categoryId;//카테고리
	private Long typeId;//타입
	private List<Long> snsIdList;//SNS
	private Boolean special;//스페셜
	private CampaignStatus status;//상태
	private Long localId;//지역

}
