package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.util.RequestValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

import static com.fastcampus.befinal.common.contant.SwaggerConstant.*;
import static com.fastcampus.befinal.common.contant.SwaggerConstant.SWAGGER_CATEGORY;
import static com.fastcampus.befinal.common.contant.TaskConstant.*;

public class SameAdDto {
    @Builder
    @Schema(description = "동일광고 필터 조건 request")
    public record SameAdFilterConditionRequest(
        @Schema(example = SWAGGER_AD_ID)
        @Pattern(regexp = "^[1-9][0-9]{3}(0[1-9]|1[0-2])[A-Z][0-9]{5}$", message = PATTERN_MISMATCH_AD_ID, groups = RequestValidationGroups.PatternGroup.class)
        String cursorId,

        @Schema(example = SWAGGER_KEYWORD)
        @Size(min = 2, message = MIN_LENGTH_KEYWORD, groups = RequestValidationGroups.SizeGroup.class)
        String keyword,

        @Schema(example = SWAGGER_PERIOD)
        @Pattern(regexp = "^[1-9][0-9]{3}-(0?[1-9]|1[0-2])-[12]$", message = PATTERN_MISMATCH_PERIOD, groups = RequestValidationGroups.PatternGroup.class)
        String period,

        @Schema(example = SWAGGER_ISSUE)
        Boolean same,

        @Schema(example = SWAGGER_MEDIA)
        List<String> media,

        @Schema(example = SWAGGER_CATEGORY)
        List<String> category
    ) {}

    @Builder
    public record SameAdvertisementListInfo(
        String adId,
        String media,
        String category,
        String product,
        String advertiser,
        Boolean same
    ) {}

    @Builder
    public record SameTaskListInfo(
        Long totalElements,
        String cursorId,
        List<SameAdvertisementListInfo> sameAdvertisementList
    ) {}

    @Builder
    public record InspectionAdInfo(
        String id,
        String product,
        String advertiser,
        String category,
        String postDate,
        String content
    ) {}

    @Builder
    public record AdSimilarityInfo(
        String id,
        String product,
        String advertiser,
        String category,
        String postDate,
        Integer similarityPercent,
        Integer sameSentenceCount
    ) {}

    @Builder
    public record FindSimilarityListResponse(
        InspectionAdInfo inspectionAdInfo,
        List<AdSimilarityInfo> adSimilarityInfoList
    ) {}

    @Builder
    public record FindSimilarityDetailResponse(
        String content,
        String sameSentence
    ) {}
}
