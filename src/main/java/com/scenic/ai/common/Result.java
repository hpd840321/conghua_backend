package com.scenic.ai.common;

import lombok.Data;

/**
 * 统一返回结果
 */
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;
    private boolean success;

    private Result() {
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }
} 