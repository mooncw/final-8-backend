package com.fastcampus.befinal.domain.info;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SameAdInfo {
    @Builder
    public record InspectionAdInfo(
        String id,
        String product,
        String advertiser,
        String category,
        LocalDateTime postDateTime,
        String content
    ) {}

    @Builder
    public record AdSimilarityInfo(
        String id,
        String product,
        String advertiser,
        String category,
        LocalDateTime postDateTime,
        BigDecimal similarity,
        Integer sameSentenceCount
    ) {}

    @Builder
    public record FindSimilarityListInfo(
        InspectionAdInfo inspectionAdInfo,
        List<AdSimilarityInfo> adSimilarityInfoList
    ) {
        public static FindSimilarityListInfo of(InspectionAdInfo inspectionAdInfo, List<AdSimilarityInfo> adSimilarityInfoList) {
            return FindSimilarityListInfo.builder()
                .inspectionAdInfo(inspectionAdInfo)
                .adSimilarityInfoList(adSimilarityInfoList)
                .build();
        }
    }
}
