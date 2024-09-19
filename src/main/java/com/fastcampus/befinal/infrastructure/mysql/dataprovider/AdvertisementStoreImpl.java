package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementStore;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class AdvertisementStoreImpl implements AdvertisementStore {

    public void saveIssueAdDecision(IssueAdInfo.IssueAdDecisionSaveInfo info){
        info.advertisement().updateAdDecision(info.adDecision());
        info.advertisement().updateState(true);
        info.advertisement().updateTaskDatetime();
        info.advertisement().updateModifier(info.user());
    }
}
