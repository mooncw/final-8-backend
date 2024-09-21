package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.fastcampus.befinal.domain.service.SameAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SameAdServiceImpl implements SameAdService {
    private final AdvertisementReader advertisementReader;

    @Override
    public TaskInfo.SameTaskListInfo findSameAdList(TaskCommand.SameAdFilterConditionRequest command) {
        ScrollPagination<String, TaskInfo.SameAdvertisementListInfo> filterSameAdPagination = advertisementReader.findSameAdList(command);
        return TaskInfo.SameTaskListInfo.of(filterSameAdPagination);
    }
}
