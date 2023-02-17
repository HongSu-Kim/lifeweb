package com.bethefirst.lifeweb.entity.application;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {//신청서

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_id")
	private Long id;//신청서ID PK

	@OneToOne
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;//캠페인 FK


	@OneToMany(mappedBy = "application", cascade = CascadeType.REMOVE)
	private List<ApplicationQuestion> applicationQuestionList = new ArrayList<>();//신청서질문

	@OneToMany(mappedBy = "application", cascade = CascadeType.REMOVE)
	private List<Applicant> applicantList = new ArrayList<>();//신청자


	/** 신청서 생성 */
	public Application(Campaign campaign) {
		this.campaign = campaign;
	}

}
