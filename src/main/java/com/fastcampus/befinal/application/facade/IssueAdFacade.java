package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.IssueAdDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.service.IssueAdService;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class IssueAdFacade {
    private final IssueAdService issueAdService;
    private final IssueAdDtoMapper issueAdDtoMapper;

    public IssueAdDto.IssueAdDetailResponse findIssueAdDetail(String advertisementId){
        IssueAdInfo.IssueAdDetailAllInfo info = issueAdService.findIssueAdDetail(advertisementId);
        return issueAdDtoMapper.from(info);
    }
}
