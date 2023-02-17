package com.bethefirst.lifeweb.dto.campaign.request;

import com.bethefirst.lifeweb.entity.application.ApplicantStatus;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class CampaignSearchRequirements {//캠페인 검색 조건

	private Pageable pageable;//페이징
	private Long categoryId;//카테고리
	private Long typeId;//타입
	private List<Long> snsId;//SNS
	private Boolean special;//스페셜
	private Boolean pick;//픽
	private CampaignStatus campaignStatus;//캠페인상태
	private Long localId;//지역
	private ApplicantStatus applicantStatus;//신청자상태
	private Long memberId;//회원

}
