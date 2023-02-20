package com.bethefirst.lifeweb.dto.application.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UpdateApplicantStatusDto {

	@NotNull(message = "캠페인ID는 필수 입력 값입니다.")
	private Long campaignId;

	private List<Long> selectApplicantId;
	private List<Long> unselectApplicantId;

}
