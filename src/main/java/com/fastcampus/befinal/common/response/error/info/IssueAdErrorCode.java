package com.fastcampus.befinal.common.response.error.info;


import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.IssueAdCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum IssueAdErrorCode implements ErrorCode {
    NOT_FOUND_ADVERTISEMENT_ID(HttpStatus.BAD_REQUEST, IssueAdCode.NOT_FOUND_ADVERTISEMENT_ID,
        "광고 아이디를 찾을 수 없습니다."),
    NOT_FOUND_ISSUE_REVIEW_ID(HttpStatus.BAD_REQUEST, IssueAdCode.NOT_FOUND_ISSUE_REVIEW_ID,
        "지적광고 검토 아이디를 찾을 수 없습니다"),
    NOT_FOUND_PROVISION_ID(HttpStatus.BAD_REQUEST, IssueAdCode.NOT_FOUND_PROVISION_ID,
        "조항 아이디를 찾을 수 없습니다."),
    NOT_FOUND_DECISION_ID(HttpStatus.BAD_REQUEST, IssueAdCode.NOT_FOUND_DECISION_ID,
        "심의결정 아이디를 찾을 수 없습니다."),
    INVALID_OPERATION_TYPE(HttpStatus.BAD_REQUEST, IssueAdCode.INVALID_OPERATION_TYPE,
        "잘못된 타입입니다 다시 확인해주세요.");


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
