package com.fastcampus.befinal.domain.info;

import lombok.Builder;

import java.util.List;

public class TaskInfo {

    @Builder
    public record CursorInfo(
            Boolean cursorState,
            String cursorAdId
    ) {}

    @Builder
    public record MyTaskInfo (
            MyAdCountInfo myAdCount,
            MyTaskListResponse myAdvertisement
    ) {
        public static MyTaskInfo of(MyAdCountInfo adCount, MyTaskListResponse myAdvertisement) {
            return MyTaskInfo.builder()
                    .myAdCount(adCount)
                    .myAdvertisement(myAdvertisement)
                    .build();
        }
    }

    @Builder
    public record MyAdCountInfo (
            Integer myTotalAd,
            Integer myDoneAd,
            Integer myNotDoneAd
    ) {}

    @Builder
    public record MyAdvertisementInfo (
            String adId,
            String media,
            String adCategory,
            String product,
            String advertiser,
            Boolean state,
            Boolean issue
    ) {}

    @Builder
    public record MyTaskListResponse(
            Long totalElements,
            CursorInfo currentCursorInfo,
            List<TaskInfo.MyAdvertisementInfo> advertisementList
    ){
        public static MyTaskListResponse of(Long totalElements, CursorInfo cursorInfo, List<MyAdvertisementInfo> contents) {
            return MyTaskListResponse.builder()
                    .totalElements(totalElements)
                    .currentCursorInfo(cursorInfo)
                    .advertisementList(contents)
                    .build();
        }
    }
}
