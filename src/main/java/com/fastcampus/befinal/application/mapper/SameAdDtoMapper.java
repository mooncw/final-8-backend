package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.command.SameAdCommand;
import com.fastcampus.befinal.domain.info.SameAdInfo;
import com.fastcampus.befinal.presentation.dto.SameAdDto;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SameAdDtoMapper {
    SameAdCommand.SameAdFilterConditionRequest toTaskCommand(SameAdDto.SameAdFilterConditionRequest request);

    @Mapping(source = "adId", target = "adId", qualifiedByName = "toAdIdValue")
    SameAdDto.SameAdvertisementListInfo from(SameAdInfo.SameAdvertisementListInfo sameAdvertisementList);
    SameAdDto.SameTaskListInfo from(SameAdInfo.SameTaskListInfo sameTaskListResponse);

    @Named("toAdIdValue")
    default String toAdIdValue(String adId) {
        return adId.substring(6);
    }
}
