package com.fastcampus.befinal.domain.info;

import lombok.Builder;

public class FilterInfo {
    @Builder
    public record FilterOptionInfo(
        String name,
        Long adCount
    ) {
        public static FilterOptionInfo of(String name, Long adCount) {
            return FilterOptionInfo.builder()
                .name(name)
                .adCount(adCount)
                .build();
        }
    }
}
