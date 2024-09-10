package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.FilterCommand;
import com.fastcampus.befinal.domain.info.FilterInfo;

import java.util.List;

public interface FilterReader {
    List<FilterInfo.FilterOptionInfo> findMediaList(FilterCommand.ConditionCommand command);
    List<FilterInfo.FilterOptionInfo> findCategoryList(FilterCommand.ConditionCommand command);
}
