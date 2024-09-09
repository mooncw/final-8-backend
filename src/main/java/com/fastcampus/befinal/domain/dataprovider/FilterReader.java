package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.FilterInfo;

import java.util.List;

public interface FilterReader {
    List<FilterInfo.FilterOptionInfo> findMediaList(String keyword);
    List<FilterInfo.FilterOptionInfo> findCategoryList(String keyword);
}
