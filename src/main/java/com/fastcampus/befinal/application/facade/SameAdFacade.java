package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.SameAdDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.fastcampus.befinal.domain.service.SameAdService;
import com.fastcampus.befinal.presentation.dto.SameAdDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class SameAdFacade {
    private final SameAdService sameAdService;
    private final SameAdDtoMapper sameAdDtoMapper;

    public SameAdDto.SameTaskListInfo findSameAdList(SameAdDto.SameAdFilterConditionRequest request) {
        SameAdInfo.SameTaskListInfo info = sameAdService.findSameAdList(sameAdDtoMapper.toTaskCommand(request));
        return sameAdDtoMapper.from(info);
    }

    public SameAdDto.FindSimilarityListResponse findSimilarityList(String inspectionAdvertisementId) {
        return sameAdDtoMapper.from(sameAdService.findSimilarityList(inspectionAdvertisementId));
    }
}
