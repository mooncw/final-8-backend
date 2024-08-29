package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.DashBoardInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@DisplayName("UserBoardService 테스트")
@ExtendWith(MockitoExtension.class)
public class UserBoardServiceImplTest {
    @InjectMocks
    private UserBoardServiceImpl userBoardService;

    @Mock
    private AdvertisementReader advertisementReader;

    @Test
    @DisplayName("대시보드 성공 테스트")
    void loadDashBoardTest() {
        // given
        String userId = "testID";
        LocalDate date = LocalDate.now();

        DashBoardInfo.DashBoardDataInfo info = DashBoardInfo.DashBoardDataInfo.builder()
                .totalAd(1)
                .myAd(1)
                .totalDoneAd(1)
                .myDoneAd(1)
                .totalNotDoneAd(1)
                .myNotDoneAd(1)
                .dailyDoneList(new ArrayList<>())
                .recentDoneList(new ArrayList<>())
                .build();

        when(advertisementReader.findDashBoardData(userId)).thenReturn(info);

        // when
        DashBoardInfo.DashBoardDataInfo result = userBoardService.loadUserDashBoardData(userId);

        // then
        verify(advertisementReader, times(1)).findDashBoardData(userId);
    }
}
