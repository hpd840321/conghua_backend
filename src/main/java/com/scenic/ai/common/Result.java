package com.scenic.ai.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 统一响应对象
 */
@Data
public class Result<T> {
    
    /**
     * 状态码
     */
    private int code;
    
    /**
     * 返回消息
     */
    private String message;
    
    /**
     * 返回数据
     */
    private T data;
    
    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功返回结果
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(HttpStatus.OK.value(), "操作成功", data);
    }
    
    /**
     * 成功返回结果
     */
    public static <T> Result<T> ok(String message, T data) {
        return new Result<>(HttpStatus.OK.value(), message, data);
    }
    
    /**
     * 失败返回结果
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }
    
    /**
     * 失败返回结果
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
    
    /**
     * 参数验证失败返回结果
     */
    public static <T> Result<T> validateFailed(String message) {
        return new Result<>(HttpStatus.BAD_REQUEST.value(), message, null);
    }
    
    /**
     * 未登录返回结果
     */
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(HttpStatus.UNAUTHORIZED.value(), message, null);
    }
    
    /**
     * 未授权返回结果
     */
    public static <T> Result<T> forbidden(String message) {
        return new Result<>(HttpStatus.FORBIDDEN.value(), message, null);
    }
} 