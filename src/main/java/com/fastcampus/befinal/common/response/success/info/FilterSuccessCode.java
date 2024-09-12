package com.fastcampus.befinal.common.response.success.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.FilterCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum FilterSuccessCode implements SuccessCode{

    FILTER_MEDIA_LIST(HttpStatus.OK, FilterCode.FILTER_MEDIA_LIST, "필터 매체명 리스트 조회되었습니다."),
    FILTER_CATEGORY_LIST(HttpStatus.OK, FilterCode.FILTER_CATEGORY_LIST, "필터 업종명 리스트 조회되었습니다.");

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
