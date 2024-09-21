package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.dataprovider.UserManagementReader;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName("UserBoardService 테스트")
@ExtendWith(MockitoExtension.class)
class UserBoardServiceImplTest {
    @InjectMocks
    private UserBoardServiceImpl userBoardService;

    @Mock
    private AdvertisementReader advertisementReader;

    @Mock
    private UserManagementReader userManagementReader;

    @Test
    @DisplayName("대시보드 성공 테스트")
    void loadDashBoardTest() {
        // given
        String userId = "testID";

        DashboardInfo.AdCount adCount = DashboardInfo.AdCount.of(1, 1, 1,
                1, 0, 0);
        List<DashboardInfo.DailyDone> dailyDoneList = new ArrayList<>();
        List<DashboardInfo.RecentDone> recentDoneList = new ArrayList<>();

        doReturn(adCount)
                .when(advertisementReader)
                .findAdCount(userId);

        doReturn(dailyDoneList)
                .when(advertisementReader)
                .findDailyDone(userId);

        doReturn(recentDoneList)
                .when(advertisementReader)
                .findRecentDone(userId);

        // when
        DashboardInfo.DashboardDataInfo result = userBoardService.loadUserDashboardData(userId);

        // then
        assertNotNull(result);
        assertEquals(adCount.totalAd(), result.adCount().totalAd());
        assertEquals(adCount.myAd(), result.adCount().myAd());
        assertEquals(adCount.totalDoneAd(), result.adCount().totalDoneAd());
        assertEquals(adCount.myDoneAd(), result.adCount().myDoneAd());
        assertEquals(adCount.totalNotDoneAd(), result.adCount().totalNotDoneAd());
        assertEquals(adCount.myNotDoneAd(), result.adCount().myNotDoneAd());
        assertEquals(dailyDoneList, result.dailyDoneList());
        assertEquals(recentDoneList, result.recentDoneList());

        verify(advertisementReader, times(1)).findAdCount(userId);
        verify(advertisementReader, times(1)).findDailyDone(userId);
        verify(advertisementReader, times(1)).findRecentDone(userId);
    }

    @Test
    @DisplayName("관리자 대시보드 성공 테스트")
    void loadAdminDashBoardTest() {
        // given
        Integer notApprovedUser = 1;

        DashboardInfo.AdminAdCountInfo adCount = new DashboardInfo.AdminAdCountInfo(1,1, 1, 0);
        List<DashboardInfo.TodayWork> todayWorkList = new ArrayList<>();
        List<DashboardInfo.DailyAvgDone> dailyAvgDoneList = new ArrayList<>();
        List<DashboardInfo.PersonalTask> personalTaskList = new ArrayList<>();

        doReturn(notApprovedUser)
            .when(userManagementReader)
            .findNotApproveUserCount();

        doReturn(adCount)
            .when(advertisementReader)
            .findAdminAdCountInfo();

        doReturn(todayWorkList)
            .when(advertisementReader)
            .findTodayWorkList();

        doReturn(dailyAvgDoneList)
            .when(advertisementReader)
            .findDailyAvgDoneList();

        doReturn(personalTaskList)
            .when(advertisementReader)
            .findPersonalTaskList();

        // when
        DashboardInfo.DashboardAdminDataInfo result = userBoardService.loadAdminDashboardData();

        // then
        assertNotNull(result);
        assertEquals(notApprovedUser, result.adminTimeline().notApprovedUser());
        assertEquals(adCount.remainingAd(), result.adminTimeline().remainingAd());
        assertEquals(adCount.totalAd(), result.adCount().totalAd());
        assertEquals(adCount.doneAd(), result.adCount().doneAd());
        assertEquals(adCount.notDoneAd(), result.adCount().notDoneAd());
        assertEquals(todayWorkList, result.todayWorkList());
        assertEquals(dailyAvgDoneList, result.dailyAvgDoneList());
        assertEquals(personalTaskList, result.personalTaskList());

        verify(userManagementReader, times(1)).findNotApproveUserCount();
        verify(advertisementReader, times(1)).findAdminAdCountInfo();
        verify(advertisementReader, times(1)).findTodayWorkList();
        verify(advertisementReader, times(1)).findDailyAvgDoneList();
        verify(advertisementReader, times(1)).findPersonalTaskList();
    }
}