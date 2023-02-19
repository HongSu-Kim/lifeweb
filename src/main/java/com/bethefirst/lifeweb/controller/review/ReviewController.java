package com.bethefirst.lifeweb.controller.review;

import com.bethefirst.lifeweb.dto.review.reqeust.DeleteReviewDto;
import com.bethefirst.lifeweb.dto.review.reqeust.CreateReviewDto;
import com.bethefirst.lifeweb.dto.review.reqeust.ReviewSearchRequirements;
import com.bethefirst.lifeweb.dto.review.reqeust.UpdateReviewDto;
import com.bethefirst.lifeweb.dto.review.response.ReviewDto;
import com.bethefirst.lifeweb.service.review.interfaces.ReviewService;
import com.bethefirst.lifeweb.util.UrlUtil;
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
	@PostMapping
	@PreAuthorize("isAuthenticated() and (( #createReviewDto.memberId == principal.memberId ) or hasRole('ADMIN'))")
	public ResponseEntity<?> create(@RequestBody CreateReviewDto createReviewDto) {

		//URL 유효성 검사
		urlUtil.inspectionUrl(createReviewDto.getUrl());

		reviewService.createReview(createReviewDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/reviews"));
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 리뷰 삭제 */
	@DeleteMapping("/{reviewId}")
	@PreAuthorize("isAuthenticated() and (( #deleteReviewDto.memberId == principal.memberId ) or hasRole('ADMIN'))")
	public ResponseEntity<?> delete(@PathVariable Long reviewId,
									@RequestBody DeleteReviewDto deleteReviewDto){
		reviewService.deleteReview(reviewId);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/reviews"));
		return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
	}

	/** 리뷰 수정 */
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{reviewId}")
	@PreAuthorize("isAuthenticated() and (( #updateReviewDto.memberId == principal.memberId ) or hasRole('ADMIN'))")
	public ResponseEntity<?> update(@PathVariable Long reviewId,
									@RequestBody UpdateReviewDto updateReviewDto){

		//URL 유효성 검사
		urlUtil.inspectionUrl(updateReviewDto.getUrl());

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
