package com.fastcampus.befinal.application.facade;

import com.fastcampus.befinal.application.mapper.IssueAdDtoMapper;
import com.fastcampus.befinal.application.mapper.UserTaskDtoMapper;
import com.fastcampus.befinal.common.annotation.Facade;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.fastcampus.befinal.domain.service.IssueAdService;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import com.fastcampus.befinal.presentation.dto.TaskDto;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class IssueAdFacade {
    private final IssueAdService issueAdService;
    private final IssueAdDtoMapper issueAdDtoMapper;
    private final UserTaskDtoMapper userTaskDtoMapper;

    public TaskDto.TaskListInfo findIssueAdList(TaskDto.FilterConditionRequest request){
        TaskInfo.TaskListInfo info = issueAdService.findIssueAdList(userTaskDtoMapper.toTaskCommand(request));
        return userTaskDtoMapper.from(info);
    }

    public IssueAdDto.IssueAdDetailResponse findIssueAdDetail(String advertisementId){
        IssueAdInfo.IssueAdDetailAllInfo info = issueAdService.findIssueAdDetail(advertisementId);
        return issueAdDtoMapper.from(info);
    }
}
