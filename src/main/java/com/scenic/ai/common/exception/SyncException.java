package com.scenic.ai.common.exception;

import lombok.Getter;

/**
 * 同步操作异常
 * 用于处理数据同步过程中出现的异常情况
 */
@Getter
public class SyncException extends RuntimeException {
    
    /**
     * 创建同步异常
     *
     * @param message 异常信息
     */
    public SyncException(String message) {
        super(message);
    }

    /**
     * 创建同步异常
     *
     * @param message 异常信息
     * @param cause 原始异常
     */
    public SyncException(String message, Throwable cause) {
        super(message, cause);
    }
} 