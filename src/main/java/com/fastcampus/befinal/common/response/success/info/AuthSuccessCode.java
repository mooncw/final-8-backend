package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.AuthCode;
import com.fastcampus.befinal.common.response.code.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthSuccessCode implements SuccessCode {
    REISSUE_JWT_SUCCESS(HttpStatus.OK, AuthCode.REISSUE_JWT_SUCCESS, "JWT 재발급되었습니다."),
    SIGNUP_SUCCESS(HttpStatus.OK, AuthCode.SIGNUP_SUCCESS, "회원가입되었습니다."),
    CHECK_ID_DUPLICATION_SUCCESS(HttpStatus.OK, AuthCode.CHECK_ID_DUPLICATION_SUCCESS, "중복되지 않는 ID입니다."),
    SEND_CERTIFICATION_NUMBER_SUCCESS(HttpStatus.OK, AuthCode.SEND_CERTIFICATION_NUMBER_SUCCESS, "인증번호 요청 완료되었습니다."),
    CHECK_CERTIFICATION_NUMBER_SUCCESS(HttpStatus.OK, AuthCode.CHECK_CERTIFICATION_NUMBER_SUCCESS, "유효한 인증번호입니다.");

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
