package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.presentation.dto.DashboardDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DashboardDtoMapper {
    DashboardDto.DashboardDataResponse from(DashboardInfo.DashboardDataInfo dataInfo);
    DashboardDto.AdCount from(DashboardInfo.AdCount adCount);
    DashboardDto.DailyDone from(DashboardInfo.DailyDone dailyDone);

    @Mapping(source = "adTaskDateTime", target = "adTaskDateTime", qualifiedByName = "toZonedDateTime")
    DashboardDto.RecentDone from(DashboardInfo.RecentDone recentDone);

    @Named("toZonedDateTime")
    default ZonedDateTime toZonedDateTime(LocalDateTime adTaskDateTime) {
        return adTaskDateTime.atZone(ZoneId.of("Asia/Seoul"));
    }
}
