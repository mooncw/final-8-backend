package com.fastcampus.befinal.common.response.error.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.JwtCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum JwtErrorCode implements ErrorCode {
    NOT_VALID_JWT(HttpStatus.UNAUTHORIZED, JwtCode.NOT_VALID_JWT, "유효한 JWT이 아닙니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, JwtCode.EXPIRED_JWT, "만료된 JWT입니다."),
    UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED, JwtCode.UNSUPPORTED_JWT, "지원되지 않는 형식의 JWT입니다."),
    ILLEGAL_JWT(HttpStatus.UNAUTHORIZED, JwtCode.ILLEGAL_JWT, "부적절한 JWT입니다."),
    NOT_EXPIRED_ACCESSTOKEN(HttpStatus.UNAUTHORIZED, JwtCode.NOT_EXPIRED_ACCESSTOKEN, "만료되지 않은 AccessToken입니다."),
    NOT_FOUND_REFRESHTOKEN(HttpStatus.UNAUTHORIZED, JwtCode.NOT_FOUND_REFRESHTOKEN, "존재하지 않는 RefreshToken입니다."),
    INCONSISTENT_REFRESHTOKEN(HttpStatus.UNAUTHORIZED, JwtCode.INCONSISTENT_REFRESHTOKEN, "일치하지 않는 RefreshToken입니다.");

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
