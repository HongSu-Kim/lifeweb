package com.bethefirst.lifeweb.service.review.interfaces;

import com.bethefirst.lifeweb.dto.review.reqeust.ReviewCreateDto;
import com.bethefirst.lifeweb.dto.review.reqeust.ReviewSearchRequirements;
import com.bethefirst.lifeweb.dto.review.reqeust.ReviewUpdateDto;
import com.bethefirst.lifeweb.dto.review.response.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    /** 리뷰 등록 */
    void createReview(ReviewCreateDto reviewCreateDto, Long memberId);

    /** 리뷰 삭제 */
    void deleteReview(Long reviewId);

    /** 리뷰 수정 */
    void updateReview(ReviewUpdateDto reviewUpdateDto, Long reviewId);

    /** 검색조건에 따른 리뷰 전체조회 */
    Page<ReviewDto> getReviewList(ReviewSearchRequirements requirements, Pageable pageable);


}
