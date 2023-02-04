package com.bethefirst.lifeweb.dto.review.reqeust;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.review.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateDto {

    private Long campaignId;
    private String reviewUrl;

    public Review createReview(Member member, Campaign campaign){
        return new Review(member, campaign, reviewUrl);
    }

}
