package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.dataprovider.UserManagementReader;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.domain.service.UserBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBoardServiceImpl implements UserBoardService {
    private final AdvertisementReader advertisementReader;
    private final UserManagementReader userManagementReader;

    @Override
    @Transactional(readOnly = true)
    public DashboardInfo.DashboardDataInfo loadUserDashboardData(String userId) {
        DashboardInfo.AdCount adCount = advertisementReader.findAdCount(userId);
        List<DashboardInfo.DailyDone> dailyDoneList = advertisementReader.findDailyDone(userId);
        List<DashboardInfo.RecentDone> recentDoneList = advertisementReader.findRecentDone(userId);

        return DashboardInfo.DashboardDataInfo.builder()
                .adCount(adCount)
                .dailyDoneList(dailyDoneList)
                .recentDoneList(recentDoneList)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardInfo.DashboardAdminDataInfo loadAdminDashboardData() {
        Integer notApproveUser = userManagementReader.findNotApproveUserCount();
        DashboardInfo.AdminAdCountInfo adCountInfo = advertisementReader.findAdminAdCountInfo();

        DashboardInfo.AdminTimeline adminTimeline = DashboardInfo.AdminTimeline.of(notApproveUser, adCountInfo.remainingAd());
        DashboardInfo.AdminAdCount adminAdCount = DashboardInfo.AdminAdCount.from(adCountInfo);

        List<DashboardInfo.TodayWork> todayWorkList = advertisementReader.findTodayWorkList();
        List<DashboardInfo.DailyAvgDone> dailyAvgDoneList = advertisementReader.findDailyAvgDoneList();
        List<DashboardInfo.PersonalTask> personalTaskList = advertisementReader.findPersonalTaskList();

        return DashboardInfo.DashboardAdminDataInfo.of(adminTimeline, adminAdCount, todayWorkList, dailyAvgDoneList, personalTaskList);
    }
}
