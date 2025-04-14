package com.scenic.ai.common.exception;

import lombok.Getter;

/**
 * 参数验证异常
 */
@Getter
public class ValidationException extends RuntimeException {
    private final int code;

    public ValidationException(String message) {
        super(message);
        this.code = 400;
    }

    public ValidationException(int code, String message) {
        super(message);
        this.code = code;
    }
} 