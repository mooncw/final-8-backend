package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.info.TaskInfo;

public interface UserTaskService {
    TaskInfo.MyTaskInfo loadFilterTask(String userId, TaskCommand.FilterConditionRequest taskCommand);
}
