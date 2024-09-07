package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.common.util.RequestValidationGroups;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

import static com.fastcampus.befinal.common.contant.TaskConstant.MIN_LENGTH_KEYWORD;

public class TaskDto {
    @Builder
    public record FilterConditionRequest(
            CursorInfo cursorInfo,
            @Size(min = 2, message = MIN_LENGTH_KEYWORD, groups = RequestValidationGroups.SizeGroup.class)
            String keyword,
            String period,
            Boolean state,
            Boolean issue,
            List<String> media,
            List<String> category
    ) {}

    @Builder
    public record CursorInfo(
            Boolean cursorState,
            String cursorId
    ) {}

    @Builder
    public record TaskResponse(
            AdCountResponse adCount,
            TaskListResponse taskList
    ) {}

    @Builder
    public record AdCountResponse(
            Integer myTotalAd,
            Integer myDoneAd,
            Integer myNotDoneAd
    ) {}

    @Builder
    public record AdvertisementListResponse (
            String adId,
            String media,
            String category,
            String product,
            String advertiser,
            Boolean state,
            Boolean issue
    ) {}

    @Builder
    public record TaskListResponse(
            Long totalElements,
            CursorInfo cursorInfo,
            List<AdvertisementListResponse> advertisementList
    ){}
}
