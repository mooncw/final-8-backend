package com.fastcampus.befinal.common.response.error.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.JwtTokenCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum JwtTokenErrorCode implements ErrorCode {
    NOT_VALID_JWT_TOKEN(HttpStatus.BAD_REQUEST, JwtTokenCode.NOT_VALID_JWT_TOKEN, "유효한 JWT이 아닙니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.BAD_REQUEST, JwtTokenCode.EXPIRED_JWT_TOKEN, "만료된 JWT입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.BAD_REQUEST, JwtTokenCode.UNSUPPORTED_JWT_TOKEN, "지원되지 않는 형식의 JWT입니다."),
    ILLEGAL_JWT_TOKEN(HttpStatus.BAD_REQUEST, JwtTokenCode.ILLEGAL_JWT_TOKEN, "부적절한 JWT입니다.");

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
