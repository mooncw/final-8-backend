package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.IssueAdInfo;

public interface AdReviewStore {
    void saveAdReview(IssueAdInfo.IssueAdReviewSaveInfo info);
    void updateAdReview(IssueAdInfo.IssueAdReviewUpdateInfo info);
    void deleteAdReview(IssueAdInfo.IssueAdReviewDeleteInfo info);

}
