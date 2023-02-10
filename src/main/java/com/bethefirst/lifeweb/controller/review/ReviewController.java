package com.bethefirst.lifeweb.controller.review;

import com.bethefirst.lifeweb.dto.review.reqeust.CreateReviewDto;
import com.bethefirst.lifeweb.dto.review.reqeust.ReviewSearchRequirements;
import com.bethefirst.lifeweb.dto.review.reqeust.UpdateReviewDto;
import com.bethefirst.lifeweb.dto.review.response.ReviewDto;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import com.bethefirst.lifeweb.service.review.interfaces.ReviewService;
import com.bethefirst.lifeweb.util.UrlUtil;
import com.bethefirst.lifeweb.util.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
@Slf4j
public class ReviewController {

	private final ReviewService reviewService;
	private final UrlUtil urlUtil;

	/** 리뷰 등록 */
	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public void create(@Valid @RequestBody CreateReviewDto createReviewDto){

		urlUtil.inspectionUrl(createReviewDto.getReviewUrl());

		Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
				-> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

		reviewService.createReview(createReviewDto, currentMemberId);
	}

	/** 리뷰 삭제 */
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{reviewId}")
	public void delete(@PathVariable Long reviewId){
		reviewService.deleteReview(reviewId);
	}

	/** 리뷰 수정 */
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{reviewId}")
	public void update(@PathVariable Long reviewId,
					   @RequestBody UpdateReviewDto updateReviewDto){

		urlUtil.inspectionUrl(updateReviewDto.getReviewUrl());

		reviewService.updateReview(updateReviewDto, reviewId);
	}

	/** 리뷰 전체 조회 */
	@GetMapping
	public Page<ReviewDto> list(@RequestBody ReviewSearchRequirements requirements,
								@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable){
		return  reviewService.getReviewList(requirements, pageable);
	}

}
