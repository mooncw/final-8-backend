package com.fastcampus.befinal.presentation.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DashboardDto {
    @Builder
    public record DashboardDataResponse(
            AdCount adCount,
            List<DailyDone> dailyDoneList,
            List<RecentDone> recentDoneList
    ) {}

    @Builder
    public record AdCount(
            Integer totalAd,
            Integer myAd,
            Integer totalDoneAd,
            Integer myDoneAd,
            Integer totalNotDoneAd,
            Integer myNotDoneAd
    ) {}

    @Builder
    public record DailyDone(
            LocalDate date,
            Integer dailyMyDoneAd
    ) {}

    @Builder
    public record RecentDone(
            String adId,
            String adName,
            LocalDateTime adTaskDateTime
    ) {}
}
