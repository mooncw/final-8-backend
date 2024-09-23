package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.DashboardDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.domain.service.UserBoardService;
import com.fastcampus.befinal.presentation.dto.DashboardDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class BoardFacade {
    private final UserBoardService userBoardService;
    private final DashboardDtoMapper dashBoardDtoMapper;

    public DashboardDto.DashboardDataResponse loadUserDashboardData(String userId) {
        DashboardInfo.DashboardDataInfo dataInfo = userBoardService.loadUserDashboardData(userId);
        return dashBoardDtoMapper.from(dataInfo);
    }

    public DashboardDto.DashboardAdminDataResponse loadAdminDashboardData(){
        DashboardInfo.DashboardAdminDataInfo info = userBoardService.loadAdminDashboardData();
        return dashBoardDtoMapper.from(info);
    }

    public DashboardDto.UserNameListResponse loadUserNameList() {
        DashboardInfo.UserNameListInfo info = userBoardService.findUserNameList();
        return dashBoardDtoMapper.from(info);
    }

    public DashboardDto.DailyDoneList loadDailyDoneListByUserId(String userId) {
        DashboardInfo.DailyDoneList info = userBoardService.findDailyDoneListByUserId(userId);
        return dashBoardDtoMapper.from(info);
    }
}
