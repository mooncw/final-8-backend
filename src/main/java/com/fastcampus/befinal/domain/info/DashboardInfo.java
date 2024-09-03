package com.fastcampus.befinal.domain.info;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DashboardInfo {
    @Builder
    public record DashboardDataInfo(
            AdCount adCount,
            List<DailyDone> dailyDoneList,
            List<RecentDone> recentDoneList
    ) {
        public static DashboardDataInfo of(AdCount adCount, List<DailyDone> dailyDoneList,
                                           List<RecentDone> recentDoneList) {
            return DashboardDataInfo.builder()
                    .adCount(adCount)
                    .dailyDoneList(dailyDoneList)
                    .recentDoneList(recentDoneList)
                    .build();
        }
    }

    @Builder
    public record AdCount(
            Integer totalAd,
            Integer myAd,
            Integer totalDoneAd,
            Integer myDoneAd,
            Integer totalNotDoneAd,
            Integer myNotDoneAd
    ) {
        public static AdCount of( Integer totalAd, Integer myAd, Integer totalDoneAd,
                                  Integer myDoneAd, Integer totalNotDoneAd, Integer myNotDoneAd) {
            return AdCount.builder()
                    .totalAd(totalAd)
                    .myAd(myAd)
                    .totalDoneAd(totalDoneAd)
                    .myDoneAd(myDoneAd)
                    .totalNotDoneAd(totalNotDoneAd)
                    .myNotDoneAd(myNotDoneAd)
                    .build();
        }
    }

    @Builder
    public record DailyDone(
            LocalDate date,
            Integer dailyMyDoneAd
    ) {
        public static DailyDone of(LocalDate date, Integer dailyMyDoneAd) {
            return DailyDone.builder()
                    .date(date)
                    .dailyMyDoneAd(dailyMyDoneAd)
                    .build();
        }
    }

    @Builder
    public record RecentDone(
            String adId,
            String adName,
            LocalDateTime adTaskDateTime
    ) {}
}
