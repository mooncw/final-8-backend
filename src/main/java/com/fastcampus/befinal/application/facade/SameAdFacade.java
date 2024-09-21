package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.SameAdDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.service.SameAdService;
import com.fastcampus.befinal.presentation.dto.SameAdDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class SameAdFacade {
    private final SameAdService sameAdService;
    private final SameAdDtoMapper sameAdDtoMapper;

    public SameAdDto.FindSimilarityListResponse findSimilarityList(String inspectionAdvertisementId) {
        return sameAdDtoMapper.from(sameAdService.findSimilarityList(inspectionAdvertisementId));
    }

    public SameAdDto.FindSimilarityDetailResponse findSimilarityDetail(
        String inspectionAdvertisementId,
        String comparisonAdvertisementId
    ) {
        return sameAdDtoMapper.from(sameAdService.findSimilarityDetail(inspectionAdvertisementId, comparisonAdvertisementId));
    }
}
