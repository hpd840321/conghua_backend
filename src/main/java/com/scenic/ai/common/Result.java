package com.scenic.ai.common;

import lombok.Data;

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
    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功响应
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功");
    }
    
    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }
    
    /**
     * 失败响应
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message);
    }
    
    /**
     * 失败响应（带错误码）
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message);
    }
    
    /**
     * 参数错误响应
     */
    public static <T> Result<T> validateError(String message) {
        return new Result<>(400, message);
    }
    
    /**
     * 未授权响应
     */
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(401, message);
    }
    
    /**
     * 禁止访问响应
     */
    public static <T> Result<T> forbidden(String message) {
        return new Result<>(403, message);
    }
    
    /**
     * 资源不存在响应
     */
    public static <T> Result<T> notFound(String message) {
        return new Result<>(404, message);
    }
} 