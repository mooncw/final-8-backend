package com.fastcampus.befinal.infrastructure.mysql.mapper;

import com.fastcampus.befinal.domain.entity.AdReview;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AdReviewEntityMapper {

    AdReview from(IssueAdInfo.IssueAdReviewSaveEntityInfo info);
}
