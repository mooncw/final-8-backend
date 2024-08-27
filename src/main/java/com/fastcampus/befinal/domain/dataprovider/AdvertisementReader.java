package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.DashBoardInfo;

public interface AdvertisementReader {
    DashBoardInfo.DashBoardDataInfo findDashBoardData(String userId);

}
