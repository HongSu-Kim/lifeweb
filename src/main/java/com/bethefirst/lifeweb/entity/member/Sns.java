package com.bethefirst.lifeweb.entity.member;

import com.bethefirst.lifeweb.entity.application.Application;
import com.bethefirst.lifeweb.entity.campaign.CampaignSns;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Sns {//SNS

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_sns_id")
	private Long id;//회원SNSID PK

	private String name;//SNS이름

	@OneToMany(mappedBy = "sns")
	private List<MemberSns> memberSnsList = new ArrayList<>();//회원SNS

	@OneToMany(mappedBy = "sns")
	private List<CampaignSns> campaignSnsList = new ArrayList<>();//캠페인타입

	@OneToMany(mappedBy = "sns")
	private List<Application> applicationList = new ArrayList<>();//신청서

}
