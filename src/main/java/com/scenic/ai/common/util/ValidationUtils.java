package com.scenic.ai.common.util;

import com.scenic.ai.common.enums.ErrorCode;
import com.scenic.ai.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * 参数验证工具类
 */
public class ValidationUtils {
    private static final String DEVICE_CODE_PATTERN = "^[A-Za-z0-9]{6,32}$";
    private static final String TOURISM_NAME_PATTERN = "^[\\u4e00-\\u9fa5a-zA-Z0-9\\-_]{2,50}$";

    /**
     * 验证设备编码
     */
    public static void validateDeviceCode(String deviceCode) {
        if (StringUtils.isBlank(deviceCode)) {
            throw new BusinessException(ErrorCode.DEVICE_CODE_EMPTY, "设备编码不能为空");
        }
        if (!deviceCode.matches(DEVICE_CODE_PATTERN)) {
            throw new BusinessException(ErrorCode.DEVICE_CODE_INVALID, "设备编码格式不正确");
        }
    }

    /**
     * 验证景区名称
     */
    public static void validateTourismName(String tourismName) {
        if (StringUtils.isBlank(tourismName)) {
            throw new BusinessException(ErrorCode.TOURISM_NAME_EMPTY, "景区名称不能为空");
        }
        if (!tourismName.matches(TOURISM_NAME_PATTERN)) {
            throw new BusinessException(ErrorCode.TOURISM_NAME_INVALID, "景区名称格式不正确");
        }
    }

    /**
     * 验证时间范围
     */
    public static void validateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.TIME_RANGE_INVALID, "开始时间和结束时间不能为空");
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.TIME_RANGE_INVALID, "开始时间不能晚于结束时间");
        }
    }

    /**
     * 验证ID
     */
    public static void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "ID不能为空且必须大于0");
        }
    }

    /**
     * 验证分页参数
     */
    public static void validatePage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            throw new BusinessException(ErrorCode.PAGE_NUM_INVALID, "页码不能为空且必须大于0");
        }
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            throw new BusinessException(ErrorCode.PAGE_SIZE_INVALID, "每页条数不能为空且必须在1-100之间");
        }
    }
}