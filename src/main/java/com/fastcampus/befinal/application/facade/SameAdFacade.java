package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.UserTaskDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.fastcampus.befinal.domain.service.SameAdService;
import com.fastcampus.befinal.presentation.dto.TaskDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class SameAdFacade {
    private final SameAdService sameAdService;
    private final UserTaskDtoMapper userTaskDtoMapper;

    public TaskDto.SameTaskListInfo findSameAdList(TaskDto.SameAdFilterConditionRequest request) {
        TaskInfo.SameTaskListInfo info = sameAdService.findSameAdList(userTaskDtoMapper.toTaskCommand(request));
        return userTaskDtoMapper.from(info);
    }
}
