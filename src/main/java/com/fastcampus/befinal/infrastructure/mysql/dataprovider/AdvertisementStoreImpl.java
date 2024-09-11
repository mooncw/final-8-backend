package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementStore;
import com.fastcampus.befinal.domain.entity.AdDecision;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class AdvertisementStoreImpl implements AdvertisementStore {
    private final EntityManager entityManager;

    public void saveIssueAdDecision(IssueAdInfo.IssueAdDecisionSaveInfo info){
        info.advertisement().updateAdDecision(info.adDecision());
        info.advertisement().updateState(true);
    }
}
