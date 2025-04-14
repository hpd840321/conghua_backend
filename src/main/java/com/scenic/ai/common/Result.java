package com.scenic.ai.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 通用响应结果类
 *
 * @author scenic
 * @date 2024-03-19
 */
@Data
public class Result<T> {
    
    /**
     * 响应码
     */
    private Integer code;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 响应数据
     */
    private T data;

    /**
     * 私有构造函数
     */
    private Result() {
    }

    /**
     * 带参数的构造函数
     */
    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功响应
     *
     * @param data 响应数据
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }
    
    /**
     * 失败响应
     *
     * @param code 错误码
     * @param message 错误消息
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> error(Integer code, String message) {
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