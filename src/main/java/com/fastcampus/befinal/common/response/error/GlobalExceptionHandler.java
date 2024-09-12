package com.fastcampus.befinal.common.response.error;

import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.response.error.exception.BaseException;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.response.error.info.RequestErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import static com.fastcampus.befinal.common.response.error.info.BaseErrorCode.SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<AppApiResponse> onBusinessException(BusinessException e) {
        return ResponseEntityFactory.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppApiResponse> onBaseException(Exception e) {
        log.error(e.getMessage());
        BaseException baseException = new BaseException(SERVER_ERROR);
        return ResponseEntityFactory.toResponseEntity(baseException.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppApiResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        RequestErrorCode errorCode = RequestErrorCode.REQUEST_ERROR;
        errorCode.setMessage(e);
        return ResponseEntityFactory.toResponseEntity(errorCode);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<AppApiResponse> onHandlerMethodValidationException(HandlerMethodValidationException e) {
        RequestErrorCode errorCode = RequestErrorCode.REQUEST_ERROR;
        errorCode.setMessage("입력을 다시 확인해주세요");
        return ResponseEntityFactory.toResponseEntity(errorCode);
    }
}
