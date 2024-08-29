package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.DashBoardInfo;
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

        DashBoardInfo.AdCount adCount = DashBoardInfo.AdCount.builder()
                        .totalAd(1)
                        .myAd(1)
                        .totalDoneAd(1)
                        .myDoneAd(1)
                        .totalNotDoneAd(1)
                        .myNotDoneAd(1)
                        .build();
        List<DashBoardInfo.DailyDone> dailyDoneList = new ArrayList<>();
        List<DashBoardInfo.RecentDone> recentDoneList = new ArrayList<>();

        when(advertisementReader.findAdCount(userId)).thenReturn(adCount);
        when(advertisementReader.findDailyDone(userId)).thenReturn(dailyDoneList);
        when(advertisementReader.findRecentDone(userId)).thenReturn(recentDoneList);

        // when
        DashBoardInfo.DashBoardDataInfo result = userBoardService.loadUserDashBoardData(userId);

        // then
        assertNotNull(result);
        assertEquals(adCount.totalAd(), result.totalAd());
        assertEquals(adCount.myAd(), result.myAd());
        assertEquals(adCount.totalDoneAd(), result.totalDoneAd());
        assertEquals(adCount.myDoneAd(), result.myDoneAd());
        assertEquals(adCount.totalNotDoneAd(), result.totalNotDoneAd());
        assertEquals(adCount.myNotDoneAd(), result.myNotDoneAd());
        assertEquals(dailyDoneList, result.dailyDoneList());
        assertEquals(recentDoneList, result.recentDoneList());

        verify(advertisementReader, times(1)).findAdCount(userId);
        verify(advertisementReader, times(1)).findDailyDone(userId);
        verify(advertisementReader, times(1)).findRecentDone(userId);
    }
}