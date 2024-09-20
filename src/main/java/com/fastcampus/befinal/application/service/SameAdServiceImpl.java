package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdSimilarityReader;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.fastcampus.befinal.domain.service.SameAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SameAdServiceImpl implements SameAdService {
    private final AdvertisementReader advertisementReader;
    private final AdSimilarityReader adSimilarityReader;

    @Override
    public SameAdInfo.FindSimilarityListInfo findSimilarityList(String inspectionAdvertisementId) {
        SameAdInfo.InspectionAdInfo inspectionAdInfo =
            advertisementReader.findInspectionAdInfo(inspectionAdvertisementId);

        List<SameAdInfo.AdSimilarityInfo> adSimilarityInfoList =
            adSimilarityReader.findAdSimilarityInfoList(inspectionAdvertisementId);

        return SameAdInfo.FindSimilarityListInfo.of(inspectionAdInfo, adSimilarityInfoList);
    }
}
