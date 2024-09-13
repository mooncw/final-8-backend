package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.annotation.ValidAdReviewType;
import com.fastcampus.befinal.common.util.RequestValidationGroups;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

import static com.fastcampus.befinal.common.contant.IssueAdConstant.*;
import static com.fastcampus.befinal.common.contant.SwaggerConstant.*;

public class IssueAdDto {
    @Builder
    @ValidAdReviewType(message = INVALID_ADVERTISEMENT_REVIEW_TYPE)
    public record IssueAdReview(
        @Schema(description = SWAGGER_OPERATION_TYPE_DESCRIPTION, example = SWAGGER_OPERATION_TYPE, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = NOT_BLANK_OPERATION_TYPE)
        @Pattern(regexp = "Create|Update|Delete", message = INVALID_OPERATION_TYPE)
        String operationType,
        @Schema(description = SWAGGER_REVIEW_ID_DESCRIPTION, example = SWAGGER_REVIEW_ID, nullable = true)
        Long reviewId,
        @Schema(description = SWAGGER_ADVERTISEMENT_ID_DESCRIPTION, example = SWAGGER_ADVERTISEMENT_ID, nullable = true)
        String advertisementId,
        @Schema(description = SWAGGER_PROVISION_ID_DESCRIPTION, example = SWAGGER_PROVISION_ID, nullable = true)
        Integer provisionId,
        @Schema(description = SWAGGER_SENTENCE_DESCRIPTION, example = SWAGGER_SENTENCE, nullable = true)
        String sentence,
        @Schema(description = SWAGGER_OPINION_DESCRIPTION, example = SWAGGER_OPINION, nullable = true)
        String opinion
    ) {}

    @Builder
    public record IssueAdReviewRequest(
        @NotEmpty(message = NOT_EMPTY_REVIEW_LIST, groups = RequestValidationGroups.NotEmptyGroup.class)
        List<@Valid IssueAdReview> reviewList
    ) {}

    @Builder
    public record IssueAdResultDecisionRequest(
        @Schema(example = SWAGGER_ADVERTISEMENT_ID)
        @NotBlank(message = NOT_BLANK_ADVERTISE_ID, groups = RequestValidationGroups.NotBlankGroup.class)
        String advertisementId,
        @Schema(example = SWAGGER_DECISION_ID)
        @NotNull(message = NOT_BLANK_DECISION_ID, groups = RequestValidationGroups.NotNullGroup.class)
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
