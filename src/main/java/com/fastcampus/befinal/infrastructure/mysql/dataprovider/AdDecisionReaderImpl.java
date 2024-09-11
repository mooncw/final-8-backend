package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.AdDecisionReader;
import com.fastcampus.befinal.domain.entity.AdDecision;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.repository.AdDecisionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DataProvider
@RequiredArgsConstructor
public class AdDecisionReaderImpl implements AdDecisionReader {
    private final AdDecisionRepository adDecisionRepository;

    public List<IssueAdInfo.IssueAdDecisionInfo> findIssueAdDecisionList(){
        List<AdDecision> decisionList = adDecisionRepository.findAll();
        return decisionList.stream().map(IssueAdInfo.IssueAdDecisionInfo::from).toList();
    }
}
