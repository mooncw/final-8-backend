package com.fastcampus.befinal.common.response.error;

import com.fastcampus.befinal.common.response.ApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.response.error.exception.BaseException;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse> onBusinessException(BusinessException e) {
        log.error(e.getMessage());
        return ResponseEntityFactory.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> onBaseException(BaseException e) {
        log.error(e.getMessage());
        return ResponseEntityFactory.toResponseEntity(e.getErrorCode());
    }
}
