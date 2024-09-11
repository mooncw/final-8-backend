package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.dataprovider.AdProvisionReader;
import com.fastcampus.befinal.domain.entity.AdProvision;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.repository.AdProvisionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.fastcampus.befinal.common.response.error.info.IssueAdErrorCode.NOT_FOUND_PROVISION_ID;

@DataProvider
@RequiredArgsConstructor
public class AdProvisionReaderImpl implements AdProvisionReader {
    private final AdProvisionRepository adProvisionRepository;

    public List<IssueAdInfo.IssueAdProvisionInfo> findIssueAdProvisionList(){
        List<AdProvision> provisionList = adProvisionRepository.findAll();
        return provisionList.stream().map(IssueAdInfo.IssueAdProvisionInfo::from).toList();
    }

    public AdProvision findAdProvisionById(Integer id){
        return adProvisionRepository.findById(id).orElseThrow(()->new BusinessException(NOT_FOUND_PROVISION_ID));
    }
}
