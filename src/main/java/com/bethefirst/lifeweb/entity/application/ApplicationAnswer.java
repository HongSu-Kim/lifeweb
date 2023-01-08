package com.bethefirst.lifeweb.entity.application;

import com.bethefirst.lifeweb.entity.campaign.ApplicationQuestion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class ApplicationAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_answer_id")
	private Long id;//신청서답변ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_id")
	private Application application;//신청서 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_question_id")
	private ApplicationQuestion applicationQuestion;//신청서질문 FK

	private String answer;//답변

}
