package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.service.IssueAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IssueAdServiceImpl implements IssueAdService {
    private final AdvertisementReader advertisementReader;

    @Override
    @Transactional(readOnly = true)
    public IssueAdInfo.IssueAdDetailAllInfo findIssueAdDetail(String advertisementId){
        return advertisementReader.findIssueAdDetail(advertisementId);
    }
}
