package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.AdSimilarityReader;
import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.fastcampus.befinal.domain.repository.AdSimilarityRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.fastcampus.befinal.common.response.error.info.SameErrorCode.NOT_FOUND_SAME_ADVERTISEMENT_FOR_INSPECTION_ADVERTISEMENT;

@DataProvider
@RequiredArgsConstructor
public class AdSimilarityReaderImpl implements AdSimilarityReader {
    private final AdSimilarityRepositoryCustom adSimilarityRepositoryCustom;

    @Override
    public List<SameAdInfo.AdSimilarityInfo> findAdSimilarityInfoList(String inspectionAdvertisementId) {
        return adSimilarityRepositoryCustom.findAdSimilarityInfoList(inspectionAdvertisementId);
    }

    @Override
    public SameAdInfo.FindSimilarityDetailInfo findSimilarityDetailInfo(String inspectionAdvertisementId, String comparisonAdvertisementId) {
        return adSimilarityRepositoryCustom.findSimilarityDetailInfo(inspectionAdvertisementId, comparisonAdvertisementId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_SAME_ADVERTISEMENT_FOR_INSPECTION_ADVERTISEMENT));
    }
}
