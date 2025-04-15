package com.scenic.ai.common.core.domain;

/**
 * 通用响应结果
 * 
 * @author AI
 * @date 2023-05-20
 */
public class R<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功状态码
     */
    public static final int SUCCESS = 200;

    /**
     * 失败状态码
     */
    public static final int ERROR = 500;

    /**
     * 未授权状态码
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 禁止访问状态码
     */
    public static final int FORBIDDEN = 403;

    /**
     * 构造方法
     */
    public R() {
    }

    /**
     * 构造方法
     * 
     * @param code 状态码
     * @param msg 返回消息
     * @param data 返回数据
     */
    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功结果
     * 
     * @param <T> 数据类型
     * @return 成功结果
     */
    public static <T> R<T> ok() {
        return new R<>(SUCCESS, "操作成功", null);
    }

    /**
     * 返回成功结果
     * 
     * @param data 返回数据
     * @param <T> 数据类型
     * @return 成功结果
     */
    public static <T> R<T> ok(T data) {
        return new R<>(SUCCESS, "操作成功", data);
    }

    /**
     * 返回成功结果
     * 
     * @param msg 返回消息
     * @param data 返回数据
     * @param <T> 数据类型
     * @return 成功结果
     */
    public static <T> R<T> ok(String msg, T data) {
        return new R<>(SUCCESS, msg, data);
    }

    /**
     * 返回失败结果
     * 
     * @param <T> 数据类型
     * @return 失败结果
     */
    public static <T> R<T> error() {
        return new R<>(ERROR, "操作失败", null);
    }

    /**
     * 返回失败结果
     * 
     * @param msg 返回消息
     * @param <T> 数据类型
     * @return 失败结果
     */
    public static <T> R<T> error(String msg) {
        return new R<>(ERROR, msg, null);
    }

    /**
     * 返回失败结果
     * 
     * @param code 状态码
     * @param msg 返回消息
     * @param <T> 数据类型
     * @return 失败结果
     */
    public static <T> R<T> error(Integer code, String msg) {
        return new R<>(code, msg, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
} 