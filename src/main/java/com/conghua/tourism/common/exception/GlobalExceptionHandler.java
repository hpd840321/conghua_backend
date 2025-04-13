package com.conghua.tourism.common.exception;

import com.conghua.tourism.common.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ApiResult<Object> handleServiceException(ServiceException e) {
        log.error("业务异常：", e);
        return ApiResult.error(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ApiResult<Object> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        log.error("参数校验异常：{}", message);
        return ApiResult.error(400, message);
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<Object> handleException(Exception e) {
        log.error("系统异常：", e);
        return ApiResult.error("系统异常，请联系管理员");
    }
} 