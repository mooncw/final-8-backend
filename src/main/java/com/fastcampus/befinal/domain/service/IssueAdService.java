package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;

import java.util.List;

public interface IssueAdService {
    IssueAdInfo.IssueAdDetailAllInfo findIssueAdDetail(String advertisementId);
    void saveIssueAdReviews(IssueAdDto.IssueAdReviewRequest request);
    IssueAdInfo.IssueAdProvisionListInfo findProvisionList();
    IssueAdInfo.IssueAdDecisionListInfo findDecisionList();
    void saveIssueAdResultDecision(IssueAdDto.IssueAdResultDecisionRequest command);
}
