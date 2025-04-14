package com.scenic.ai.common.model;

import com.scenic.ai.common.constant.ResponseCode;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 统一API响应结果封装类
 * 用于统一处理所有API接口的响应数据格式
 *
 * @author scenic-AI
 * @version 1.0
 * @since 2024-01
 * @param <T> 响应数据的类型
 */
@Data
public class Result<T> {
    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应时间戳
     */
    private LocalDateTime timestamp;

    /**
     * 私有构造函数，初始化时间戳
     */
    private Result() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * 成功响应，无数据
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(ResponseCode.SUCCESS);
        result.setMessage(ResponseCode.SUCCESS_MSG);
        return result;
    }

    /**
     * 成功响应，有数据
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResponseCode.SUCCESS);
        result.setMessage(ResponseCode.SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    /**
     * 错误响应，指定错误码和消息
     */
    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 系统内部错误响应
     */
    public static <T> Result<T> error(String message) {
        return error(ResponseCode.INTERNAL_ERROR, ResponseCode.INTERNAL_ERROR_MSG);
    }

    /**
     * 参数错误响应
     */
    public static <T> Result<T> paramError(String message) {
        return error(ResponseCode.PARAM_ERROR, ResponseCode.PARAM_ERROR_MSG);
    }

    /**
     * 业务错误响应
     */
    public static <T> Result<T> businessError(String message) {
        return error(ResponseCode.BUSINESS_ERROR, ResponseCode.BUSINESS_ERROR_MSG);
    }
} 