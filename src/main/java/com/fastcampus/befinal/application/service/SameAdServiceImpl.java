package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.SameAdCommand;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.fastcampus.befinal.domain.service.SameAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SameAdServiceImpl implements SameAdService {
    private final AdvertisementReader advertisementReader;

    @Override
    @Transactional(readOnly = true)
    public SameAdInfo.SameTaskListInfo findSameAdList(SameAdCommand.SameAdFilterConditionRequest command) {
        ScrollPagination<String, SameAdInfo.SameAdvertisementListInfo> filterSameAdPagination = advertisementReader.findSameAdList(command);
        return SameAdInfo.SameTaskListInfo.of(filterSameAdPagination);
    }
}
