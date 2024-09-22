package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.command.SameAdCommand;
import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.fastcampus.befinal.presentation.dto.SameAdDto;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SameAdDtoMapper {
    SameAdCommand.SameAdFilterConditionRequest toTaskCommand(SameAdDto.SameAdFilterConditionRequest request);

    SameAdDto.SameAdvertisementListInfo from(SameAdInfo.SameAdvertisementListInfo sameAdvertisementList);
    SameAdDto.SameTaskListInfo from(SameAdInfo.SameTaskListInfo sameTaskListResponse);

    SameAdDto.FindSimilarityListResponse from(SameAdInfo.FindSimilarityListInfo info);

    @Mapping(source = "postDateTime", target = "postDate", qualifiedByName = "toPostDateValue")
    SameAdDto.InspectionAdInfo from(SameAdInfo.InspectionAdInfo info);

    @Mapping(source = "similarity", target = "similarityPercent", qualifiedByName = "toSimilarityPercentValue")
    @Mapping(source = "postDateTime", target = "postDate", qualifiedByName = "toPostDateValue")
    SameAdDto.AdSimilarityInfo from(SameAdInfo.AdSimilarityInfo info);

    @Named("toSimilarityPercentValue")
    default Integer toSimilarityPercentValue(BigDecimal similarity) {
        BigDecimal similarityPercent = similarity.multiply(new BigDecimal(100));

        return similarityPercent.setScale(0, RoundingMode.HALF_UP).intValue();
    }

    @Named("toPostDateValue")
    default String toPostDateValue(LocalDateTime postDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return postDateTime.format(formatter);
    }
}
