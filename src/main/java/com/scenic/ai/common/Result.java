package com.scenic.ai.common;

import java.io.Serializable;

/**
 * 统一响应结果类
 * 
 * @author AI
 * @date 2023-05-20
 */
public class Result<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 状态码
     */
    private int code;
    
    /**
     * 返回信息
     */
    private String message;
    
    /**
     * 返回数据
     */
    private T data;
    
    /**
     * 无参构造函数
     */
    public Result() {
    }
    
    /**
     * 获取状态码
     * @return 状态码
     */
    public int getCode() {
        return code;
    }
    
    /**
     * 设置状态码
     * @param code 状态码
     */
    public void setCode(int code) {
        this.code = code;
    }
    
    /**
     * 获取返回信息
     * @return 返回信息
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * 设置返回信息
     * @param message 返回信息
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * 获取返回数据
     * @return 返回数据
     */
    public T getData() {
        return data;
    }
    
    /**
     * 设置返回数据
     * @param data 返回数据
     */
    public void setData(T data) {
        this.data = data;
    }
    
    /**
     * 成功返回结果
     * 
     * @param <T> 泛型参数
     * @return 成功结果
     */
    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        return result;
    }
    
    /**
     * 成功返回结果
     * 
     * @param data 获取的数据
     * @param <T> 泛型参数
     * @return 成功结果
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }
    
    /**
     * 失败返回结果
     * 
     * @param message 提示信息
     * @param <T> 泛型参数
     * @return 失败结果
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 失败返回结果
     * 
     * @param code 错误码
     * @param message 提示信息
     * @param <T> 泛型参数
     * @return 失败结果
     */
    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 失败返回结果
     * 
     * @param code 错误码
     * @param message 提示信息
     * @param data 获取的数据
     * @param <T> 泛型参数
     * @return 失败结果
     */
    public static <T> Result<T> error(int code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
} 