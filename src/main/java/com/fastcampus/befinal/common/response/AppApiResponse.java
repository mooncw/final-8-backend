package com.fastcampus.befinal.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record AppApiResponse<T>(
    Integer code,
    String message,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data
) {
    public static <T> AppApiResponse<T> of(
        ResponseCode responseCode,
        T data) {
        return AppApiResponse.<T>builder()
            .code(responseCode.getCode())
            .message(responseCode.getMessage())
            .data(data)
            .build();
    }

    public static <T> AppApiResponse<T> of(
        ResponseCode responseCode
    ) {
        return AppApiResponse.<T>builder()
            .code(responseCode.getCode())
            .message(responseCode.getMessage())
            .build();
    }
}
