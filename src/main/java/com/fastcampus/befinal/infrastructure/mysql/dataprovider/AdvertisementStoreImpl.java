package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementStore;
import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.repository.AdvertisementRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataProvider
@RequiredArgsConstructor
public class AdvertisementStoreImpl implements AdvertisementStore {
    private final AdvertisementRepositoryCustom advertisementRepositoryCustom;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAssignee(UserSummary userSummary, List<String> personalTaskAdIdList) {
        advertisementRepositoryCustom.updateAssignee(userSummary, personalTaskAdIdList);
    }
}
