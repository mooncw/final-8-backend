package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.entity.Advertisement;
import com.fastcampus.befinal.domain.info.*;
import com.fastcampus.befinal.domain.repository.AdvertisementRepository;
import com.fastcampus.befinal.domain.repository.AdvertisementRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.fastcampus.befinal.common.response.error.info.IssueAdErrorCode.NOT_FOUND_ADVERTISEMENT_ID;

@DataProvider
@RequiredArgsConstructor
public class AdvertisementReaderImpl implements AdvertisementReader {
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementRepositoryCustom advertisementRepositoryCustom;

    @Override
    public DashboardInfo.AdCount findAdCount(String userId) {
        return advertisementRepositoryCustom.getAdCount(userId);
    }

    @Override
    public List<DashboardInfo.DailyDone> findDailyDone(String userId) {
        return advertisementRepositoryCustom.getDailyDoneList(userId);
    }

    @Override
    public List<DashboardInfo.RecentDone> findRecentDone(String userId) {
        return advertisementRepositoryCustom.getRecentDoneList(userId);
    }

    @Override
    public ScrollPagination<TaskInfo.CursorInfo, TaskInfo.AdvertisementListInfo> findIssueAdList(TaskCommand.FilterConditionRequest command){
        return advertisementRepositoryCustom.findIssueAdListScrollByCursorInfo(command);
    }

    @Override
    public IssueAdInfo.IssueAdDetailInfo findIssueAdDetail(String advertisementId){
        return advertisementRepositoryCustom.findIssueAdDetail(advertisementId)
            .orElseThrow(()-> new BusinessException(NOT_FOUND_ADVERTISEMENT_ID));
    }

    @Override
    public Advertisement findAdvertisementById(String advertisementId){
        return advertisementRepository.findById(advertisementId).
            orElseThrow(()-> new BusinessException(NOT_FOUND_ADVERTISEMENT_ID));
    }

    @Override
    public ScrollPagination<String, AdminInfo.UnassignedAdInfo> findUnassignedAdScroll(String cursorId) {
        return advertisementRepositoryCustom.findUnassignedAdScroll(cursorId);
    }

    @Override
    public Long countUnassigned() {
        return advertisementRepository.countByAssigneeIsNull();
    }

    @Override
    public List<AdminInfo.UnassignedAdIdInfo> findAllUnassignedAdId(Long amount) {
        return advertisementRepositoryCustom.findAllIdByAssigneeIsNull(amount);
    }

    @Override
    public SameAdInfo.InspectionAdInfo findInspectionAdInfo(String advertisementId) {
        return advertisementRepositoryCustom.findInspectionAdInfo(advertisementId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_ADVERTISEMENT_ID));
    }
}
