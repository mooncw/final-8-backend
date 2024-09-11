package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.Advertisement;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.domain.info.IssueAdInfo;

import java.util.List;

public interface AdvertisementReader {
    DashboardInfo.AdCount findAdCount(String userId);
    List<DashboardInfo.DailyDone> findDailyDone(String userId);
    List<DashboardInfo.RecentDone> findRecentDone(String userId);
    IssueAdInfo.IssueAdDetailInfo findIssueAdDetail(String advertisementId);
    Advertisement findAdvertisementById(String advertisementId);
}
