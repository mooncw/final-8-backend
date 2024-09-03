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
}
