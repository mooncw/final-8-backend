package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.DashBoardInfo;
import com.fastcampus.befinal.domain.service.UserBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBoardServiceImpl implements UserBoardService {
    private final AdvertisementReader advertisementReader;

    @Override
    public DashBoardInfo.DashBoardDataInfo loadUserDashBoardData(String userId) {
        return advertisementReader.findDashBoardData(userId);
    }
}
