package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.info.FilterInfo;
import com.fastcampus.befinal.presentation.dto.FilterDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FilterDtoMapper {
    List<FilterDto.FilterOptionResponse> from(List<FilterInfo.FilterOptionInfo> filterOption);
}
