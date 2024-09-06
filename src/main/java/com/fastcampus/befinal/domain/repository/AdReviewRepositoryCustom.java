package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.QAdReview;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private static final QAdReview adReview = QAdReview.adReview;

    public List<IssueAdInfo.IssueAdReviewInfo> getReviewList(String advertisementId){
        return queryFactory
            .select(Projections.constructor(IssueAdInfo.IssueAdReviewInfo.class,
                adReview.adProvision.article,
                adReview.adProvision.content,
                adReview.sentence,
                adReview.opinion))
            .from(adReview)
            .where(isAdIdEq(advertisementId))
            .fetch();

    }

    private BooleanExpression isAdIdEq(String advertisementId) { return adReview.advertisement.id.eq(advertisementId); }
}
