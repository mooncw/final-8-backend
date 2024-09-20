package com.fastcampus.befinal.domain.info;

import lombok.Builder;

import java.util.List;

public class SameAdInfo {
    @Builder
    public record InspectionAdInfo(
        String id,
        String product,
        String advertiser,
        String category,
        String postDate,
        String content
    ) {}

    @Builder
    public record AdSimilarityInfo(
        String id,
        String product,
        String advertiser,
        String category,
        String postDate,
        Integer similarityPercent,
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
