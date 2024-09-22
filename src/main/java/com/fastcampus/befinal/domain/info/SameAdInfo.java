package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.common.util.ScrollPagination;
import lombok.Builder;

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
}
