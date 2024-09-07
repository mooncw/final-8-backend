package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.TaskFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.response.success.info.MyTaskSuccessCode;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.presentation.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/my-task")
public class UserTaskController {
    private final TaskFacade taskFacade;

    @PostMapping
    public ResponseEntity<AppApiResponse<TaskDto.TaskResponse>> checkMyTask(
            //@AuthenticationPrincipal UserDetailsInfo user,
            @RequestBody
            @Validated(DefaultGroupSequence.class)
            TaskDto.FilterConditionRequest request
    ) {
        //String userId = user.getUsername();
        TaskDto.TaskResponse response = taskFacade.loadFilterMyTask("testID", request);
        return ResponseEntityFactory.toResponseEntity(MyTaskSuccessCode.CHECK_MY_TASK_SUCCESS, response);
    }
}
