package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdReviewReader;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.service.IssueAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueAdServiceImpl implements IssueAdService {
    private final AdvertisementReader advertisementReader;
    private final AdReviewReader adReviewReader;

    @Override
    @Transactional(readOnly = true)
    public IssueAdInfo.IssueAdDetailAllInfo findIssueAdDetail(String advertisementId){
        IssueAdInfo.IssueAdDetailInfo issueAdDetail = advertisementReader.findIssueAdDetail(advertisementId);
        List<IssueAdInfo.IssueAdReviewInfo> adReviewList = adReviewReader.findIssueAdReviewList(advertisementId);
        return IssueAdInfo.IssueAdDetailAllInfo.of(issueAdDetail, adReviewList);
    }
}
