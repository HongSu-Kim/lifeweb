package com.bethefirst.lifeweb.repository.review;

import com.bethefirst.lifeweb.dto.review.reqeust.ReviewSearchRequirements;
import com.bethefirst.lifeweb.entity.review.Review;
import com.bethefirst.lifeweb.util.QueryDsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.bethefirst.lifeweb.entity.campaign.QCampaign.campaign;
import static com.bethefirst.lifeweb.entity.campaign.QCampaignCategory.campaignCategory;
import static com.bethefirst.lifeweb.entity.campaign.QCampaignLocal.campaignLocal;
import static com.bethefirst.lifeweb.entity.campaign.QCampaignType.campaignType;
import static com.bethefirst.lifeweb.entity.campaign.QLocal.local;
import static com.bethefirst.lifeweb.entity.member.QMember.member;
import static com.bethefirst.lifeweb.entity.member.QSns.sns;
import static com.bethefirst.lifeweb.entity.review.QReview.review;
import static org.springframework.util.CollectionUtils.isEmpty;

public class ReviewRepositoryQueryDslImpl extends QueryDsl4RepositorySupport implements ReviewRepositoryQueryDsl{

    public ReviewRepositoryQueryDslImpl(){
        super(Review.class);
    }


    /** 검색조건에 따른 리뷰들을 조회합니다. */
    @Override
    public Page<Review> findAllBySearchRequirements(ReviewSearchRequirements requirements, Pageable pageable) {
        return applyPagination(pageable, query -> query
                .selectFrom(review)
                        .join(review.member, member).fetchJoin()
                        .join(review.campaign, campaign).fetchJoin()
                        .join(campaign.campaignCategory, campaignCategory).fetchJoin()
                        .join(campaign.campaignType, campaignType).fetchJoin()
                        .join(campaign.sns, sns).fetchJoin()
                        .leftJoin(campaign.campaignLocal, campaignLocal).fetchJoin()
                        .leftJoin(campaignLocal.local, local).fetchJoin()
                .where(
                        memberIdEq(requirements.getMemberId()),
                        campaignIdEq(requirements.getCampaignId()),
                        snsIdListIn(requirements.getSnsId())
                ),
                countQuery -> countQuery
                .select(review.count())
                .from(review)
                .where(
                        memberIdEq(requirements.getMemberId()),
                        campaignIdEq(requirements.getCampaignId()),
                        snsIdListIn(requirements.getSnsId())
                )
        );
    }

    /** SNS ID 검색조건 */
    private BooleanExpression snsIdListIn(List<Long> snsIdList) {
        return isEmpty(snsIdList) ? null : review.campaign.sns.id.in(snsIdList);
    }

    /** 캠페인 ID 검색조건 */
    private BooleanExpression campaignIdEq(Long campaignId) {
        return campaignId == null ?  null : review.campaign.id.eq(campaignId);
    }

    /** 회원 ID 검색조건 */
    private BooleanExpression memberIdEq(Long memberId) {
        return memberId == null ? null : review.member.id.eq(memberId);
    }



}
