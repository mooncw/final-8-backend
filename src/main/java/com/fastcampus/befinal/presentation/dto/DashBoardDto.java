package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.domain.info.DashBoardInfo;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DashBoardDto {
    @Builder
    public record DashBoardDataResponse(
            int totalAd,
            int myAd,
            int totalDoneAd,
            int myDoneAd,
            int totalNotDoneAd,
            int myNotDoneAd,
            List<DashBoardInfo.DailyDone> dailyDoneList,
            List<DashBoardInfo.RecentDone> recentDoneList
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
