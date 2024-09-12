package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.FilterCommand;
import com.fastcampus.befinal.domain.info.FilterInfo;

import java.util.List;

public interface FilterService {
    List<FilterInfo.FilterOptionInfo> searchMediaOptions(FilterCommand.ConditionCommand command);
    List<FilterInfo.FilterOptionInfo> searchCategoryOptions(FilterCommand.ConditionCommand command);
}
