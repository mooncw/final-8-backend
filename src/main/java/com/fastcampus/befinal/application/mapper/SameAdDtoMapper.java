package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.fastcampus.befinal.presentation.dto.SameAdDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SameAdDtoMapper {
    SameAdDto.FindSimilarityListResponse from(SameAdInfo.FindSimilarityListInfo info);

    @Mapping(source = "postDateTime", target = "postDate", qualifiedByName = "toPostDateValue")
    SameAdDto.InspectionAdInfo from(SameAdInfo.InspectionAdInfo info);

    @Mapping(source = "similarity", target = "similarityPercent", qualifiedByName = "toSimilarityPercentValue")
    @Mapping(source = "postDateTime", target = "postDate", qualifiedByName = "toPostDateValue")
    SameAdDto.AdSimilarityInfo from(SameAdInfo.AdSimilarityInfo info);

    @Named("toSimilarityPercentValue")
    default Integer toSimilarityPercentValue(Double similarity) {
        return (int) Math.round(similarity * 100);
    }

    @Named("toPostDateValue")
    default String toPostDateValue(LocalDateTime postDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return postDateTime.format(formatter);
    }
}
