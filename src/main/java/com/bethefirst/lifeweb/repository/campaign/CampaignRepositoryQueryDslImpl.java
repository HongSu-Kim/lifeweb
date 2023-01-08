package com.bethefirst.lifeweb.repository.campaign;

import com.bethefirst.lifeweb.dto.campaign.SearchRequirements;
import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.bethefirst.lifeweb.entity.campaign.QCampaign.campaign;

public class CampaignRepositoryQueryDslImpl implements CampaignRepositoryQueryDsl {

	private final JPAQueryFactory queryFactory;

	public CampaignRepositoryQueryDslImpl(EntityManager entityManager) {
		queryFactory = new JPAQueryFactory(entityManager);
	}

	public Page<Campaign> findAllBySearchRequirements(SearchRequirements searchRequirements) {

		List<Campaign> content = queryFactory
				.select(campaign)
				.from(campaign)
				.where(
						categoryNameEq(searchRequirements.getCategoryName()),
						typeNameEq(searchRequirements.getTypeName()),
						specialEq(searchRequirements.getSpecial())
//						statusEq(searchRequirements.getStatus())
				)
				.orderBy()
				.offset(searchRequirements.getPageable().getOffset())
				.limit(searchRequirements.getPageable().getPageSize())
				.fetch();

		Long count = queryFactory
				.select(campaign.count())
				.from(campaign)
				.where(

				)
				.fetchOne();

		return new PageImpl<>(content, searchRequirements.getPageable(), count);
	}

	private OrderSpecifier<?> orderBy(Pageable pageable) {

		for (Sort.Order o : pageable.getSort()) {
			PathBuilder<Campaign> orderByExpression = new PathBuilder<>(Campaign.class, "campaign");
			return new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, orderByExpression.get(o.getProperty()));
		}

		return null;
	}

	private BooleanExpression categoryNameEq(String categoryName) {
		return categoryName == null ? null : campaign.campaignCategory.name.eq(categoryName);
	}

	private BooleanExpression typeNameEq(String typeName) {
		return typeName == null ? null : campaign.campaignType.name.eq(typeName);
	}

	private BooleanExpression specialEq(Boolean special) {
		return special == null ? null : campaign.special.eq(special);
	}

//	private BooleanExpression statusEq(CampaignStatus status) {
//		return status == null ? null : campaign.status.eq();
//	}

	private BooleanExpression localNameEq(String localName) {
		return localName == null ? null : campaign.campaignLocal.local.name.eq(localName);
	}

//	private BooleanExpression snsNameListIn(String snsNameList) {
//		return snsNameList == null ? null : campaign.campaignSnsList.eq(snsNameList);
//	}


}
