package com.fastcampus.befinal.common.response;

import org.springframework.http.ResponseEntity;

public class ResponseEntityFactory {
    public static ResponseEntity<ApiResponse> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getHttpStatus()).body(ApiResponse.of(responseCode));
    }

    public static <T> ResponseEntity<ApiResponse<T>> toResponseEntity(ResponseCode responseCode, T data) {
        return ResponseEntity.status(responseCode.getHttpStatus()).body(ApiResponse.of(responseCode, data));
    }
}
