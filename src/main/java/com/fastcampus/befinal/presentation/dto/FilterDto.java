package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.util.RequestValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.fastcampus.befinal.common.contant.TaskConstant.*;

public class FilterDto {
    @Builder
    public record FilterOptionResponse(
        String name,
        Long adCount
    ) {}

    @Builder
    @Schema(description = "Filter 매체/업종명 리스트 조회 request")
    public record ConditionRequest(
        @Schema(example = SWAGGER_KEYWORD)
        String keyword,

        @Schema(example = SWAGGER_PERIOD)
        @Pattern(regexp = "^\\d{4}-(0?[1-9]|1[0-2])-[12]$", message = PATTERN_MISMATCH_PERIOD, groups = RequestValidationGroups.PatternGroup.class)
        String period
    ) {}
}
