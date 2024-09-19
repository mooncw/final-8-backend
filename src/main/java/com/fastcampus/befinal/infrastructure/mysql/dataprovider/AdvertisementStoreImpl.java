package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementStore;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import lombok.RequiredArgsConstructor;
import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.repository.AdvertisementRepositoryCustom;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataProvider
@RequiredArgsConstructor
public class AdvertisementStoreImpl implements AdvertisementStore {
    private final AdvertisementRepositoryCustom advertisementRepositoryCustom;

    @Override
    public void saveIssueAdDecision(IssueAdInfo.IssueAdDecisionSaveInfo info){
        info.advertisement().updateAdDecision(info.adDecision());
        info.advertisement().updateState(true);
        info.advertisement().updateTaskDatetime();
        info.advertisement().updateModifier(info.user());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAssignee(UserSummary userSummary, List<String> personalTaskAdIdList) {
        advertisementRepositoryCustom.updateAssignee(userSummary, personalTaskAdIdList);
    }
}
