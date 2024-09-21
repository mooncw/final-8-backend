package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.info.TaskInfo;

public interface SameAdService {

    TaskInfo.SameTaskListInfo findSameAdList(TaskCommand.SameAdFilterConditionRequest command);

}
