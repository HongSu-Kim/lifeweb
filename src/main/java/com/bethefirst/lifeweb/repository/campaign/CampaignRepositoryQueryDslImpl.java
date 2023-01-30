package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.dto.campaign.CampaignSearchRequirements;
import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.bethefirst.lifeweb.entity.campaign.QCampaign.campaign;
import static com.bethefirst.lifeweb.entity.campaign.QCampaignCategory.campaignCategory;
import static com.bethefirst.lifeweb.entity.campaign.QCampaignType.campaignType;

@RequiredArgsConstructor
public class CampaignRepositoryQueryDslImpl implements CampaignRepositoryQueryDsl {

	private final JPAQueryFactory queryFactory;

	/** 캠페인 리스트 조회 */
	@Override
	public Page<Campaign> findAllBySearchRequirements(CampaignSearchRequirements searchRequirements) {

		// content
		List<Campaign> content = queryFactory
				.select(campaign)
				.from(campaign)
				.join(campaign.campaignCategory, campaignCategory).fetchJoin()
				.join(campaign.campaignType, campaignType).fetchJoin()
				.where(
						categoryIdEq(searchRequirements.getCategoryId()),
						typeIdEq(searchRequirements.getTypeId()),
						snsIdListIn(searchRequirements.getSnsIdList()),
						specialEq(searchRequirements.getSpecial()),
						statusEq(searchRequirements.getStatus()),
						localIdEq(searchRequirements.getLocalId())
				)
				.orderBy(orderBy(searchRequirements.getPageable()))
				.offset(searchRequirements.getPageable().getOffset())
				.limit(searchRequirements.getPageable().getPageSize())
				.fetch();

		// size
		Long count = queryFactory
				.select(campaign.count())
				.from(campaign)
				.where(
						categoryIdEq(searchRequirements.getCategoryId()),
						typeIdEq(searchRequirements.getTypeId()),
						snsIdListIn(searchRequirements.getSnsIdList()),
						specialEq(searchRequirements.getSpecial()),
						statusEq(searchRequirements.getStatus()),
						localIdEq(searchRequirements.getLocalId())
				)
				.fetchOne();

		return new PageImpl<>(content, searchRequirements.getPageable(), count);
	}

	/** 정렬 설정 */
	private OrderSpecifier<?> orderBy(Pageable pageable) {

		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<Campaign> orderByExpression = new PathBuilder<>(Campaign.class, "campaign");
			return new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, orderByExpression.get(o.getProperty()));
		}

		return null;
	}

	/** 카테고리 */
	private BooleanExpression categoryIdEq(Long categoryId) {
		return categoryId == null ? null : campaign.campaignCategory.id.eq(categoryId);
	}

	/** 타입 */
	private BooleanExpression typeIdEq(Long typeId) {
		return typeId == null ? null : campaign.campaignType.id.eq(typeId);
	}

	/** SNS */
	private BooleanExpression snsIdListIn(List<Long> snsIdList) {
		return snsIdList == null ? null : campaign.sns.id.in(snsIdList);
	}

	/** 스페셜 */
	private BooleanExpression specialEq(Boolean special) {
		return special == null ? null : campaign.special.eq(special);
	}

	/** 상태 */
	private BooleanExpression statusEq(CampaignStatus status) {
		return status == null ? null : campaign.status.eq(status);
	}

	/** 지역 */
	private BooleanExpression localIdEq(Long localId) {
		return localId == null ? null : campaign.campaignLocal.local.id.eq(localId);
	}

}
