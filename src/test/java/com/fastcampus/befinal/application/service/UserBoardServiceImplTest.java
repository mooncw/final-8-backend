package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
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
}