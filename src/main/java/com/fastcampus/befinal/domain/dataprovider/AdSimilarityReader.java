package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.SameAdInfo;

import java.util.List;

public interface AdSimilarityReader {
    List<SameAdInfo.AdSimilarityInfo> findAdSimilarityInfoList(String inspectionAdvertisementId);
}
