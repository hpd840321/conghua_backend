package com.scenic.ai.exception;

/**
 * 错误码枚举
 */
public class ErrorCode {
    /**
     * 查询错误
     */
    public static final int QUERY_ERROR = 1001;
    
    /**
     * 保存错误
     */
    public static final int SAVE_ERROR = 1002;
    
    /**
     * 更新错误
     */
    public static final int UPDATE_ERROR = 1003;
    
    /**
     * 删除错误
     */
    public static final int DELETE_ERROR = 1004;
    
    /**
     * 参数错误
     */
    public static final int PARAM_ERROR = 1005;
    
    /**
     * 数据库错误
     */
    public static final int DATABASE_ERROR = 1006;
    
    /**
     * 数据不存在错误
     */
    public static final int DATA_NOT_FOUND = 1007;
    
    /**
     * 系统错误
     */
    public static final int SYSTEM_ERROR = 9999;
    
    private ErrorCode() {
        // 私有构造函数，防止实例化
    }
} 