package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.annotation.ValidAdReviewType;
import com.fastcampus.befinal.common.util.RequestValidationGroups;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class IssueAdDto {
    @Builder
    @ValidAdReviewType(groups = RequestValidationGroups.CustomValidateGroup.class)
    public record IssueAdReviewRequest(
        @NotBlank(groups = RequestValidationGroups.NotBlankGroup.class)
        @Pattern(regexp = "Create|Update|Delete", groups = RequestValidationGroups.PatternGroup.class)
        String operationType,
        Long reviewId,
        String advertisementId,
        Integer provisionId,
        String sentence,
        String opinion
    ){}

    @Builder
    public record IssueAdDetailResponse(
        String id,
        String product,
        String advertiser,
        String category,
        LocalDate postDate,
        String assigneeName,
        String modifierName,
        String content,
        List<IssueAdInfo.IssueAdReviewInfo> reviewList
    ){}

    @Builder
    public record IssueAdProvisionResponse(
        Integer id,
        Integer article,
        String content
    ){}

    @Builder
    public record IssueAdDecisionResponse(
        Long id,
        String decision
    ){}
}
