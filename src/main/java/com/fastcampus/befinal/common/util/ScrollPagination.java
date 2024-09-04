package com.fastcampus.befinal.common.util;

import lombok.Builder;

import java.util.List;

@Builder
public record ScrollPagination<K, T>(
    Long totalElements,
    K currentCursorId,
    List<T> contents
) {
    public static <K, T> ScrollPagination<K, T> of(Long totalElements, K cursorId, List<T> contents) {
        return ScrollPagination.<K, T>builder()
            .totalElements(totalElements)
            .currentCursorId(cursorId)
            .contents(contents)
            .build();
    }
}
