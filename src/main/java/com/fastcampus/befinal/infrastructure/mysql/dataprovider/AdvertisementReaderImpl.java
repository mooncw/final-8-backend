package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.repository.AdReviewRepositoryCustom;
import com.fastcampus.befinal.domain.repository.AdvertisementRepositoryCustom;
import lombok.RequiredArgsConstructor;
import java.util.List;

import static com.fastcampus.befinal.common.response.error.info.IssueAdErrorCode.NOT_FOUND_ADVERTISEMENT_ID;

@DataProvider
@RequiredArgsConstructor
public class AdvertisementReaderImpl implements AdvertisementReader {
    private final AdvertisementRepositoryCustom advertisementRepositoryCustom;
    private final AdReviewRepositoryCustom adReviewRepositoryCustom;

    @Override
    public DashboardInfo.AdCount findAdCount(String userId) {
        return advertisementRepositoryCustom.getAdCount(userId);
    }

    @Override
    public List<DashboardInfo.DailyDone> findDailyDone(String userId) {
        return advertisementRepositoryCustom.getDailyDoneList(userId);
    }

    @Override
    public List<DashboardInfo.RecentDone> findRecentDone(String userId) {
        return advertisementRepositoryCustom.getRecentDoneList(userId);
    }

    @Override
    public IssueAdInfo.IssueAdDetailAllInfo findIssueAdDetail(String advertisementId){
        IssueAdInfo.IssueAdDetailInfo issueAdDetail = advertisementRepositoryCustom.getIssueAdDetail(advertisementId)
            .orElseThrow(()-> new BusinessException(NOT_FOUND_ADVERTISEMENT_ID));
        List<IssueAdInfo.IssueAdReviewInfo> issueAdReviewList = adReviewRepositoryCustom.getReviewList(advertisementId);
        return IssueAdInfo.IssueAdDetailAllInfo.of(issueAdDetail, issueAdReviewList);
    }
}
