package com.minio.es.common.exception;

import com.minio.es.common.status.ResponseObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(value = GlobalServiceException.class)
    public ResponseObject errorHandler(GlobalServiceException e) {
        return ResponseObject.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseObject errorHandler(Exception e) {
        return ResponseObject.error(-1, e.getMessage());
    }
}
