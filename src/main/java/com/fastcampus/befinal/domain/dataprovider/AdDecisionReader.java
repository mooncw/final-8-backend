package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.AdDecision;
import com.fastcampus.befinal.domain.info.IssueAdInfo;

import java.util.List;

public interface AdDecisionReader {
    List<IssueAdInfo.IssueAdDecisionInfo> findIssueAdDecisionList();
    AdDecision findAdDecisionById(Long id);
}
