package com.bethefirst.lifeweb.entity.member;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
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
	@Column(name = "sns_id")
	private Long id;//SNSID PK

	private String name;//SNS이름

	@OneToMany(mappedBy = "sns")
	private List<MemberSns> memberSnsList = new ArrayList<>();//회원SNS

	@OneToMany(mappedBy = "sns")
	private List<Campaign> campaignList = new ArrayList<>();//캠페인

	public Sns(String name) {
		this.name = name;
	}
}
