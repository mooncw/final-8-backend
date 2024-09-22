package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.common.util.ScrollPagination;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SameAdInfo {
    @Builder
    public record SameAdvertisementListInfo(
        String adId,
        String media,
        String category,
        String product,
        String advertiser,
        Boolean same
    ) {}

    @Builder
    public record SameTaskListInfo(
        Long totalElements,
        String cursorId,
        List<SameAdvertisementListInfo> sameAdvertisementList
    ) {
        public static SameTaskListInfo of(ScrollPagination<String, SameAdvertisementListInfo> scrollPagination) {
            return SameTaskListInfo.builder()
                .totalElements(scrollPagination.totalElements())
                .cursorId(scrollPagination.currentCursorId())
                .sameAdvertisementList(scrollPagination.contents())
                .build();
        }
    }

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

    @Builder
    public record FindSimilarityDetailInfo(
        String content,
        String sameSentence
    ) {}
}
