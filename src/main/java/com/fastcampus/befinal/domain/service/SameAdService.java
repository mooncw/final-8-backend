package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.SameAdCommand;
import com.fastcampus.befinal.domain.info.SameAdInfo;

public interface SameAdService {

    SameAdInfo.SameTaskListInfo findSameAdList(SameAdCommand.SameAdFilterConditionRequest command);

    SameAdInfo.FindSimilarityListInfo findSimilarityList(String inspectionAdvertisementId);
}
