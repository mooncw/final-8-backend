package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.AuthCode;
import com.fastcampus.befinal.common.response.code.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthSuccessCode implements SuccessCode {
    REISSUE_JWT(HttpStatus.OK, AuthCode.REISSUE_JWT, "JWT 재발급되었습니다.");

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
