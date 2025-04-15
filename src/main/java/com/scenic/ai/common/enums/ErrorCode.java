package com.scenic.ai.common.enums;

/**
 * 错误码枚举
 * 
 * @author AI
 * @date 2024-04-15
 */
public enum ErrorCode {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR(500, "系统错误"),

    /**
     * 参数错误
     */
    PARAM_INVALID(400, "参数错误"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 分页参数错误
     */
    PAGE_PARAM_ERROR(400, "分页参数错误"),

    /**
     * 设备不存在
     */
    DEVICE_NOT_FOUND(404, "设备不存在"),

    /**
     * 设备已存在
     */
    DEVICE_ALREADY_EXISTS(400, "设备已存在"),

    /**
     * 设备编码不能为空
     */
    DEVICE_CODE_EMPTY(400, "设备编码不能为空"),

    /**
     * 设备名称不能为空
     */
    DEVICE_NAME_EMPTY(400, "设备名称不能为空"),

    /**
     * 景区不存在
     */
    TOURISM_NOT_FOUND(404, "景区不存在"),

    /**
     * 景区已存在
     */
    TOURISM_ALREADY_EXISTS(400, "景区已存在"),

    /**
     * 景区名称不能为空
     */
    TOURISM_NAME_EMPTY(400, "景区名称不能为空"),

    /**
     * 时间参数错误
     */
    TIME_PARAM_ERROR(400, "时间参数错误"),

    /**
     * 开始时间不能晚于结束时间
     */
    START_TIME_AFTER_END_TIME(400, "开始时间不能晚于结束时间"),

    /**
     * 告警不存在
     */
    ALERT_NOT_FOUND(404, "告警不存在"),

    /**
     * 告警ID不能为空
     */
    ALERT_ID_EMPTY(400, "告警ID不能为空"),

    /**
     * 告警类型不能为空
     */
    ALERT_TYPE_EMPTY(400, "告警类型不能为空"),

    /**
     * 告警级别不能为空
     */
    ALERT_LEVEL_EMPTY(400, "告警级别不能为空"),

    /**
     * 告警状态不能为空
     */
    ALERT_STATUS_EMPTY(400, "告警状态不能为空"),

    /**
     * 告警处理记录不存在
     */
    ALERT_HANDLE_RECORD_NOT_FOUND(404, "告警处理记录不存在"),

    /**
     * 告警处理记录ID不能为空
     */
    ALERT_HANDLE_RECORD_ID_EMPTY(400, "告警处理记录ID不能为空"),

    /**
     * 处理人不能为空
     */
    HANDLER_EMPTY(400, "处理人不能为空"),

    /**
     * 处理方式不能为空
     */
    HANDLE_METHOD_EMPTY(400, "处理方式不能为空"),

    /**
     * 处理结果不能为空
     */
    HANDLE_RESULT_EMPTY(400, "处理结果不能为空"),

    /**
     * 处理时间不能为空
     */
    HANDLE_TIME_EMPTY(400, "处理时间不能为空"),

    /**
     * 处理描述不能为空
     */
    HANDLE_DESC_EMPTY(400, "处理描述不能为空"),

    /**
     * 客流统计数据不存在
     */
    CROWD_STATISTICS_NOT_FOUND(404, "客流统计数据不存在"),

    /**
     * 客流统计数据ID不能为空
     */
    CROWD_STATISTICS_ID_EMPTY(400, "客流统计数据ID不能为空"),

    /**
     * 客流数量不能为空
     */
    CROWD_COUNT_EMPTY(400, "客流数量不能为空"),

    /**
     * 客流密度不能为空
     */
    CROWD_DENSITY_EMPTY(400, "客流密度不能为空"),

    /**
     * 算法类型不能为空
     */
    ALG_NAME_EMPTY(400, "算法类型不能为空"),

    /**
     * 任务编码不能为空
     */
    TASK_CODE_EMPTY(400, "任务编码不能为空"),

    /**
     * 记录时间不能为空
     */
    RECORD_TIME_EMPTY(400, "记录时间不能为空"),

    /**
     * 重试日志不存在
     */
    RETRY_LOG_NOT_FOUND(404, "重试日志不存在"),

    /**
     * 重试日志ID不能为空
     */
    RETRY_LOG_ID_EMPTY(400, "重试日志ID不能为空"),

    /**
     * 业务类型不能为空
     */
    BUSINESS_TYPE_EMPTY(400, "业务类型不能为空"),

    /**
     * 业务ID不能为空
     */
    BUSINESS_ID_EMPTY(400, "业务ID不能为空"),

    /**
     * 重试次数不能为空
     */
    RETRY_COUNT_EMPTY(400, "重试次数不能为空"),

    /**
     * 最大重试次数不能为空
     */
    MAX_RETRY_COUNT_EMPTY(400, "最大重试次数不能为空"),

    /**
     * 状态不能为空
     */
    STATUS_EMPTY(400, "状态不能为空"),

    /**
     * 错误信息不能为空
     */
    ERROR_MESSAGE_EMPTY(400, "错误信息不能为空"),

    /**
     * 下次重试时间不能为空
     */
    NEXT_RETRY_TIME_EMPTY(400, "下次重试时间不能为空");

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}