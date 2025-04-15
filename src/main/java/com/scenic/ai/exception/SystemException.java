package com.scenic.ai.exception;

/**
 * 系统异常类
 * 
 * @author AI
 * @date 2023-05-20
 */
public class SystemException extends RuntimeException {
    
    /**
     * 错误码
     */
    private final int code;
    
    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误信息
     */
    public SystemException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误信息
     * @param cause 原始异常
     */
    public SystemException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    
    /**
     * 获取错误码
     * 
     * @return 错误码
     */
    public int getCode() {
        return code;
    }
} 