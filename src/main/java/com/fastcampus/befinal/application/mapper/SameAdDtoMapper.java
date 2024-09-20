package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.fastcampus.befinal.presentation.dto.SameAdDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SameAdDtoMapper {
    SameAdDto.FindSimilarityListResponse from(SameAdInfo.FindSimilarityListInfo info);
}
