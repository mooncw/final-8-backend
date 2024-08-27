package com.fastcampus.befinal.domain.info;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DashBoardInfo {
    @Builder
    public record DashBoardDataInfo(
            int totalAd,
            int myAd,
            int totalDoneAd,
            int myDoneAd,
            int totalNotDoneAd,
            int myNotDoneAd,
            List<DailyDone> dailyDoneList,
            List<RecentDone> recentDoneList
    ) {}

    @Builder
    public record AdCount(
            int totalAd,
            int myAd,
            int totalDoneAd,
            int myDoneAd,
            int totalNotDoneAd,
            int myNotDoneAd
    ) {}

    @Builder
    public record DailyDone(
            LocalDate date,
            int dailyMyDoneAd
    ) {}

    @Builder
    public record RecentDone(
            String adId,
            String adName,
            LocalDateTime adModifiedDate
    ) {}
}
