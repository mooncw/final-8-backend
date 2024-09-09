package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.FilterFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.response.success.info.FilterSuccessCode;
import com.fastcampus.befinal.presentation.dto.FilterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/filter-options")
@Tag(name = "Filter Options", description = "필터 조건 리스트 API")
public class FilterController {
    private final FilterFacade filterFacade;

    @GetMapping("/media")
    @Operation(summary = "매체명 필터 옵션 검색", description = "키워드에 따른 매체명 필터 옵션 검색합니다.")
    public ResponseEntity<AppApiResponse<List<FilterDto.FilterOptionResponse>>> searchMediaOptions(
            @RequestParam(required = false) String keyword
    ) {
        List<FilterDto.FilterOptionResponse> response = filterFacade.searchMediaOptions(keyword);
        return ResponseEntityFactory.toResponseEntity(FilterSuccessCode.FILTER_MEDIA_LIST, response);
    }

    @GetMapping("/category")
    @Operation(summary = "업종명 필터 옵션 검색", description = "키워드에 따른 업종명 필터 옵션 검색합니다.")
    public ResponseEntity<AppApiResponse<List<FilterDto.FilterOptionResponse>>> searchCategoryOptions(
            @RequestParam(required = false) String keyword
    ) {
        List<FilterDto.FilterOptionResponse> response = filterFacade.searchCategoryOptions(keyword);
        return ResponseEntityFactory.toResponseEntity(FilterSuccessCode.FILTER_CATEGORY_LIST, response);
    }
}
