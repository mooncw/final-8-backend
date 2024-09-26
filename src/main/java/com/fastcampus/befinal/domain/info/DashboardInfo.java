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
    public record DailyDoneList(
        List<DailyDone> dailyDoneList
    ) {
        public static DailyDoneList from(List<DailyDone> dailyDoneList) {
            return DailyDoneList.builder()
                .dailyDoneList(dailyDoneList)
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
        AdminTimeline adminTimeline,
        AdminAdCount adCount,
        List<TodayWork> todayWorkList,
        List<DailyAvgDone> dailyAvgDoneList,
        List<PersonalTask> personalTaskList
    ) {
        public static DashboardAdminDataInfo of(AdminTimeline adminTimeline, AdminAdCount adCount, List<TodayWork> todayWorkList,
                                                List<DailyAvgDone> dailyAvgDoneList ,List<PersonalTask> personalTaskList){
            return DashboardAdminDataInfo.builder()
                .adminTimeline(adminTimeline)
                .adCount(adCount)
                .todayWorkList(todayWorkList)
                .dailyAvgDoneList(dailyAvgDoneList)
                .personalTaskList(personalTaskList)
                .build();
        }
    }

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
    public record TodayWork(
        LocalDate date,
        Integer doneAd
    ) {
        public static TodayWork of(LocalDate date, Integer doneAd){
            return TodayWork.builder()
                .date(date)
                .doneAd(doneAd)
                .build();
        }
    }

    @Builder
    public record DailyAvgDone(
        LocalDate date,
        Double avgDoneAd
    ) {
        public static DailyAvgDone of(LocalDate date, Double avgDoneAd){
            return DailyAvgDone.builder()
                .date(date)
                .avgDoneAd(avgDoneAd)
                .build();
        }
    }

    @Builder
    public record PersonalTask(
        String userName,
        Integer doneAd,
        Integer totalAd
    ) {
        public static PersonalTask of(String userName, Integer doneAd, Integer totalAd){
            return PersonalTask.builder()
                .userName(userName)
                .doneAd(doneAd)
                .totalAd(totalAd)
                .build();
        }
    }

    @Builder
    public record UserNameListInfo(
        List<UserName> userNameList
    ) {
        public static UserNameListInfo from(List<UserName> userNameList) {
            return UserNameListInfo.builder()
                .userNameList(userNameList)
                .build();
        }
    }

    @Builder
    public record UserName(
        Long id,
        String userName
    ) {}
}
