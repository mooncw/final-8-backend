package com.fastcampus.befinal.presentation.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.ZonedDateTime;
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
        String adTaskDateTime
    ) {}

    @Builder
    public record DashboardAdminDataResponse(
        AdminTimeline adminTimeline,
        AdminAdCount adCount,
        List<TodayWork> todayWorkList,
        List<PersonalTask> personalTaskList
    ) {}

    @Builder
    public record AdminTimeline(
        Integer notApprovedUser,
        Integer remainingAd
    ) {}

    @Builder
    public record AdminAdCount(
        Integer totalAd,
        Integer doneAd,
        Integer notDoneAd
    ) {}

    @Builder
    public record TodayWork(
        LocalDate date,
        Double avg
    ) {}

    @Builder
    public record PersonalTask(
        String userName,
        Integer doneAd,
        Integer totalAd
    ) {}
}
