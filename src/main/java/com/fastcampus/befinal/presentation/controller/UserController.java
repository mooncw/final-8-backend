package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.TaskFacade;
import com.fastcampus.befinal.application.facade.UserFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.response.success.info.MyTaskSuccessCode;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.presentation.dto.TaskDto;
import com.fastcampus.befinal.presentation.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.fastcampus.befinal.common.response.success.info.UserSuccessCode.UPDATE_PASSWORD_SUCCESS;
import static com.fastcampus.befinal.common.response.success.info.UserSuccessCode.UPDATE_USER_SUCCESS;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "마이페이지 관련 API")
public class UserController {
    private final UserFacade userFacade;
    private final TaskFacade taskFacade;

    @PutMapping("/info")
    @Operation(summary = "회원정보 변경")
    @ApiResponse(responseCode = "200", description = "회원 정보가 변경되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 3300," +
                    "\"message\": \"회원 정보가 변경되었습니다.\"" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> updateUserInfo(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        UserDto.UserUpdateRequest request,

        @AuthenticationPrincipal
        UserDetailsInfo userDetails
    ){
        userFacade.updateUser(userDetails, request);
        return ResponseEntityFactory.toResponseEntity(UPDATE_USER_SUCCESS);
    }

    @PutMapping("/password")
    @Operation(summary = "비밀번호 변경")
    @ApiResponse(responseCode = "200", description = "비밀번호가 변경되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 3301," +
                    "\"message\": \"비밀번호가 변경되었습니다.\"" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> updatePassword(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        UserDto.PasswordUpdateRequest request,

        @AuthenticationPrincipal
        UserDetailsInfo userDetails
    ){
        userFacade.updatePassword(userDetails, request);
        return ResponseEntityFactory.toResponseEntity(UPDATE_PASSWORD_SUCCESS);
    }

    @PostMapping("/my-task")
    @Operation(summary = "사용자 나의 작업 조회 - Param default 값은 null")
    @ApiResponse(responseCode = "200", description = "나의 작업 조회가 가능합니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                        "\"code\": 3500, " +
                        "\"message\": \"나의 작업 조회가 가능합니다.\", " +
                        "\"data\": { " +
                            "\"adCount\": { " +
                                "\"myTotalAd\": 1, " +
                                "\"myDoneAd\": 1, " +
                                "\"myNotDoneAd\": 0 " +
                            "}, " +
                            "\"taskList\": { " +
                                "\"totalElements\": 1, " +
                                "\"cursorInfo\": { " +
                                    "\"cursorState\": true, " +
                                    "\"cursorId\": \"A00001\" " +
                                "}, " +
                            "\"advertisementList\": [ " +
                                "{ " +
                                    "\"adId\": \"A00001\", " +
                                    "\"media\": \"동아일보\", " +
                                    "\"category\": \"가정용품\", " +
                                    "\"product\": \"노트북\", " +
                                    "\"advertiser\": \"테크컴퍼니\", " +
                                    "\"state\": true, " +
                                    "\"issue\": false " +
                                "} " +
                            "] " +
                        "} " +
                    "} " +
                "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<TaskDto.TaskResponse>> checkMyTask(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        TaskDto.FilterConditionRequest request

        //@AuthenticationPrincipal UserDetailsInfo user
    ) {
        //String userId = user.getUsername();
        TaskDto.TaskResponse response = taskFacade.loadFilterMyTask("1", request);
        return ResponseEntityFactory.toResponseEntity(MyTaskSuccessCode.CHECK_MY_TASK_SUCCESS, response);
    }
}
