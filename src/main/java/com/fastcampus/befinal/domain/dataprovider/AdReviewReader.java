package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.IssueAdInfo;

import java.util.List;

public interface AdReviewReader {
    List<IssueAdInfo.IssueAdReviewInfo> findIssueAdReviewList(String advertisementId);
}
