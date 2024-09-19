package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.AdDecisionReader;
import com.fastcampus.befinal.domain.entity.AdDecision;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.repository.AdDecisionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.fastcampus.befinal.common.response.error.info.IssueAdErrorCode.NOT_FOUND_DECISION_ID;

@DataProvider
@RequiredArgsConstructor
public class AdDecisionReaderImpl implements AdDecisionReader {
    private final AdDecisionRepository adDecisionRepository;

    public IssueAdInfo.IssueAdDecisionListInfo findIssueAdDecisionList(){
        List<AdDecision> decisionList = adDecisionRepository.findAll();
        return IssueAdInfo.IssueAdDecisionListInfo
            .from(decisionList.stream().map(IssueAdInfo.IssueAdDecisionInfo::from).toList());
    }

    public AdDecision findAdDecisionById(Long id){
        return adDecisionRepository.findById(id).orElseThrow(()->new BusinessException(NOT_FOUND_DECISION_ID));
    }
}
