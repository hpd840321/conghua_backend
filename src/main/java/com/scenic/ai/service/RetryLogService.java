package com.scenic.ai.service;

import com.scenic.ai.mapper.RetryLogMapper;
import com.scenic.ai.model.RetryLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.scenic.ai.common.enums.ErrorCode;
import com.scenic.ai.common.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 重试日志服务类 - 单例模式
 * 用于管理业务重试任务的状态和进度
 */
public class RetryLogService {

    private static final Logger log = LoggerFactory.getLogger(RetryLogService.class);

    /**
     * 重试状态常量
     */
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILED = "FAILED";

    /**
     * 业务类型常量
     */
    public static final String BUSINESS_TYPE_CROWD = "CROWD";
    public static final String BUSINESS_TYPE_FLOW = "FLOW";
    public static final String BUSINESS_TYPE_ALERT = "ALERT";

    /**
     * 默认重试间隔（分钟）
     */
    private static final int DEFAULT_RETRY_INTERVAL = 1;

    /**
     * 最大重试次数
     */
    private static final int MAX_RETRY_COUNT = 3;

    private static volatile RetryLogService instance;
    private final RetryLogMapper retryLogMapper;

    private RetryLogService(RetryLogMapper retryLogMapper) {
        if (retryLogMapper == null) {
            throw new IllegalArgumentException("RetryLogMapper cannot be null");
        }
        this.retryLogMapper = retryLogMapper;
    }

    public static RetryLogService getInstance(RetryLogMapper retryLogMapper) {
        if (instance == null) {
            synchronized (RetryLogService.class) {
                if (instance == null) {
                    instance = new RetryLogService(retryLogMapper);
                }
            }
        }
        return instance;
    }

    /**
     * 创建重试日志
     * 
     * @param businessType  业务类型
     * @param businessId    业务ID
     * @param maxRetryCount 最大重试次数
     * @param errorMessage  错误信息
     * @return 重试日志对象
     */
    public RetryLog createRetryLog(String businessType, String businessId, int maxRetryCount, String errorMessage) {
        if (businessType == null || businessId == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "业务类型和业务ID不能为空");
        }
        if (maxRetryCount <= 0) {
            maxRetryCount = MAX_RETRY_COUNT;
        }

        RetryLog retryLog = new RetryLog();
        retryLog.setBusinessType(businessType);
        retryLog.setBusinessId(businessId);
        retryLog.setMaxRetryCount(maxRetryCount);
        retryLog.setRetryCount(0);
        retryLog.setStatus(STATUS_PENDING);
        retryLog.setErrorMessage(errorMessage);
        retryLog.setNextRetryTime(LocalDateTime.now().plusMinutes(DEFAULT_RETRY_INTERVAL));

        try {
            retryLogMapper.insert(retryLog);
            return retryLog;
        } catch (Exception e) {
            log.error("创建重试日志失败: businessType={}, businessId={}", businessType, businessId, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建重试日志失败");
        }
    }

    /**
     * 更新重试信息
     * 
     * @param id            重试日志ID
     * @param retryCount    重试次数
     * @param nextRetryTime 下次重试时间
     * @param errorMessage  错误信息
     * @return 是否更新成功
     */
    public boolean updateRetryInfo(Long id, int retryCount, LocalDateTime nextRetryTime, String errorMessage) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "ID不能为空或小于等于0");
        }
        if (nextRetryTime == null) {
            nextRetryTime = LocalDateTime.now().plusMinutes(DEFAULT_RETRY_INTERVAL);
        }

        try {
            int rows = retryLogMapper.updateRetryInfo(id, retryCount, nextRetryTime);
            if (rows > 0 && errorMessage != null) {
                retryLogMapper.updateStatus(id, STATUS_PENDING, errorMessage);
            }
            return rows > 0;
        } catch (Exception e) {
            log.error("更新重试信息失败: id={}, retryCount={}", id, retryCount, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新重试信息失败");
        }
    }

    /**
     * 更新重试状态
     * 
     * @param id           重试日志ID
     * @param status       状态
     * @param errorMessage 错误信息
     * @return 是否更新成功
     */
    public boolean updateStatus(Long id, String status, String errorMessage) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "ID不能为空或小于等于0");
        }
        if (status == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "状态不能为空");
        }

        try {
            int rows = retryLogMapper.updateStatus(id, status, errorMessage);
            return rows > 0;
        } catch (Exception e) {
            log.error("更新状态失败: id={}, status={}", id, status, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新状态失败");
        }
    }

    /**
     * 查找需要重试的任务
     * 
     * @param nextRetryTime 下次重试时间
     * @param status        状态
     * @return 重试日志列表
     */
    public List<RetryLog> findRetryTasks(LocalDateTime nextRetryTime, String status) {
        if (nextRetryTime == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "下次重试时间不能为空");
        }
        return retryLogMapper.findRetryTasks(nextRetryTime, status);
    }

    /**
     * 根据业务类型和ID查找重试日志
     * 
     * @param businessType 业务类型
     * @param businessId   业务ID
     * @return 重试日志对象
     */
    public RetryLog findByBusinessTypeAndId(String businessType, String businessId) {
        if (businessType == null || businessId == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "业务类型和业务ID不能为空");
        }
        return retryLogMapper.findByBusinessTypeAndId(businessType, businessId);
    }

    /**
     * 根据状态查找重试日志
     * 
     * @param status 状态
     * @return 重试日志列表
     */
    public List<RetryLog> findByStatus(String status) {
        if (status == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "状态不能为空");
        }
        return retryLogMapper.findByStatus(status);
    }

    /**
     * 按业务类型统计重试日志
     * 
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 统计结果列表
     */
    public List<Map<String, Object>> countByBusinessType(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
        }
        return retryLogMapper.countByBusinessType(startTime, endTime);
    }

    /**
     * 按状态统计重试日志
     * 
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 统计结果列表
     */
    public List<Map<String, Object>> countByStatus(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
        }
        return retryLogMapper.countByStatus(startTime, endTime);
    }

    /**
     * 根据条件查询重试日志
     * 
     * @param params 查询参数
     * @return 重试日志列表
     */
    public List<RetryLog> findByConditions(Map<String, Object> params) {
        if (params == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "查询参数不能为空");
        }
        return retryLogMapper.findByConditions(params);
    }

    /**
     * 统计符合条件的记录数
     * 
     * @param params 查询参数
     * @return 记录数
     */
    public Long countRecords(Map<String, Object> params) {
        if (params == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "查询参数不能为空");
        }
        return retryLogMapper.countRecords(params);
    }

    /**
     * 清理成功的重试日志
     * 
     * @param beforeTime 清理该时间之前的日志
     * @return 清理的记录数
     */
    public int cleanSuccessLogs(LocalDateTime beforeTime) {
        if (beforeTime == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "清理时间不能为空");
        }
        try {
            return retryLogMapper.cleanSuccessLogs(beforeTime);
        } catch (Exception e) {
            log.error("清理成功的重试日志失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "清理成功的重试日志失败");
        }
    }
}