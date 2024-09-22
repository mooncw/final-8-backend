package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.AdSimilarityReader;
import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.fastcampus.befinal.domain.repository.AdSimilarityRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DataProvider
@RequiredArgsConstructor
public class AdSimilarityReaderImpl implements AdSimilarityReader {
    private final AdSimilarityRepositoryCustom adSimilarityRepositoryCustom;

    @Override
    public List<SameAdInfo.AdSimilarityInfo> findAdSimilarityInfoList(String inspectionAdvertisementId) {
        return adSimilarityRepositoryCustom.findAdSimilarityInfoList(inspectionAdvertisementId);
    }
}
