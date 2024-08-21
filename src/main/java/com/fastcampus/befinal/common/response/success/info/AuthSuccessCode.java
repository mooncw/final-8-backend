package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.AuthCode;
import com.fastcampus.befinal.common.response.code.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthSuccessCode implements SuccessCode {
    REISSUE_JWT_SUCCESS(HttpStatus.OK, AuthCode.REISSUE_JWT_SUCCESS, "JWT 재발급되었습니다."),
    SIGNUP_SUCCESS(HttpStatus.OK, AuthCode.SIGNUP_SUCCESS, "회원가입되었습니다.");

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
