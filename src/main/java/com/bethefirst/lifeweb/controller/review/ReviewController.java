package com.bethefirst.lifeweb.controller.review;

import com.bethefirst.lifeweb.dto.review.reqeust.ReviewCreateDto;
import com.bethefirst.lifeweb.exception.UnauthorizedException;
import com.bethefirst.lifeweb.service.review.interfaces.ReviewService;
import com.bethefirst.lifeweb.util.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Controller
@RequestMapping("/review")
@Slf4j
public class ReviewController {

	private final ReviewService reviewService;

	/** 리뷰 등록 */
	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public void create(@Valid @RequestBody ReviewCreateDto reviewCreateDto){
		Long currentMemberId = SecurityUtil.getCurrentMemberId().orElseThrow(()
				-> new UnauthorizedException("Security Context에 인증 정보가 없습니다."));

		reviewService.createReview(reviewCreateDto, currentMemberId);
	}
}
