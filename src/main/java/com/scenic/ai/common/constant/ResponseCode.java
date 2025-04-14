package com.scenic.ai.common.constant;

/**
 * 响应状态码常量类
 * 
 * 状态码说明：
 * - 2xx：成功
 * - 4xx：客户端错误，如参数错误、未授权等
 * - 5xx：服务器错误
 * - 6xx：业务错误
 */
public class ResponseCode {
    
    // ========== 成功状态码 ==========
    /**
     * 成功
     */
    public static final int SUCCESS = 200;
    public static final String SUCCESS_MSG = "操作成功";

    // ========== 客户端错误状态码 ==========
    /**
     * 参数错误
     */
    public static final int PARAM_ERROR = 400;
    public static final String PARAM_ERROR_MSG = "请求参数错误";

    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;
    public static final String UNAUTHORIZED_MSG = "未授权，请先登录";

    /**
     * 禁止访问
     */
    public static final int FORBIDDEN = 403;
    public static final String FORBIDDEN_MSG = "无权限访问该资源";

    /**
     * 资源不存在
     */
    public static final int NOT_FOUND = 404;
    public static final String NOT_FOUND_MSG = "请求的资源不存在";

    // ========== 服务器错误状态码 ==========
    /**
     * 系统内部错误
     */
    public static final int INTERNAL_ERROR = 500;
    public static final String INTERNAL_ERROR_MSG = "系统内部错误";

    // ========== 业务错误状态码 ==========
    /**
     * 通用业务错误
     */
    public static final int BUSINESS_ERROR = 600;
    public static final String BUSINESS_ERROR_MSG = "业务处理失败";

    /**
     * 设备相关错误
     */
    public static final int DEVICE_ERROR = 610;
    public static final String DEVICE_ERROR_MSG = "设备操作失败";
    
    /**
     * 全景图相关错误
     */
    public static final int PANORAMA_ERROR = 620;
    public static final String PANORAMA_ERROR_MSG = "全景图操作失败";

    /**
     * 区域相关错误
     */
    public static final int REGION_ERROR = 630;
    public static final String REGION_ERROR_MSG = "区域操作失败";

    /**
     * 文件操作相关错误
     */
    public static final int FILE_ERROR = 640;
    public static final String FILE_ERROR_MSG = "文件操作失败";

    private ResponseCode() {
        // 私有构造函数，防止实例化
    }
} 