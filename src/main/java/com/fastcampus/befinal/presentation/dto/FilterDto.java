package com.fastcampus.befinal.presentation.dto;

import lombok.Builder;

public class FilterDto {
    @Builder
    public record FilterOptionResponse(
        String name,
        Long adCount
    ) {}
}
