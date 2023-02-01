package com.bethefirst.lifeweb.service.review.interfaces;

import com.bethefirst.lifeweb.dto.review.reqeust.ReviewCreateDto;
import com.bethefirst.lifeweb.dto.review.reqeust.ReviewUpdateDto;

public interface ReviewService {

    /** 리뷰 등록 */
    void createReview(ReviewCreateDto reviewCreateDto, Long memberId);

    /** 리뷰 삭제 */
    void deleteReview(Long reviewId);

    /** 리뷰 수정 */
    void updateReview(ReviewUpdateDto reviewUpdateDto, Long reviewId);
}
