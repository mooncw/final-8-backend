package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.dataprovider.UserTaskReader;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.fastcampus.befinal.domain.service.UserTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTaskServiceImpl implements UserTaskService {
    private final UserTaskReader userTaskReader;

    @Override
    public TaskInfo.TaskResponse loadFilterTask(String userId, TaskCommand.FilterConditionRequest taskCommand) {
        TaskInfo.AdCountInfo myAdCount = userTaskReader.findMyAdCount(userId);

        ScrollPagination<TaskInfo.CursorInfo, TaskInfo.AdvertisementListInfo> filterTaskPagination = userTaskReader.findFilterMyAdvertisement(userId, taskCommand);

        TaskInfo.TaskListInfo myTaskList = TaskInfo.TaskListInfo.of(filterTaskPagination);

        return TaskInfo.TaskResponse.of(myAdCount, myTaskList);
    }
}
