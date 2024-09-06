package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.AdReviewReader;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.repository.AdReviewRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DataProvider
@RequiredArgsConstructor
public class AdReviewReaderImpl implements AdReviewReader {
    private final AdReviewRepositoryCustom adReviewRepositoryCustom;
    @Override
    public List<IssueAdInfo.IssueAdReviewInfo> findIssueAdReviewList(String advertisementId){
        return adReviewRepositoryCustom.getReviewList(advertisementId);
    }
}
