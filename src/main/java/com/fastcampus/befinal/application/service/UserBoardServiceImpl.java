package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.DashBoardInfo;
import com.fastcampus.befinal.domain.service.UserBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBoardServiceImpl implements UserBoardService {
    private final AdvertisementReader advertisementReader;

    @Override
    public DashBoardInfo.DashBoardDataInfo loadUserDashBoardData(String userId) {
        DashBoardInfo.AdCount adCount = advertisementReader.findAdCount(userId);
        List<DashBoardInfo.DailyDone> dailyDoneList = advertisementReader.findDailyDone(userId);
        List<DashBoardInfo.RecentDone> recentDoneList = advertisementReader.findRecentDone(userId);

        return DashBoardInfo.DashBoardDataInfo.builder()
                .totalAd(adCount.totalAd())
                .myAd(adCount.myAd())
                .totalDoneAd(adCount.totalDoneAd())
                .myDoneAd(adCount.myDoneAd())
                .totalNotDoneAd(adCount.totalNotDoneAd())
                .myNotDoneAd(adCount.myNotDoneAd())
                .dailyDoneList(dailyDoneList)
                .recentDoneList(recentDoneList)
                .build();
    }
}
