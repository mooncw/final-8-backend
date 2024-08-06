package com.fastcampus.befinal.common.response.error.exception;

import com.fastcampus.befinal.common.response.error.info.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends Exception {
    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
