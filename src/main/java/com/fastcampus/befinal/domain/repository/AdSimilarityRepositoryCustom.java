package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.QAdSimilarity;
import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.fastcampus.befinal.common.contant.SameAdConstant.ADVERTISEMENT_SIMILARITY_INFO_LIST_SIZE;

@Repository
@RequiredArgsConstructor
public class AdSimilarityRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final QAdSimilarity adSimilarity = QAdSimilarity.adSimilarity;

    public List<SameAdInfo.AdSimilarityInfo> findAdSimilarityInfoList(String inspectionAdvertisementId) {
        return queryFactory
            .select(Projections.constructor(SameAdInfo.AdSimilarityInfo.class,
                adSimilarity.adSimilarityPK.comparisonAd.id,
                adSimilarity.adSimilarityPK.comparisonAd.product,
                adSimilarity.adSimilarityPK.comparisonAd.advertiser,
                adSimilarity.adSimilarityPK.comparisonAd.adCategory.category,
                adSimilarity.adSimilarityPK.comparisonAd.postDateTime,
                adSimilarity.similarity,
                adSimilarity.sameSentenceCount
            ))
            .from(adSimilarity)
            .where(adSimilarity.adSimilarityPK.inspectionAd.id.eq(inspectionAdvertisementId))
            .orderBy(adSimilarity.similarity.desc())
            .limit(ADVERTISEMENT_SIMILARITY_INFO_LIST_SIZE)
            .fetch();
    }

    public Optional<SameAdInfo.FindSimilarityDetailInfo> findSimilarityDetailInfo(String inspectionAdvertisementId, String comparisonAdvertisementId) {
        return Optional.ofNullable(
            queryFactory.select(Projections.constructor(SameAdInfo.FindSimilarityDetailInfo.class,
                adSimilarity.adSimilarityPK.comparisonAd.adContent.content,
                adSimilarity.sameSentence
            ))
            .from(adSimilarity)
            .where(adSimilarity.adSimilarityPK.inspectionAd.id.eq(inspectionAdvertisementId)
                .and(adSimilarity.adSimilarityPK.comparisonAd.id.eq(comparisonAdvertisementId)))
            .fetchOne()
        );
    }
}
