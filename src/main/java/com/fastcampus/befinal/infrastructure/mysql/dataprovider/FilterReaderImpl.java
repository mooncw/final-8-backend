package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.FilterReader;
import com.fastcampus.befinal.domain.entity.AdCategory;
import com.fastcampus.befinal.domain.entity.AdMedia;
import com.fastcampus.befinal.domain.info.FilterInfo;
import com.fastcampus.befinal.domain.repository.AdCategoryRepository;
import com.fastcampus.befinal.domain.repository.AdMediaRepository;
import com.fastcampus.befinal.domain.repository.AdvertisementRepository;
import com.fastcampus.befinal.domain.repository.AdvertisementRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@DataProvider
@RequiredArgsConstructor
public class FilterReaderImpl implements FilterReader {
    private final AdMediaRepository adMediaRepository;
    private final AdCategoryRepository adCategoryRepository;
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementRepositoryCustom advertisementRepositoryCustom;

    @Override
    public List<FilterInfo.FilterOptionInfo> findMediaList(String keyword) {
        List<AdMedia> mediaList = (keyword == null || keyword.isEmpty()) ?
            adMediaRepository.findAllByOrderByNameAsc() :
            adMediaRepository.findByNameContainingOrderByNameAsc(keyword);

        return mediaList.stream()
            .map(media -> FilterInfo.FilterOptionInfo.of(
                media.getName(),
                advertisementRepository.countByAdMedia(media)
            ))
            .collect(Collectors.toList());
    }

    @Override
    public List<FilterInfo.FilterOptionInfo> findCategoryList(String keyword) {
        List<AdCategory> categoryList = (keyword == null || keyword.isEmpty()) ?
            adCategoryRepository.findAllByOrderByCategoryAsc() :
            adCategoryRepository.findByCategoryContainingOrderByCategoryAsc(keyword);

        return categoryList.stream()
            .map(category -> FilterInfo.FilterOptionInfo.of(
                category.getCategory(),
                advertisementRepository.countByAdCategory(category)
            ))
            .collect(Collectors.toList());
    }
}
