package com.bethefirst.lifeweb.repository.review;

import com.bethefirst.lifeweb.dto.review.reqeust.ReviewSearchRequirements;
import com.bethefirst.lifeweb.entity.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryQueryDsl {

    /** 리뷰 리스트 조회 */
    Page<Review> findAllBySearchRequirements(ReviewSearchRequirements searchRequirements, Pageable pageable);
}
