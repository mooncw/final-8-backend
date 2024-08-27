package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.info.DashBoardInfo;
import com.fastcampus.befinal.presentation.dto.DashBoardDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DashBoardDtoMapper {
    DashBoardDto.DashBoardDataResponse from(DashBoardInfo.DashBoardDataInfo dataInfo);
    DashBoardDto.AdCount from(DashBoardInfo.AdCount adCount);
    DashBoardDto.DailyDone from(DashBoardInfo.DailyDone dailyDone);
    DashBoardDto.RecentDone from(DashBoardInfo.RecentDone recentDone);

}
