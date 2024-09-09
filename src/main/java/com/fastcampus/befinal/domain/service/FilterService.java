package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.info.FilterInfo;

import java.util.List;

public interface FilterService {
    List<FilterInfo.FilterOptionInfo> searchMediaOptions(String keyword);
    List<FilterInfo.FilterOptionInfo> searchCategoryOptions(String keyword);
}
