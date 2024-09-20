package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.presentation.dto.DashboardDto;
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

    @Builder
    public record DashboardAdminDataInfo(
        DashboardDto.AdminTimeline adminTimeline,
        DashboardDto.AdminAdCount adCount,
        List<DashboardDto.DailyWork> dailyWorkList,
        List<DashboardDto.PersonalTask> personalTaskList
    ) {}

    @Builder
    public record AdminAdCountInfo(
        Integer remainingAd,
        Integer totalAd,
        Integer doneAd,
        Integer notDoneAd
    ) {}

    @Builder
    public record AdminTimeline(
        Integer notApprovedUser,
        Integer remainingAd
    ) {
        public static AdminTimeline of(Integer notApprovedUser, Integer remainingAd){
            return AdminTimeline.builder()
                .notApprovedUser(notApprovedUser)
                .remainingAd(remainingAd)
                .build();
        }
    }

    @Builder
    public record AdminAdCount(
        Integer totalAd,
        Integer doneAd,
        Integer notDoneAd
    ) {
        public static AdminAdCount from(AdminAdCountInfo info){
            return AdminAdCount.builder()
                .totalAd(info.totalAd())
                .doneAd(info.doneAd())
                .notDoneAd(info.notDoneAd())
                .build();
        }
    }

    @Builder
    public record DailyWork(
        LocalDate date,
        Integer avg
    ) {}

    @Builder
    public record PersonalTask(
        String userName,
        Integer doneAd,
        Integer totalAd
    ) {}
}
