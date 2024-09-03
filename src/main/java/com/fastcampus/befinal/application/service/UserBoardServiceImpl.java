package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
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
}
