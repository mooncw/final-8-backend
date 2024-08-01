package com.fastcampus.befinal.common.response.error.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.ExampleCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ExampleErrorCode implements ErrorCode {
    EXAMPLE_ERROR(HttpStatus.BAD_REQUEST, ExampleCode.ERROR, "에러");

    private final HttpStatus httpStatus;
    private final Code code;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public Integer getCode() {
        return code.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
