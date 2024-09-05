package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface IssueAdDtoMapper {
    @Mapping(source = "issueAdDetail.id", target = "id")
    @Mapping(source = "issueAdDetail.product", target = "product")
    @Mapping(source = "issueAdDetail.advertiser", target = "advertiser")
    @Mapping(source = "issueAdDetail.category", target = "category")
    @Mapping(source = "issueAdDetail.postDateTime", target = "postDateTime")
    @Mapping(source = "issueAdDetail.assigneeName", target = "assigneeName")
    @Mapping(source = "issueAdDetail.modifierName", target = "modifierName")
    @Mapping(source = "issueAdDetail.content", target = "content")
    @Mapping(source = "issueAdReviewList", target = "reviewList")
    IssueAdDto.IssueAdDetailResponse from(IssueAdInfo.IssueAdDetailAllInfo info);
}
