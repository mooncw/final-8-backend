package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.presentation.dto.DashboardDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DashboardDtoMapper {
    DashboardDto.DashboardDataResponse from(DashboardInfo.DashboardDataInfo dataInfo);
    DashboardDto.AdCount from(DashboardInfo.AdCount adCount);
    DashboardDto.DailyDone from(DashboardInfo.DailyDone dailyDone);

    @Mapping(source = "adTaskDateTime", target = "adTaskDateTime", qualifiedByName = "toAdTaskDateTimeValue")
    DashboardDto.RecentDone from(DashboardInfo.RecentDone recentDone);

    @Named("toAdTaskDateTimeValue")
    default String toAdTaskDateTimeValue(LocalDateTime finalLoginDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return finalLoginDateTime.format(formatter);
    }
}
