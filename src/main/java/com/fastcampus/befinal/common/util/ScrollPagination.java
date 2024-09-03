package com.fastcampus.befinal.common.util;

import lombok.Builder;

import java.util.List;

@Builder
public record ScrollPagination<T>(
    Long totalElements,
    Object currentCursorId,
    List<T> contents
) {
    public static <T> ScrollPagination<T> of(Long totalElements, Long cursorId, List<T> contents) {
        return ScrollPagination.<T>builder()
            .totalElements(totalElements)
            .currentCursorId(cursorId)
            .contents(contents)
            .build();
    }

    public static <T> ScrollPagination<T> of(Long totalElements, String cursorId, List<T> contents) {
        return ScrollPagination.<T>builder()
            .totalElements(totalElements)
            .currentCursorId(cursorId)
            .contents(contents)
            .build();
    }
}
