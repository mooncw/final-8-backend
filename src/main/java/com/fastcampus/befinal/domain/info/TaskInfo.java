package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.common.util.ScrollPagination;
import lombok.Builder;

import java.util.List;

public class TaskInfo {

    @Builder
    public record CursorInfo(
            Boolean cursorState,
            String cursorId
    ) {}

    @Builder
    public record TaskResponse (
            AdCountInfo adCount,
            TaskListInfo taskList
    ) {
        public static TaskResponse of(AdCountInfo adCount, TaskListInfo taskList) {
            return TaskResponse.builder()
                    .adCount(adCount)
                    .taskList(taskList)
                    .build();
        }
    }

    @Builder
    public record AdCountInfo (
            Integer myTotalAd,
            Integer myDoneAd,
            Integer myNotDoneAd
    ) {}

    @Builder
    public record AdvertisementListInfo (
            String adId,
            String media,
            String category,
            String product,
            String advertiser,
            Boolean state,
            Boolean issue
    ) {}

    @Builder
    public record TaskListInfo(
            Long totalElements,
            CursorInfo cursorInfo,
            List<AdvertisementListInfo> advertisementList
    ){
        public static TaskListInfo of(ScrollPagination<CursorInfo, AdvertisementListInfo> scrollPagination) {
            return TaskListInfo.builder()
                    .totalElements(scrollPagination.totalElements())
                    .cursorInfo(scrollPagination.currentCursorId())
                    .advertisementList(scrollPagination.contents())
                    .build();
        }
    }

    @Builder
    public record IssueAdvertisementListInfo (
        String adId,
        String media,
        String category,
        String product,
        String advertiser,
        Boolean state,
        Boolean issue,
        String assigneeName
    ) {}

    @Builder
    public record IssueAdListInfo(
        Long totalElements,
        CursorInfo cursorInfo,
        List<IssueAdvertisementListInfo> advertisementList
    ){
        public static IssueAdListInfo of(ScrollPagination<CursorInfo, IssueAdvertisementListInfo> scrollPagination) {
            return IssueAdListInfo.builder()
                .totalElements(scrollPagination.totalElements())
                .cursorInfo(scrollPagination.currentCursorId())
                .advertisementList(scrollPagination.contents())
                .build();
        }
    }
}
