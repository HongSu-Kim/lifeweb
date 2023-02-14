package com.bethefirst.lifeweb.repository.application;

import com.bethefirst.lifeweb.dto.application.ApplicationSearchRequirements;
import com.bethefirst.lifeweb.entity.application.Application;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.bethefirst.lifeweb.entity.application.QApplication.application;
import static com.bethefirst.lifeweb.entity.campaign.QCampaign.campaign;
import static com.bethefirst.lifeweb.entity.campaign.QCampaignCategory.campaignCategory;
import static com.bethefirst.lifeweb.entity.campaign.QCampaignLocal.campaignLocal;
import static com.bethefirst.lifeweb.entity.campaign.QCampaignType.campaignType;
import static com.bethefirst.lifeweb.entity.campaign.QLocal.local;
import static com.bethefirst.lifeweb.entity.member.QMember.member;
import static com.bethefirst.lifeweb.entity.member.QSns.sns;

@RequiredArgsConstructor
public class ApplicationRepositoryQueryDslImpl implements ApplicationRepositoryQueryDsl {

	private final JPAQueryFactory queryFactory;

	/** 신청서 리스트 조회 */
	@Override
	public Page<Application> findAllBySearchRequirements(ApplicationSearchRequirements searchRequirements) {

		// content
		List<Application> content = queryFactory
				.select(application)
				.from(application)
				.join(application.member, member).fetchJoin()
				.join(application.campaign, campaign).fetchJoin()
				.join(campaign.campaignCategory, campaignCategory).fetchJoin()
				.join(campaign.campaignType, campaignType).fetchJoin()
				.join(campaign.sns, sns).fetchJoin()
				.leftJoin(campaign.campaignLocal, campaignLocal).fetchJoin()
				.leftJoin(campaignLocal.local, local).fetchJoin()
				.where(
						memberIdEq(searchRequirements.getMemberId()),
						campaignIdEq(searchRequirements.getCampaignId())
				)
				.offset(searchRequirements.getPageable().getOffset())
				.limit(searchRequirements.getPageable().getPageSize())
				.fetch();

		// size
		Long count = queryFactory
				.select(application.count())
				.from(application)
				.where(
						memberIdEq(searchRequirements.getMemberId()),
						campaignIdEq(searchRequirements.getCampaignId())
				)
				.fetchOne();

		return new PageImpl<>(content, searchRequirements.getPageable(), count);
	}

	/** 맴버 */
	private BooleanExpression memberIdEq(Long memberId) {
		return memberId == null ? null : application.member.id.eq(memberId);
	}

	/** 캠페인 */
	private BooleanExpression campaignIdEq(Long campaignId) {
		return campaignId == null ? null : application.campaign.id.eq(campaignId);
	}
	
}
