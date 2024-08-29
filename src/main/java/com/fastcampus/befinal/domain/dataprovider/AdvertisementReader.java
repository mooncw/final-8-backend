package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.DashBoardInfo;

import java.util.List;

public interface AdvertisementReader {
    DashBoardInfo.AdCount findAdCount(String userId);
    List<DashBoardInfo.DailyDone> findDailyDone(String userId);
    List<DashBoardInfo.RecentDone> findRecentDone(String userId);
}
