package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.AuthCode;
import com.fastcampus.befinal.common.response.code.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthSuccessCode implements SuccessCode {
    REISSUE_JWT_SUCCESS(HttpStatus.OK, AuthCode.REISSUE_JWT_SUCCESS, "JWT 재발급되었습니다."),
    SIGN_UP_SUCCESS(HttpStatus.OK, AuthCode.SIGN_UP_SUCCESS, "회원가입되었습니다."),
    CHECK_ID_DUPLICATION_SUCCESS(HttpStatus.OK, AuthCode.CHECK_ID_DUPLICATION_SUCCESS, "중복되지 않는 ID입니다."),
    SEND_CERTIFICATION_NUMBER_SUCCESS(HttpStatus.OK, AuthCode.SEND_CERTIFICATION_NUMBER_SUCCESS, "인증번호 요청 완료되었습니다."),
    CHECK_CERTIFICATION_NUMBER_SUCCESS(HttpStatus.OK, AuthCode.CHECK_CERTIFICATION_NUMBER_SUCCESS, "유효한 인증번호입니다."),
    SIGN_IN_SUCCESS(HttpStatus.OK, AuthCode.SIGN_IN_SUCCESS, "로그인되었습니다."),
    FIND_ID_SUCCESS(HttpStatus.OK, AuthCode.FIND_ID_SUCCESS, "아이디가 조회되었습니다."),
    FIND_PASSWORD_SUCCESS(HttpStatus.OK, AuthCode.FIND_PASSWORD_SUCCESS, "비밀번호가 조회되었습니다."),
    EDIT_PASSWORD_SUCCESS(HttpStatus.OK, AuthCode.EDIT_PASSWORD_SUCCESS, "비밀번호가 수정되었습니다.");

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
