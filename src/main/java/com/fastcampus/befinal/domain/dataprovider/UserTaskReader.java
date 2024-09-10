package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.info.TaskInfo;

public interface UserTaskReader {
    TaskInfo.AdCountInfo findMyAdCount(String userId);
    ScrollPagination<TaskInfo.CursorInfo, TaskInfo.AdvertisementListInfo> findFilterMyAdvertisement(String userId, TaskCommand.FilterConditionRequest taskCommand);
}
