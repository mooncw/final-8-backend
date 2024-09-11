package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.annotation.ValidAdReviewType;
import com.fastcampus.befinal.common.util.RequestValidationGroups;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

import static com.fastcampus.befinal.common.contant.IssueAdConstant.*;

public class IssueAdDto {
    @Builder
    @ValidAdReviewType(message = INVALID_ADVERTISEMENT_REVIEW_TYPE,groups = RequestValidationGroups.CustomValidateGroup.class)
    public record IssueAdReviewRequest(
        @NotBlank(groups = RequestValidationGroups.NotBlankGroup.class)
        @Pattern(regexp = "Create|Update|Delete", message = INVALID_OPERATION_TYPE, groups = RequestValidationGroups.PatternGroup.class)
        String operationType,
        Long reviewId,
        String advertisementId,
        Integer provisionId,
        String sentence,
        String opinion
    ){}

    @Builder
    public record IssueAdResultDecisionRequest(
        @NotBlank(message = NOT_BLANK_ADVERTISE_ID, groups = RequestValidationGroups.NotBlankGroup.class)
        String advertisementId,
        @Min(value = DECISION_ID_MIN, message = INVALID_DECISION_ID, groups = RequestValidationGroups.SizeGroup.class)
        @Max(value = DECISION_ID_MAX, message = INVALID_DECISION_ID, groups = RequestValidationGroups.SizeGroup.class)
        Long decisionId
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
