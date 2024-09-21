package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.SameAdCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum SameAdSuccessCode implements SuccessCode{
    GET_SAME_ADVERTISEMENT_LIST_SUCCESS(HttpStatus.OK, SameAdCode.GET_SAME_ADVERTISEMENT_LIST_SUCCESS, "동일 광고 리스트가 조회되었습니다.");

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
