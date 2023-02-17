package com.bethefirst.lifeweb.dto.application.response;

import com.bethefirst.lifeweb.entity.application.Applicant;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ApplicantDto {

	private Long id;//신청자ID

//	private MemberDto memberDto;//회원
//	private ApplicationDto applicationDto;//신청서

	private String memo;//메모
	private LocalDateTime created;//신청일

	private List<ApplicantAnswerDto> applicantAnswerDtoList;//신청자답변

	public ApplicantDto(Applicant applicant) {

		id = applicant.getId();

//		memberDto = new MemberDto(applicant.getMember());
//		applicationDto = new ApplicationDto(applicant.getApplication());

		memo = applicant.getMemo();
		created = applicant.getCreated();

		applicantAnswerDtoList = applicant.getApplicantAnswerList()
				.stream().map(ApplicantAnswerDto::new)
				.collect(Collectors.toList());

	}

}
