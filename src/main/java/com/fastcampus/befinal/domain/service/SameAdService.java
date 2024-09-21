package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.info.SameAdInfo;

public interface SameAdService {
    SameAdInfo.FindSimilarityListInfo findSimilarityList(String inspectionAdvertisementId);

    SameAdInfo.FindSimilarityDetailInfo findSimilarityDetail(String inspectionAdvertisementId, String comparisonAdvertisementId);
}
