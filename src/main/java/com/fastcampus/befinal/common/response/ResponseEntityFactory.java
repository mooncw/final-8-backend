package com.fastcampus.befinal.common.response;

import org.springframework.http.ResponseEntity;

public class ResponseEntityFactory {
    public static ResponseEntity<AppApiResponse> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getHttpStatus()).body(AppApiResponse.of(responseCode));
    }

    public static <T> ResponseEntity<AppApiResponse<T>> toResponseEntity(ResponseCode responseCode, T data) {
        return ResponseEntity.status(responseCode.getHttpStatus()).body(AppApiResponse.of(responseCode, data));
    }
}
