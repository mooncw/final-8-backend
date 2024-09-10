package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.command.FilterCommand;
import com.fastcampus.befinal.domain.dataprovider.FilterReader;
import com.fastcampus.befinal.domain.entity.AdCategory;
import com.fastcampus.befinal.domain.entity.AdMedia;
import com.fastcampus.befinal.domain.info.FilterInfo;
import com.fastcampus.befinal.domain.repository.AdCategoryRepository;
import com.fastcampus.befinal.domain.repository.AdMediaRepository;
import com.fastcampus.befinal.domain.repository.AdvertisementRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@DataProvider
@RequiredArgsConstructor
public class FilterReaderImpl implements FilterReader {
    private final AdMediaRepository adMediaRepository;
    private final AdCategoryRepository adCategoryRepository;
    private final AdvertisementRepositoryCustom advertisementRepositoryCustom;

    @Override
    public List<FilterInfo.FilterOptionInfo> findMediaList(FilterCommand.ConditionCommand command) {
        List<AdMedia> mediaList = (command.keyword() == null || command.keyword().isEmpty()) ?
            adMediaRepository.findAllByOrderByNameAsc() :
            adMediaRepository.findByNameContainingOrderByNameAsc(command.keyword());

        return mediaList.stream()
            .map(media -> FilterInfo.FilterOptionInfo.of(
                media.getName(),
                advertisementRepositoryCustom.countMediaByPeriod(media, command.period())
            ))
            .collect(Collectors.toList());
    }

    @Override
    public List<FilterInfo.FilterOptionInfo> findCategoryList(FilterCommand.ConditionCommand command) {
        List<AdCategory> categoryList = (command.keyword() == null || command.keyword().isEmpty()) ?
            adCategoryRepository.findAllByOrderByCategoryAsc() :
            adCategoryRepository.findByCategoryContainingOrderByCategoryAsc(command.keyword());

        return categoryList.stream()
            .map(category -> FilterInfo.FilterOptionInfo.of(
                category.getCategory(),
                advertisementRepositoryCustom.countCategoryByPeriod(category, command.period())
            ))
            .collect(Collectors.toList());
    }
}
