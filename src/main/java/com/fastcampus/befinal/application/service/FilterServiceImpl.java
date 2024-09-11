package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.command.FilterCommand;
import com.fastcampus.befinal.domain.dataprovider.FilterReader;
import com.fastcampus.befinal.domain.info.FilterInfo;
import com.fastcampus.befinal.domain.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {

    private final FilterReader filterReader;

    @Override
    @Transactional(readOnly = true)
    public List<FilterInfo.FilterOptionInfo> searchMediaOptions(FilterCommand.ConditionCommand command) {
        return filterReader.findMediaList(command);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilterInfo.FilterOptionInfo> searchCategoryOptions(FilterCommand.ConditionCommand command) {
        return filterReader.findCategoryList(command);
    }
}
