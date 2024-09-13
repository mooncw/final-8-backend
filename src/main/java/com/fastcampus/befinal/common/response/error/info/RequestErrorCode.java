package com.fastcampus.befinal.common.response.error.info;

import com.fastcampus.befinal.common.response.code.Code;
import com.fastcampus.befinal.common.response.code.RequestCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum RequestErrorCode implements ErrorCode {
    REQUEST_ERROR(HttpStatus.BAD_REQUEST, RequestCode.REQUEST_ERROR);

    private final HttpStatus httpStatus;
    private final Code code;
    private String message;

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

    public void setMessage(MethodArgumentNotValidException e) {
        this.message = e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining("\n"));
    }
}
