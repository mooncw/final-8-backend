package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.info.IssueAdInfo;

public interface IssueAdService {
    IssueAdInfo.IssueAdDetailAllInfo findIssueAdDetail(String advertisementId);
}
