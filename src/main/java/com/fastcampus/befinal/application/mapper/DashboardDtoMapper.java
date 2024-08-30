package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.presentation.dto.DashboardDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DashboardDtoMapper {
    DashboardDto.DashboardDataResponse from(DashboardInfo.DashboardDataInfo dataInfo);
    DashboardDto.AdCount from(DashboardInfo.AdCount adCount);
    DashboardDto.DailyDone from(DashboardInfo.DailyDone dailyDone);
    DashboardDto.RecentDone from(DashboardInfo.RecentDone recentDone);

}
