package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.TaskFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.response.success.info.MyTaskSuccessCode;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.domain.info.UserDetailsInfo;
import com.fastcampus.befinal.presentation.dto.TaskDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/my-task")
@Tag(name = "User-Task", description = "사용자 나의 작업 관련 API")
public class UserTaskController {
    private final TaskFacade taskFacade;

    @PostMapping
    @Operation(summary = "사용자 나의 작업 조회")
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
                                                        "\"category\": \"IT\", " +
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
            @AuthenticationPrincipal UserDetailsInfo user,
            @RequestBody
            @Validated(DefaultGroupSequence.class)
            TaskDto.FilterConditionRequest request
    ) {
        String userId = user.getUsername();
        TaskDto.TaskResponse response = taskFacade.loadFilterMyTask(userId, request);
        return ResponseEntityFactory.toResponseEntity(MyTaskSuccessCode.CHECK_MY_TASK_SUCCESS, response);
    }
}
