package com.bethefirst.lifeweb.dto.review;

import com.bethefirst.lifeweb.entity.review.Review;
import com.bethefirst.lifeweb.entity.review.ReviewImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {

	private Long id;//리뷰ID

//	private MemberDto memberDto;//회원
//	private CampaignDto campaignDto;//캠페인

	private String reviewUrl;//리뷰주소
	private LocalDateTime created;//등록일

	private List<String> imageList;//리뷰이미지

	public ReviewDto(Review review) {

		id = review.getId();

		reviewUrl = review.getReviewUrl();
		created = review.getCreated();

		imageList = review.getReviewImageList()
				.stream().map(ReviewImage::getFileName)
				.collect(Collectors.toList());

	}

}
