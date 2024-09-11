package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.*;
import com.fastcampus.befinal.domain.entity.Advertisement;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.service.IssueAdService;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueAdServiceImpl implements IssueAdService {
    private final AdvertisementReader advertisementReader;
    private final AdvertisementStore advertisementStore;
    private final AdReviewReader adReviewReader;
    private final AdReviewStore adReviewStore;
    private final AdProvisionReader adProvisionReader;
    private final AdDecisionReader adDecisionReader;

    @Override
    @Transactional(readOnly = true)
    public IssueAdInfo.IssueAdDetailAllInfo findIssueAdDetail(String advertisementId){
        IssueAdInfo.IssueAdDetailInfo issueAdDetail = advertisementReader.findIssueAdDetail(advertisementId);
        List<IssueAdInfo.IssueAdReviewInfo> adReviewList = adReviewReader.findIssueAdReviewList(advertisementId);
        return IssueAdInfo.IssueAdDetailAllInfo.of(issueAdDetail, adReviewList);
    }

    @Override
    @Transactional
    public void saveIssueAdReviews(List<IssueAdDto.IssueAdReviewRequest> commands){
        for (IssueAdDto.IssueAdReviewRequest command : commands) {
            switch(command.operationType()){
                case "Create":
                    IssueAdInfo.IssueAdReviewSaveInfo saveInfo =
                        IssueAdInfo.IssueAdReviewSaveInfo.from(command);
                    adReviewStore.saveAdReview(saveInfo);
                    break;
                case "Update":
                    IssueAdInfo.IssueAdReviewUpdateInfo updateInfo =
                        IssueAdInfo.IssueAdReviewUpdateInfo.from(command);
                    adReviewStore.updateAdReview(updateInfo);
                    break;
                case "Delete":
                    IssueAdInfo.IssueAdReviewDeleteInfo deleteInfo =
                        IssueAdInfo.IssueAdReviewDeleteInfo.from(command.reviewId());
                    adReviewStore.deleteAdReview(deleteInfo);
                    break;
            }
        }
    }

    @Transactional
    public void saveIssueAdResultDecision(IssueAdDto.IssueAdResultDecisionRequest command){
        Advertisement advertisement = advertisementReader.findAdvertisementById(command.advertisementId());
        IssueAdInfo.IssueAdDecisionSaveInfo info = IssueAdInfo.IssueAdDecisionSaveInfo.of(advertisement, command.decisionId());
        advertisementStore.saveIssueAdDecision(info);
    }

    @Override
    @Transactional
    public List<IssueAdInfo.IssueAdProvisionInfo> findProvisionList(){
        return adProvisionReader.findIssueAdProvisionList();
    }

    @Override
    @Transactional
    public List<IssueAdInfo.IssueAdDecisionInfo> findDecisionList(){
        return adDecisionReader.findIssueAdDecisionList();
    }
}
