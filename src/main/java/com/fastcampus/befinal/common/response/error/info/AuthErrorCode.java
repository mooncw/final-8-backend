package com.fastcampus.befinal.common.response.error.info;

import com.fastcampus.befinal.common.response.code.AuthCode;
import com.fastcampus.befinal.common.response.code.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    DENIED_ACCESS(HttpStatus.FORBIDDEN, AuthCode.DENIED_ACCESS, "접근 권한이 없습니다."),
    SIGNUP_USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, AuthCode.SIGNUP_USER_ALREADY_EXIST, "이미 회원가입 된 유저입니다."),
    USER_ID_ALREADY_EXIST(HttpStatus.UNAUTHORIZED, AuthCode.USER_ID_ALREADY_EXIST, "이미 존재하는 아이디입니다."),
    PHONE_NUMBER_ALREADY_EXIST(HttpStatus.UNAUTHORIZED, AuthCode.PHONE_NUMBER_ALREADY_EXIST, "이미 존재하는 연락처입니다."),
    NOT_FOUND_CERTIFICATION_NUMBER(HttpStatus.UNAUTHORIZED, AuthCode.NOT_FOUND_CERTIFICATION_NUMBER, "존재하지 않는 인증번호입니다."),
    INCONSISTENT_CERTIFICATION_NUMBER(HttpStatus.UNAUTHORIZED, AuthCode.INCONSISTENT_CERTIFICATION_NUMBER, "일치하지 않는 인증번호입니다."),
    INVALID_CERTIFICATION_TYPE(HttpStatus.UNAUTHORIZED, AuthCode.INVALID_CERTIFICATION_TYPE, "유효하지 않는 인증 타입입니다."),
    INVALID_ID_CHECK_TOKEN(HttpStatus.UNAUTHORIZED, AuthCode.INVALID_ID_CHECK_TOKEN, "유효하지 않는 아이디 중복 확인 토큰입니다."),
    INVALID_CERTIFICATION_NUMBER_CHECK_TOKEN(HttpStatus.UNAUTHORIZED, AuthCode.INVALID_CERTIFICATION_NUMBER_CHECK_TOKEN,
        "유효하지 않는 인증번호 확인 토큰입니다."),
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED, AuthCode.NOT_FOUND_USER, "존재하지 않는 유저입니다."),
    INCONSISTENT_USER_PASSWORD(HttpStatus.UNAUTHORIZED, AuthCode.INCONSISTENT_USER_PASSWORD, "일치하지 않는 비밀번호입니다."),
    INVALID_AUTHORITY(HttpStatus.UNAUTHORIZED, AuthCode.INVALID_AUTHORITY, "유효하지 않는 권한입니다."),
    INCONSISTENT_USER_INFO(HttpStatus.UNAUTHORIZED, AuthCode.INCONSISTENT_USER_INFO, "일치하지 않는 정보입니다."),
    INVALID_PASSWORD_RESET_TOKEN(HttpStatus.UNAUTHORIZED, AuthCode.INVALID_PASSWORD_RESET_TOKEN, "유효하지 않은 비밀번호 초기화 토큰입니다.");

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
