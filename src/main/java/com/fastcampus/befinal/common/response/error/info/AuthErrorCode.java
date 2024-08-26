package com.fastcampus.befinal.common.response.error.info;

import com.fastcampus.befinal.common.response.code.AuthCode;
import com.fastcampus.befinal.common.response.code.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    DENIED_ACCESS(HttpStatus.FORBIDDEN, AuthCode.DENIED_ACCESS, "접근 권한이 없습니다."),
    SIGNUP_USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, AuthCode.SIGNUP_USER_ALREADY_EXIST, "이미 회원가입 된 유저입니다."),
    USER_ID_ALREADY_EXIST(HttpStatus.UNAUTHORIZED, AuthCode.USER_ID_ALREADY_EXIST, "이미 존재하는 ID입니다.");

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
