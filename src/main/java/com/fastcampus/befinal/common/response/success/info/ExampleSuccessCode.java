package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.ExampleCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ExampleSuccessCode implements SuccessCode {
    MY_ALERT_ALL_FOUND(HttpStatus.OK, ExampleCode.SUCCESS, "성공");

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
