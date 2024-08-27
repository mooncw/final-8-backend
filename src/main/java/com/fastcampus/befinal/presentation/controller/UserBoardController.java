package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.BoardFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.response.success.info.DashBoardSuccessCode;
import com.fastcampus.befinal.presentation.dto.DashBoardDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "DashBoard", description = "사용자 대시보드 관련 API")
public class UserBoardController {

    private final BoardFacade boardFacade;

    @GetMapping
    public ResponseEntity<AppApiResponse<DashBoardDto.DashBoardDataResponse>> getBoardData (

    ) {
        String userId = "testID";

        DashBoardDto.DashBoardDataResponse response = boardFacade.loadUserDashBoardData(userId);
        return ResponseEntityFactory.toResponseEntity(DashBoardSuccessCode.CHECK_DASHBOARD, response);
    }
}
