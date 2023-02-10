package com.bethefirst.lifeweb.dto.review.reqeust;

import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.review.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CreateReviewDto {

    private static final String TITLE = "title";
    private static final String IMG = "imgSrc";

    private Long campaignId;
    private String reviewUrl;

    public Review createReview(Member member, Campaign campaign, Map<String, String> crawlingReviewData){
        
        return new Review(member, campaign, crawlingReviewData.get(TITLE),
                crawlingReviewData.get(IMG), reviewUrl);
    }

}
