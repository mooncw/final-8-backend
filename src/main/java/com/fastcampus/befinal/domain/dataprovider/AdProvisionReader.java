package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.IssueAdInfo;

import java.util.List;

public interface AdProvisionReader {
    List<IssueAdInfo.IssueAdProvisionInfo> findIssueAdProvisionList();
}
