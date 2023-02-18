package com.bethefirst.lifeweb.controller.review;

import com.bethefirst.lifeweb.dto.review.reqeust.CreateReviewDto;
import com.bethefirst.lifeweb.dto.review.reqeust.ReviewSearchRequirements;
import com.bethefirst.lifeweb.dto.review.reqeust.UpdateReviewDto;
import com.bethefirst.lifeweb.dto.review.response.ReviewDto;
import com.bethefirst.lifeweb.service.review.interfaces.ReviewService;
import com.bethefirst.lifeweb.util.UrlUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
@Slf4j
public class ReviewController {

	private final ReviewService reviewService;
	private final UrlUtil urlUtil;

	/** 리뷰 등록 */
	@PostMapping("/{memberId}")
	@PreAuthorize("isAuthenticated() and (( #memberId == principal.memberId ) or hasRole('ADMIN'))")
	public ResponseEntity<?> create(@PathVariable Long memberId,
									@Valid @RequestBody CreateReviewDto createReviewDto) {

		//URL 유효성 검사
		urlUtil.inspectionUrl(createReviewDto.getReviewUrl());

		reviewService.createReview(createReviewDto, memberId);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/reviews"));
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 리뷰 삭제 */
	@DeleteMapping("/{memberId}/{reviewId}")
	@PreAuthorize("isAuthenticated() and (( #memberId == principal.memberId ) or hasRole('ADMIN'))")
	public ResponseEntity<?> delete(@PathVariable Long memberId,
									@PathVariable Long reviewId){
		reviewService.deleteReview(reviewId);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/reviews"));
		return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
	}

	/** 리뷰 수정 */
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{memberId}/{reviewId}")
	@PreAuthorize("isAuthenticated() and (( #memberId == principal.memberId ) or hasRole('ADMIN'))")
	public ResponseEntity<?> update(@PathVariable Long memberId,
									@PathVariable Long reviewId,
					   @RequestBody UpdateReviewDto updateReviewDto){

		//URL 유효성 검사
		urlUtil.inspectionUrl(updateReviewDto.getReviewUrl());

		reviewService.updateReview(updateReviewDto, reviewId);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/reviews"));
		return new ResponseEntity<>(headers, HttpStatus.CREATED);

	}

	/** 리뷰 전체 조회 */
	@GetMapping
	public Page<ReviewDto> list(@RequestBody ReviewSearchRequirements requirements,
								@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable){
		return  reviewService.getReviewList(requirements, pageable);
	}

}
