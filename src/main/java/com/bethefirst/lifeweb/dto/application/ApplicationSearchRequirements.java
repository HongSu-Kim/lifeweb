package com.bethefirst.lifeweb.dto.application;

import lombok.*;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 신청서 검색 조건
public class ApplicationSearchRequirements {

	private Pageable pageable;//페이징
	private Long memberId;//맴버
	private Long campaignId;//캠페인

}
