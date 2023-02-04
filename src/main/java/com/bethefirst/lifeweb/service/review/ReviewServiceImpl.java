package com.bethefirst.lifeweb.service.review;

import com.bethefirst.lifeweb.dto.review.reqeust.CreateReviewDto;
import com.bethefirst.lifeweb.dto.review.reqeust.ReviewSearchRequirements;
import com.bethefirst.lifeweb.dto.review.reqeust.UpdateReviewDto;
import com.bethefirst.lifeweb.dto.review.response.ReviewDto;
import com.bethefirst.lifeweb.entity.application.Application;
import com.bethefirst.lifeweb.entity.application.ApplicationStatus;
import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.review.Review;
import com.bethefirst.lifeweb.repository.campaign.CampaignRepository;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.repository.review.ReviewRepository;
import com.bethefirst.lifeweb.service.review.interfaces.ReviewService;
import com.bethefirst.lifeweb.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final MemberRepository memberRepository;
	private final CampaignRepository campaignRepository;
	private final UrlUtil urlUtil;

	/** 리뷰 등록 */
	@Override
	public void createReview(CreateReviewDto createReviewDto, Long memberId) {

		Member member = memberRepository.findById(memberId).orElseThrow(() ->
				new IllegalArgumentException("존재하지 않는 회원 입니다. " + memberId));
		Campaign campaign = campaignRepository.findById(createReviewDto.getCampaignId()).orElseThrow(()
				-> new IllegalArgumentException("존재하지 않는 캠페인 입니다 " + createReviewDto.getCampaignId()));

		//URL 유효성 검사
		urlUtil.inspectionUrl(createReviewDto.getReviewUrl());

		//회원의 신청서 중 캠페인과 같은 신청서를 가져옵니다
		Application application = member.findApplication(campaign);

		//선정된 캠페인인 맞는지 확인합니다.
		if(application.getStatus() == ApplicationStatus.UNSELECT){
			throw new IllegalArgumentException("선정되지 않은 캠페인입니다.");
		}

		//오늘날짜가 등록시작일 이전인지 체크합니다
		if(campaign.getFilingStartDate().isBefore(LocalDate.now())){
			throw new IllegalArgumentException("아직 리뷰 등록 기간이 아닙니다.");
		}
		Review review = createReviewDto.createReview(member, campaign);

		reviewRepository.save(review);
	}

	/** 리뷰 삭제 */
	@Override
	public void deleteReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
				new IllegalArgumentException("존재하지 않는 리뷰입니다. " + reviewId));

		reviewRepository.delete(review);
	}

	/** 리뷰 수정 */
	@Override
	public void updateReview(UpdateReviewDto updateReviewDto, Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
				new IllegalArgumentException("존재하지 않는 리뷰입니다. " + reviewId));

		updateReviewDto.updateReview(review);
	}

	/** 검색조건에 따른 리뷰 전체 조회 */
	@Transactional(readOnly = true)
	@Override
	public Page<ReviewDto> getReviewList(ReviewSearchRequirements requirements, Pageable pageable) {
		return reviewRepository.findAllBySearchRequirements(requirements, pageable).map(ReviewDto::new);
	}


}
