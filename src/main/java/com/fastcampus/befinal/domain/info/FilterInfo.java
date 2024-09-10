package com.fastcampus.befinal.domain.info;

import lombok.Builder;

public class FilterInfo {
    @Builder
    public record FilterOptionInfo(
        String name,
        int adCount
    ) {
        public static FilterOptionInfo of(String name, int adCount) {
            return FilterOptionInfo.builder()
                .name(name)
                .adCount(adCount)
                .build();
        }
    }
}
