package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.dataprovider.UserTaskReader;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.fastcampus.befinal.domain.service.UserTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTaskServiceImpl implements UserTaskService {
    private final UserTaskReader userTaskReader;
    private final AdvertisementReader advertisementReader;

    @Override
    @Transactional(readOnly = true)
    public TaskInfo.TaskResponse loadFilterTask(String userId, TaskCommand.FilterConditionRequest taskCommand) {
        TaskInfo.AdCountInfo myAdCount = userTaskReader.findMyAdCount(userId);

        ScrollPagination<TaskInfo.CursorInfo, TaskInfo.AdvertisementListInfo> filterTaskPagination = userTaskReader.findFilterMyAdvertisement(userId, taskCommand);

        TaskInfo.TaskListInfo myTaskList = TaskInfo.TaskListInfo.of(filterTaskPagination);

        return TaskInfo.TaskResponse.of(myAdCount, myTaskList);
    }

    @Override
    public TaskInfo.TaskListInfo findIssueAdList(TaskCommand.FilterConditionRequest command){
        ScrollPagination<TaskInfo.CursorInfo, TaskInfo.AdvertisementListInfo> filterAdPagination = advertisementReader.findIssueAdList(command);
        return TaskInfo.TaskListInfo.of(filterAdPagination);
    }
}
