package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.info.TaskInfo;

public interface UserTaskReader {
    TaskInfo.MyAdCountInfo findMyAdCount(String userId);
    ScrollPagination<TaskInfo.CursorInfo, TaskInfo.MyAdvertisementInfo> findFilterMyAdvertisement(String userId, TaskCommand.FilterConditionRequest taskCommand);
}
