package com.bethefirst.lifeweb.entity.review;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {//리뷰

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long id;//리뷰ID PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;//회원 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;//캠페인 FK

	private String url;//리뷰주소
	private String title;
	private String image;
	private LocalDateTime created;//등록일

	public Review(Member member, Campaign campaign, String title, String image, String url) {
		this.member = member;
		this.campaign = campaign;
		this.title = title;
		this.image = image;
		this.url = url;
		this.created = LocalDateTime.now();

	}

	public void updateReview(String reviewUrl){
		this.url = reviewUrl;
	}

}
