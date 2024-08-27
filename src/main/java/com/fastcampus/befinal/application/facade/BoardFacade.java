package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.DashBoardDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.info.DashBoardInfo;
import com.fastcampus.befinal.domain.service.UserBoardService;
import com.fastcampus.befinal.presentation.dto.DashBoardDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class BoardFacade {
    private final UserBoardService userBoardService;
    private final DashBoardDtoMapper dashBoardDtoMapper;

    public DashBoardDto.DashBoardDataResponse loadUserDashBoardData(String userId) {
        DashBoardInfo.DashBoardDataInfo dataInfo = userBoardService.loadUserDashBoardData(userId);
        return dashBoardDtoMapper.from(dataInfo);
    }
}
