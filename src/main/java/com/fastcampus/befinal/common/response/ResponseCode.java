package com.fastcampus.befinal.common.response;

import org.springframework.http.HttpStatus;

public interface ResponseCode {
    HttpStatus getHttpStatus();

    Integer getCode();

    String getMessage();
}
