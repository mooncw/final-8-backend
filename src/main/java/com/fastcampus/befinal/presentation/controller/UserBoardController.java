package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.BoardFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.response.error.info.DashBoardErrorCode;
import com.fastcampus.befinal.common.response.success.info.DashBoardSuccessCode;
import com.fastcampus.befinal.presentation.dto.DashBoardDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "사용자 대시보드")
    @ApiResponse(responseCode = "200", description = "대시보드 확인 가능합니다.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = DashBoardDto.DashBoardDataResponse.class,
                    example = "{\n" +
                            "  \"code\": 1200,\n" +
                            "  \"message\": \"대시보드 확인 가능합니다.\",\n" +
                            "  \"data\": {\n" +
                            "    \"totalAd\": 4,\n" +
                            "    \"myAd\": 3,\n" +
                            "    \"totalDoneAd\": 3,\n" +
                            "    \"myDoneAd\": 3,\n" +
                            "    \"totalNotDoneAd\": 1,\n" +
                            "    \"myNotDoneAd\": 0,\n" +
                            "    \"dailyDoneList\": [\n" +
                            "      {\n" +
                            "        \"date\": \"2024-08-25\",\n" +
                            "        \"dailyMyDoneAd\": 2\n" +
                            "      }\n" +
                            "    ],\n" +
                            "    \"recentDoneList\": [\n" +
                            "      {\n" +
                            "        \"adId\": \"a3\",\n" +
                            "        \"adName\": \"toy\",\n" +
                            "        \"adModifiedDate\": \"2024-08-27T09:00:00\"\n" +
                            "      }\n" +
                            "    ]\n" +
                            "  }\n" +
                            "}"
                    )
            )
    )
    public ResponseEntity<AppApiResponse<DashBoardDto.DashBoardDataResponse>> getBoardData() {
        String userId = "testID";
        if(userId == null) {
            throw new BusinessException(DashBoardErrorCode.DENIED_ACCESS_DASHBOARD);
        }

        DashBoardDto.DashBoardDataResponse response = boardFacade.loadUserDashBoardData(userId);
        return ResponseEntityFactory.toResponseEntity(DashBoardSuccessCode.CHECK_DASHBOARD_SUCCESS, response);
    }
}
