package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.AdProvisionReader;
import com.fastcampus.befinal.domain.entity.AdProvision;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.repository.AdProvisionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DataProvider
@RequiredArgsConstructor
public class AdProvisionReaderImpl implements AdProvisionReader {
    private final AdProvisionRepository adProvisionRepository;

    public List<IssueAdInfo.IssueAdProvisionInfo> findIssueAdProvisionList(){
        List<AdProvision> provisionList = adProvisionRepository.findAll();
        return provisionList.stream().map(IssueAdInfo.IssueAdProvisionInfo::from).toList();
    }
}
