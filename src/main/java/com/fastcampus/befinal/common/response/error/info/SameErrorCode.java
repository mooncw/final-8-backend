package com.fastcampus.befinal.common.response.error.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.SameAdCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum SameErrorCode implements ErrorCode {
    NOT_FOUND_SAME_ADVERTISEMENT_FOR_INSPECTION_ADVERTISEMENT(HttpStatus.BAD_REQUEST,
        SameAdCode.NOT_FOUND_SAME_ADVERTISEMENT_FOR_INSPECTION_ADVERTISEMENT, "검수 광고에 대한 해당 동일 광고가 존재하지 않습니다.");

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
