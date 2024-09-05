package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.fastcampus.befinal.presentation.dto.TaskDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserTaskDtoMapper {
    TaskDto.MyTaskResponse from(TaskInfo.MyTaskInfo myTask);
    TaskDto.MyAdCountResponse from(TaskInfo.MyAdCountInfo myAdCount);
    TaskDto.MyAdvertisementResponse from(TaskInfo.MyAdvertisementInfo myAdvertisement);
    TaskDto.MyTaskListResponse from(TaskInfo.MyTaskListResponse findMyTaskListResponse);

    TaskCommand.CursorInfo toCursorCommand(TaskDto.CursorInfo cursorInfo);
    TaskCommand.FilterConditionRequest toTaskCommand(TaskDto.FilterConditionRequest request);
}
