package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;

public interface IssueAdService {

    TaskInfo.IssueAdListInfo findIssueAdList(TaskCommand.FilterConditionRequest command);
    IssueAdInfo.IssueAdDetailAllInfo findIssueAdDetail(String advertisementId);
    void saveIssueAdReviews(IssueAdDto.IssueAdReviewRequest request);
    IssueAdInfo.IssueAdProvisionListInfo findProvisionList();
    IssueAdInfo.IssueAdDecisionListInfo findDecisionList();
    void saveIssueAdResultDecision(IssueAdDto.IssueAdResultDecisionRequest command, Long userId);
}
