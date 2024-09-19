package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.info.IssueAdInfo;

import java.util.List;

public interface AdvertisementStore {
    void saveIssueAdDecision(IssueAdInfo.IssueAdDecisionSaveInfo info);
    void updateAssignee(UserSummary userSummary, List<String> personalTaskAdIdList);
}
