package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.util.RequestValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

import static com.fastcampus.befinal.common.contant.SwaggerConstant.*;
import static com.fastcampus.befinal.common.contant.TaskConstant.*;

public class TaskDto {
    @Builder
    @Schema(description = "필터 조건 request")
    public record FilterConditionRequest(
        @Valid
        CursorInfo cursorInfo,

        @Schema(example = SWAGGER_KEYWORD)
        @Size(min = 2, message = MIN_LENGTH_KEYWORD, groups = RequestValidationGroups.SizeGroup.class)
        String keyword,

        @Schema(example = SWAGGER_PERIOD)
        @Pattern(regexp = "^[1-9][0-9]{3}-(0?[1-9]|1[0-2])-[12]$", message = PATTERN_MISMATCH_PERIOD, groups = RequestValidationGroups.PatternGroup.class)
        String period,

        @Schema(example = SWAGGER_STATE)
        Boolean state,

        @Schema(example = SWAGGER_ISSUE)
        Boolean issue,

        @Schema(example = SWAGGER_MEDIA)
        List<String> media,

        @Schema(example = SWAGGER_CATEGORY)
        List<String> category
    ) {}

    @Builder
    @Schema(description = "Cursor 조건 request")
    public record CursorInfo(
        @Schema(example = SWAGGER_STATE)
        Boolean cursorState,

        @Schema(example = SWAGGER_AD_ID)
        @NotBlank(message = NOT_BLANK_CURSOR_AD_ID, groups = RequestValidationGroups.NotBlankGroup.class)
        @Pattern(regexp = "^[A-Z]\\d{5}$", message = PATTERN_MISMATCH_AD_ID, groups = RequestValidationGroups.PatternGroup.class)
        String cursorId
    ) {}

    @Builder
    public record TaskResponse(
        AdCountInfo adCount,
        TaskListInfo taskList
    ) {}

    @Builder
    public record AdCountInfo(
        Integer myTotalAd,
        Integer myDoneAd,
        Integer myNotDoneAd
    ) {}

    @Builder
    public record AdvertisementListInfo (
        String adId,
        String media,
        String category,
        String product,
        String advertiser,
        Boolean state,
        Boolean issue
    ) {}

    @Builder
    public record TaskListInfo(
        Long totalElements,
        CursorInfo cursorInfo,
        List<AdvertisementListInfo> advertisementList
    ) {}

    @Builder
    public record IssueAdvertisementListInfo (
        String adId,
        String media,
        String category,
        String product,
        String advertiser,
        Boolean state,
        Boolean issue,
        String assigneeName
    ) {}

    @Builder
    public record IssueAdListInfo(
        Long totalElements,
        CursorInfo cursorInfo,
        List<IssueAdvertisementListInfo> advertisementList
    ) {}
}
