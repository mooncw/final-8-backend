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
    public TaskInfo.MyTaskInfo loadFilterTask(String userId, TaskCommand.FilterConditionRequest taskCommand) {
        TaskInfo.MyAdCountInfo myAdCount = userTaskReader.findMyAdCount(userId);

        ScrollPagination<TaskInfo.CursorInfo, TaskInfo.MyAdvertisementInfo> filterTaskPagination = userTaskReader.findFilterMyAdvertisement(userId, taskCommand);

        TaskInfo.MyTaskListResponse myTaskList = TaskInfo.MyTaskListResponse.of(
                filterTaskPagination.totalElements(), filterTaskPagination.currentCursorId(), filterTaskPagination.contents());

        return TaskInfo.MyTaskInfo.of(myAdCount, myTaskList);
    }
}
