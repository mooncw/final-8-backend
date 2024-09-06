package com.fastcampus.befinal.common.response.error.info;


import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.IssueAdCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum IssueAdErrorCode implements ErrorCode {
    NOT_FOUND_ADVERTISEMENT_ID(HttpStatus.BAD_REQUEST, IssueAdCode.NOT_FOUND_ADVERTISEMENT_ID,
        "광고 아이디를 찾을 수 없습니다.");

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
