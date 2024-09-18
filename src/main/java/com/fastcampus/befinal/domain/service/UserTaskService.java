package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.info.TaskInfo;

public interface UserTaskService {
    TaskInfo.TaskResponse loadFilterTask(String userId, TaskCommand.FilterConditionRequest taskCommand);
    TaskInfo.TaskListInfo findIssueAdList(TaskCommand.FilterConditionRequest command);
}
