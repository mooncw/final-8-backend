package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.dataprovider.UserTaskReader;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.fastcampus.befinal.domain.repository.AdvertisementRepositoryCustom;
import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class UserTaskReaderImpl implements UserTaskReader {
    private final AdvertisementRepositoryCustom advertisementRepositoryCustom;

    @Override
    public TaskInfo.AdCountInfo findMyAdCount(String userId) {
        return advertisementRepositoryCustom.getMyTaskCount(userId);
    }

    @Override
    public ScrollPagination<TaskInfo.CursorInfo, TaskInfo.AdvertisementListInfo> findFilterMyAdvertisement(String userId, TaskCommand.FilterConditionRequest taskCommand) {
        return advertisementRepositoryCustom.getScrollByCursorInfo(userId, taskCommand);
    }
}
