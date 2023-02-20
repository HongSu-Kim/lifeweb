package com.bethefirst.lifeweb.dto.application.request;

import com.bethefirst.lifeweb.entity.application.ApplicantStatus;
import lombok.*;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ApplicantSearchRequirements {//신청자 검색 조건

	private Pageable pageable;//페이징
	private Long memberId;//맴버
	private Long campaignId;//캠페인
	private ApplicantStatus status;//상태

}
