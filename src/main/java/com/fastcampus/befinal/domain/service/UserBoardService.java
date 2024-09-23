package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.info.DashboardInfo;

public interface UserBoardService {
    DashboardInfo.DashboardDataInfo loadUserDashboardData(String userId);
    DashboardInfo.DashboardAdminDataInfo loadAdminDashboardData();
    DashboardInfo.UserNameListInfo findUserNameList();
    DashboardInfo.DailyDoneList findDailyDoneListByUserId(String userId);
}
