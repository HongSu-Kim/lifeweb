package com.bethefirst.lifeweb.entity.campaign;

import com.bethefirst.lifeweb.entity.application.ApplicationAnswer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationQuestion {//신청서질문

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_question_id")
	private Long id;//신청서질문ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;//캠페인 FK

	private String question;//질문

	@Enumerated(EnumType.STRING)
	private QuestionType type;//유형

	private String items;//항목

	@OneToMany(mappedBy = "applicationQuestion")
	private List<ApplicationAnswer> applicationAnswerList = new ArrayList<>();//신청서답변

	public ApplicationQuestion(Campaign campaign, String question, QuestionType type, String items) {
		this.campaign = campaign;
		this.question = question;
		this.type = type;
		this.items = items;
	}

	/** 신청서질문 수정 */
	public void updateApplicationQuestion(String question, QuestionType type, String items) {
		this.question = question;
		this.type = type;
		this.items = items;
	}

}
