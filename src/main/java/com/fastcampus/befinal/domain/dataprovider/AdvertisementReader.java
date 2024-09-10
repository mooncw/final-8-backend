package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.domain.info.IssueAdInfo;

import java.util.List;

public interface AdvertisementReader {
    DashboardInfo.AdCount findAdCount(String userId);
    List<DashboardInfo.DailyDone> findDailyDone(String userId);
    List<DashboardInfo.RecentDone> findRecentDone(String userId);
    IssueAdInfo.IssueAdDetailInfo findIssueAdDetail(String advertisementId);
    ScrollPagination<String, AdminInfo.UnassignedAdInfo> findUnassignedAdScroll(String cursorId);
}
