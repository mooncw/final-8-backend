package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.FilterReader;
import com.fastcampus.befinal.domain.info.FilterInfo;
import com.fastcampus.befinal.domain.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {

    private final FilterReader filterReader;

    @Override
    public List<FilterInfo.FilterOptionInfo> searchMediaOptions(String keyword) {
        return filterReader.findMediaList(keyword);
    }

    @Override
    public List<FilterInfo.FilterOptionInfo> searchCategoryOptions(String keyword) {
        return filterReader.findCategoryList(keyword);
    }
}
