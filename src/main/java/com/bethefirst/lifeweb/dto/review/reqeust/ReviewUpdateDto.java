package com.bethefirst.lifeweb.dto.review.reqeust;

import com.bethefirst.lifeweb.entity.review.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateDto {

    private String reviewUrl;


    public void updateReview(Review review){
        review.updateReview(reviewUrl);
    }
}
