package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.FilterFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.response.success.info.FilterSuccessCode;
import com.fastcampus.befinal.presentation.dto.FilterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/filter-options")
@Tag(name = "Filter Options", description = "필터 조건 리스트 API")
public class FilterController {
    private final FilterFacade filterFacade;

    @GetMapping("/media")
    @Operation(summary = "필터 매체명 리스트 조회 - Param default 값은 null")
    @ApiResponse(responseCode = "200", description = "필터 매체명 리스트 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 3600, " +
                    "\"message\": \"필터 매체명 리스트 조회되었습니다.\", " +
                    "\"data\": [" +
                    "{ " +
                    "\"name\": \"동아일보\", " +
                    "\"adCount\": 8 " +
                    "}, " +
                    "{ " +
                    "\"name\": \"조선일보\", " +
                    "\"adCount\": 6 " +
                    "}" +
                    "]" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<List<FilterDto.FilterOptionResponse>>> searchMediaOptions(
        @Valid
        @ModelAttribute FilterDto.ConditionRequest request
    ) {
        List<FilterDto.FilterOptionResponse> response = filterFacade.searchMediaOptions(request);
        return ResponseEntityFactory.toResponseEntity(FilterSuccessCode.FILTER_MEDIA_LIST, response);
    }

    @GetMapping("/category")
    @Operation(summary = "필터 업종명 리스트 조회 - Param default 값은 null")
    @ApiResponse(responseCode = "200", description = "필터 업종명 리스트 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 3601, " +
                    "\"message\": \"필터 업종명 리스트 조회되었습니다.\", " +
                    "\"data\": [" +
                    "{ " +
                    "\"name\": \"가정용품\", " +
                    "\"adCount\": 8 " +
                    "}, " +
                    "{ " +
                    "\"name\": \"의료\", " +
                    "\"adCount\": 6 " +
                    "}" +
                    "]" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<List<FilterDto.FilterOptionResponse>>> searchCategoryOptions(
        @Valid
        @ModelAttribute FilterDto.ConditionRequest request
    ) {
        List<FilterDto.FilterOptionResponse> response = filterFacade.searchCategoryOptions(request);
        return ResponseEntityFactory.toResponseEntity(FilterSuccessCode.FILTER_CATEGORY_LIST, response);
    }
}
