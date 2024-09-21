package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.Advertisement;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.info.*;

import java.util.List;

public interface AdvertisementReader {
    DashboardInfo.AdCount findAdCount(String userId);
    List<DashboardInfo.DailyDone> findDailyDone(String userId);
    List<DashboardInfo.RecentDone> findRecentDone(String userId);
    ScrollPagination<TaskInfo.CursorInfo, TaskInfo.IssueAdvertisementListInfo> findIssueAdList(TaskCommand.FilterConditionRequest command);
    IssueAdInfo.IssueAdDetailInfo findIssueAdDetail(String advertisementId);
    Advertisement findAdvertisementById(String advertisementId);
    ScrollPagination<String, AdminInfo.UnassignedAdInfo> findUnassignedAdScroll(String cursorId);
    Long countUnassigned();
    List<AdminInfo.UnassignedAdIdInfo> findAllUnassignedAdId(Long amount);
    SameAdInfo.InspectionAdInfo findInspectionAdInfo(String advertisementId);
}
