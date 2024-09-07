package com.fastcampus.befinal.domain.info;

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
            String adCategory,
            String product,
            String advertiser,
            Boolean state,
            Boolean issue
    ) {}

    @Builder
    public record TaskListInfo(
            Long totalElements,
            CursorInfo currentCursor,
            List<AdvertisementListInfo> advertisementList
    ){
        public static TaskListInfo of(Long totalElements, CursorInfo cursorInfo, List<AdvertisementListInfo> contents) {
            return TaskListInfo.builder()
                    .totalElements(totalElements)
                    .currentCursor(cursorInfo)
                    .advertisementList(contents)
                    .build();
        }
    }
}
