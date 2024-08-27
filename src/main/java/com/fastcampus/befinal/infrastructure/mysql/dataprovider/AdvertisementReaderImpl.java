package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.entity.QAdvertisement;
import com.fastcampus.befinal.domain.info.DashBoardInfo;
import com.fastcampus.befinal.domain.repository.AdvertisementRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@DataProvider
@RequiredArgsConstructor
public class AdvertisementReaderImpl implements AdvertisementReader {
    private final AdvertisementRepositoryCustom advertisementRepositoryCustom;

    @Override
    public DashBoardInfo.DashBoardDataInfo findDashBoardData(String userId) {
        LocalDateTime today = LocalDateTime.now();
        DashBoardInfo.AdCount adCount = advertisementRepositoryCustom.getAdCount(userId, today);
        List<DashBoardInfo.DailyDone> dailyDoneList = advertisementRepositoryCustom.getDailyDoneList(userId, today);
        List<DashBoardInfo.RecentDone> recentDoneList = advertisementRepositoryCustom.getRecentDoneList(userId, today);

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
