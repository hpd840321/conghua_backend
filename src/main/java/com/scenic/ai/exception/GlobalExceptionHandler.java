package com.scenic.ai.exception;

import net.sf.jsqlparser.util.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * 
 * @author AI
 * @date 2023-05-20
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理系统异常
     */
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Map<String, Object>> handleSystemException(SystemException e) {
        log.error("系统异常", e);
        Map<String, Object> response = new HashMap<>();
        response.put("code", e.getCode());
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException e) {
        log.warn("业务异常", e);
        Map<String, Object> response = new HashMap<>();
        response.put("code", e.getCode());
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException e) {
        log.warn("参数验证异常", e);
        Map<String, Object> response = new HashMap<>();
        response.put("code", ErrorCode.PARAM_ERROR);
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理资源不存在异常
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.warn("资源不存在", e);
        Map<String, Object> response = new HashMap<>();
        response.put("code", ErrorCode.DATA_NOT_FOUND);
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * 处理其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        log.error("未知异常", e);
        Map<String, Object> response = new HashMap<>();
        response.put("code", ErrorCode.SYSTEM_ERROR);
        response.put("message", "系统异常，请联系管理员");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 