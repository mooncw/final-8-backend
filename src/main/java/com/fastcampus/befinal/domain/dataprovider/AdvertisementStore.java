package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.IssueAdInfo;

public interface AdvertisementStore {
    void saveIssueAdDecision(IssueAdInfo.IssueAdDecisionSaveInfo info);
}
