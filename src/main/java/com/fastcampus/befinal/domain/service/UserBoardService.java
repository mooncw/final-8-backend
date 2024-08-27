package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.info.DashBoardInfo;

public interface UserBoardService {
    DashBoardInfo.DashBoardDataInfo loadUserDashBoardData(String userId);
}
