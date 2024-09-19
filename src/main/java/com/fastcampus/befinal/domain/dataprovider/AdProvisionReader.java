package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.entity.AdProvision;
import com.fastcampus.befinal.domain.info.IssueAdInfo;

import java.util.List;

public interface AdProvisionReader {
    IssueAdInfo.IssueAdProvisionListInfo findIssueAdProvisionList();
    AdProvision findAdProvisionById(Integer id);
}
