package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.BoardFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.response.success.info.DashboardSuccessCode;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.presentation.dto.DashboardDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "사용자 대시보드 관련 API")
public class UserBoardController {

    private final BoardFacade boardFacade;

    @GetMapping
    @Operation(summary = "사용자 대시보드")
    @ApiResponse(responseCode = "200", description = "대시보드 확인 가능합니다.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = DashboardDto.DashboardDataResponse.class,
                    example = "{" +
                            "\"code\": 3200, " +
                            "\"message\": \"대시보드 확인 가능합니다.\", " +
                            "\"data\": {" +
                            "   \"adCount\": {" +
                            "      \"totalAd\": 4," +
                            "      \"myAd\": 3," +
                            "      \"totalDoneAd\": 3," +
                            "      \"myDoneAd\": 3," +
                            "      \"totalNotDoneAd\": 1," +
                            "      \"myNotDoneAd\": 0," +
                            "      \"dailyDoneList\": [" +
                            "      {" +
                            "        \"date\": \"2024-08-25," +
                            "        \"dailyMyDoneAd\": 2" +
                            "      }" +
                            "    ]," +
                            "    \"recentDoneList\": [" +
                            "      {" +
                            "        \"adId\": a3" +
                            "        \"adName\": toy" +
                            "        \"adTaskDateTime\": 2024-08-27T09:00:00+09:00" +
                            "      }" +
                            "    ]" +
                            "  }" +
                            "}"
                    )
            )
    )
    public ResponseEntity<AppApiResponse<DashboardDto.DashboardDataResponse>> getBoardData(
            @AuthenticationPrincipal UserDetailsInfo user
        ) {
        String userId = user.getUsername();
        DashboardDto.DashboardDataResponse response = boardFacade.loadUserDashboardData(userId);
        return ResponseEntityFactory.toResponseEntity(DashboardSuccessCode.CHECK_DASHBOARD_SUCCESS, response);
    }
}
