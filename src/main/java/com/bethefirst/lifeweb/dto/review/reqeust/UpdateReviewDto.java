package com.bethefirst.lifeweb.dto.review.reqeust;

import com.bethefirst.lifeweb.entity.review.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReviewDto {

    private Long memberId; //회원 PK
    private String url; //리뷰 URL



    public void updateReview(Review review){
        review.updateReview(url);
    }
}
