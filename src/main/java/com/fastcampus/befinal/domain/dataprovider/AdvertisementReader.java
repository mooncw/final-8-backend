package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.SameAdCommand;
import com.fastcampus.befinal.domain.entity.Advertisement;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.info.*;

import java.util.List;

public interface AdvertisementReader {
    // 사용자 대시보드
    DashboardInfo.AdCount findAdCount(String userId);
    List<DashboardInfo.DailyDone> findDailyDone(String userId);
    List<DashboardInfo.RecentDone> findRecentDone(String userId);
    // 관리자 대시보드
    DashboardInfo.AdminAdCountInfo findAdminAdCountInfo();
    List<DashboardInfo.TodayWork> findTodayWorkList();
    List<DashboardInfo.DailyAvgDone> findDailyAvgDoneList();
    List<DashboardInfo.PersonalTask> findPersonalTaskList();
    // 지적광고
    ScrollPagination<TaskInfo.CursorInfo, TaskInfo.IssueAdvertisementListInfo> findIssueAdList(TaskCommand.FilterConditionRequest command);
    IssueAdInfo.IssueAdDetailInfo findIssueAdDetail(String advertisementId);
    Advertisement findAdvertisementById(String advertisementId);
    // 관리자
    ScrollPagination<String, AdminInfo.UnassignedAdInfo> findUnassignedAdScroll(String cursorId);
    Long countUnassigned();
    List<AdminInfo.UnassignedAdIdInfo> findAllUnassignedAdId(Long amount);
    SameAdInfo.InspectionAdInfo findInspectionAdInfo(String advertisementId);
    ScrollPagination<String, SameAdInfo.SameAdvertisementListInfo> findSameAdList(SameAdCommand.SameAdFilterConditionRequest command);
}
