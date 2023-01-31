package com.bethefirst.lifeweb.service.review;

import com.bethefirst.lifeweb.dto.review.reqeust.ReviewCreateDto;
import com.bethefirst.lifeweb.entity.application.Application;
import com.bethefirst.lifeweb.entity.application.ApplicationStatus;
import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.review.Review;
import com.bethefirst.lifeweb.repository.application.ApplicationRepository;
import com.bethefirst.lifeweb.repository.campaign.CampaignRepository;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.repository.review.ReviewRepository;
import com.bethefirst.lifeweb.service.review.interfaces.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	private final ApplicationRepository applicationRepository;
	private final CampaignRepository campaignRepository;


	/** 리뷰 등록 */
	@Override
	public void createReview(ReviewCreateDto reviewCreateDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() ->
				new IllegalArgumentException("존재하지 않는 회원 입니다. " + memberId));
		Campaign campaign = campaignRepository.findById(reviewCreateDto.getCampaignId()).orElseThrow(()
				-> new IllegalArgumentException("존재하지 않는 캠페인 입니다 " + reviewCreateDto.getCampaignId()));

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
		Review review = new Review(member, campaign, reviewCreateDto.getReviewUrl());

		reviewRepository.save(review);
	}

	/** 리뷰 삭제 */
	@Override
	public void deleteReview(Long reviewId) {
		
	}
}
