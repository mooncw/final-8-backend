package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.*;
import com.fastcampus.befinal.domain.entity.AdDecision;
import com.fastcampus.befinal.domain.entity.AdProvision;
import com.fastcampus.befinal.domain.entity.Advertisement;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.service.IssueAdService;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.fastcampus.befinal.common.response.error.info.IssueAdErrorCode.INVALID_OPERATION_TYPE;

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
    public void saveIssueAdReviews(IssueAdDto.IssueAdReviewRequest commands){
        for (IssueAdDto.IssueAdReview command : commands.reviewList()) {
            switch(command.operationType()){
                case "Create" -> {
                    Advertisement advertisement = advertisementReader.findAdvertisementById(command.advertisementId());
                    AdProvision createAdProvision = adProvisionReader.findAdProvisionById(command.provisionId());
                    IssueAdInfo.IssueAdReviewSaveInfo saveInfo =
                        IssueAdInfo.IssueAdReviewSaveInfo.of(command, advertisement, createAdProvision);
                    adReviewStore.saveAdReview(saveInfo);
                }
                case "Update" -> {
                    AdProvision updateAdProvision = adProvisionReader.findAdProvisionById(command.provisionId());
                    IssueAdInfo.IssueAdReviewUpdateInfo updateInfo =
                        IssueAdInfo.IssueAdReviewUpdateInfo.of(command, updateAdProvision);
                    adReviewStore.updateAdReview(updateInfo);
                }
                case "Delete" -> {
                    IssueAdInfo.IssueAdReviewDeleteInfo deleteInfo =
                        IssueAdInfo.IssueAdReviewDeleteInfo.from(command.reviewId());
                    adReviewStore.deleteAdReview(deleteInfo);
                }
                default -> throw new BusinessException(INVALID_OPERATION_TYPE);
            }
        }
    }

    @Override
    @Transactional
    public void saveIssueAdResultDecision(IssueAdDto.IssueAdResultDecisionRequest command){
        Advertisement advertisement = advertisementReader.findAdvertisementById(command.advertisementId());
        AdDecision adDecision = adDecisionReader.findAdDecisionById(command.decisionId());
        IssueAdInfo.IssueAdDecisionSaveInfo info = IssueAdInfo.IssueAdDecisionSaveInfo.of(advertisement, adDecision);
        advertisementStore.saveIssueAdDecision(info);
    }

    @Override
    @Transactional
    public IssueAdInfo.IssueAdProvisionListInfo findProvisionList(){
        return adProvisionReader.findIssueAdProvisionList();
    }

    @Override
    @Transactional
    public IssueAdInfo.IssueAdDecisionListInfo findDecisionList(){
        return adDecisionReader.findIssueAdDecisionList();
    }
}
