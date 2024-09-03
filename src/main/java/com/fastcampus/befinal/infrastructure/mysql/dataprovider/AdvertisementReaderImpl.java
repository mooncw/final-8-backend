package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.domain.repository.AdvertisementRepositoryCustom;
import lombok.RequiredArgsConstructor;
import java.util.List;

@DataProvider
@RequiredArgsConstructor
public class AdvertisementReaderImpl implements AdvertisementReader {
    private final AdvertisementRepositoryCustom advertisementRepositoryCustom;

    @Override
    public DashboardInfo.AdCount findAdCount(String userId) {
        return advertisementRepositoryCustom.getAdCount(userId);
    }

    @Override
    public List<DashboardInfo.DailyDone> findDailyDone(String userId) {
        return advertisementRepositoryCustom.getDailyDoneList(userId);
    }

    @Override
    public List<DashboardInfo.RecentDone> findRecentDone(String userId) {
        return advertisementRepositoryCustom.getRecentDoneList(userId);
    }
}
