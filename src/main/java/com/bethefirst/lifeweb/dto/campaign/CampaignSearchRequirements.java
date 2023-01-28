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
// 캠페인 검색 조건
public class CampaignSearchRequirements {

	private Pageable pageable;//페이징
	private String categoryName;//카테고리
	private String typeName;//타입
	private List<String> snsNameList;//SNS
	private Boolean special;//스페셜
	private CampaignStatus status;//상태
	private String localName;//지역

}
