package com.fastcampus.befinal.presentation.dto;

import lombok.Builder;

import java.util.List;

public class SameAdDto {
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
    public record FindSimilarityListResponse(
        InspectionAdInfo inspectionAdInfo,
        List<AdSimilarityInfo> adSimilarityInfoList
    ) {}
}
