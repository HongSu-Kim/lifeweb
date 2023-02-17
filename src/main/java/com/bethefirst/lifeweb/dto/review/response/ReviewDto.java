package com.bethefirst.lifeweb.dto.review.response;

import com.bethefirst.lifeweb.dto.campaign.CampaignDto;
import com.bethefirst.lifeweb.dto.member.response.MemberDto;
import com.bethefirst.lifeweb.entity.review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {

	private Long id;//리뷰ID

	private MemberDto memberDto;//회원
	private CampaignDto campaignDto;//캠페인

	private String reviewUrl;//리뷰주소
	private LocalDateTime created;//등록일

	public ReviewDto(Review review) {

		id = review.getId();

		memberDto = new MemberDto(review.getMember());
		campaignDto = new CampaignDto(review.getCampaign());

		reviewUrl = review.getReviewUrl();
		created = review.getCreated();

	}

}
