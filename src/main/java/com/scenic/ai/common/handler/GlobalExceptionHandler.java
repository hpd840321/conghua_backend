package com.scenic.ai.common.handler;

import com.scenic.ai.common.constant.ResponseCode;
import com.scenic.ai.common.exception.BusinessException;
import com.scenic.ai.common.exception.SyncException;
import com.scenic.ai.common.model.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 统一处理系统中的各类异常，包括业务异常、参数验证异常、系统异常等
 *
 * @author scenic-AI
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<Void>> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage());
        return ResponseEntity.ok(Result.error(e.getCode(), e.getMessage()));
    }

    /**
     * 处理同步操作异常
     */
    @ExceptionHandler(SyncException.class)
    public ResponseEntity<Result<Void>> handleSyncException(SyncException e) {
        log.error("同步操作异常：{}", e.getMessage());
        return ResponseEntity.ok(Result.error(ResponseCode.INTERNAL_ERROR, e.getMessage()));
    }

    /**
     * 处理参数非法异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数非法异常：{}", e.getMessage());
        return ResponseEntity.ok(Result.paramError(e.getMessage()));
    }

    /**
     * 处理表单数据绑定异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Result<Void>> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数绑定异常：{}", message);
        return ResponseEntity.ok(Result.paramError(message));
    }

    /**
     * 处理 JSON 请求体参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleValidationException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数校验异常：{}", message);
        return ResponseEntity.ok(Result.paramError(message));
    }

    /**
     * 处理单个参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.error("参数约束异常：{}", message);
        return ResponseEntity.ok(Result.paramError(message));
    }

    /**
     * 处理未预期的系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleException(Exception e) {
        log.error("系统异常：", e);
        return ResponseEntity.ok(Result.error(ResponseCode.INTERNAL_ERROR, "系统内部错误"));
    }
} 