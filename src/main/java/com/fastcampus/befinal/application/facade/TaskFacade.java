package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.UserTaskDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.fastcampus.befinal.domain.service.UserTaskService;
import com.fastcampus.befinal.presentation.dto.TaskDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class TaskFacade {
    private final UserTaskService userTaskService;
    private final UserTaskDtoMapper userTaskDtoMapper;

    public TaskDto.MyTaskResponse loadFilterMyTask(String userId, TaskDto.FilterConditionRequest request) {
        TaskInfo.MyTaskInfo filterTaskInfo = userTaskService.loadFilterTask(userId, userTaskDtoMapper.toTaskCommand(request));
        return userTaskDtoMapper.from(filterTaskInfo);
    }
}
