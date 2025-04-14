package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.RetryLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 重试日志Mapper接口
 */
@Mapper
public interface RetryLogMapper extends BaseMapper<RetryLog> {
    
    /**
     * 插入重试日志
     *
     * @param retryLog 重试日志
     * @return 影响行数
     */
    int insert(RetryLog retryLog);
    
    /**
     * 更新重试日志
     *
     * @param retryLog 重试日志
     * @return 影响行数
     */
    int update(RetryLog retryLog);
    
    /**
     * 根据业务类型和业务ID查询重试日志
     *
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 重试日志
     */
    RetryLog findByBusinessTypeAndId(
            @Param("businessType") String businessType,
            @Param("businessId") String businessId);
    
    /**
     * 根据状态查询重试日志
     *
     * @param status 状态
     * @return 重试日志列表
     */
    List<RetryLog> findByStatus(@Param("status") String status);
    
    /**
     * 查询需要重试的任务
     *
     * @param nextRetryTime 下次重试时间
     * @param status 状态
     * @return 重试日志列表
     */
    List<RetryLog> findRetryTasks(
            @Param("nextRetryTime") LocalDateTime nextRetryTime,
            @Param("status") String status);
    
    /**
     * 更新重试次数和下次重试时间
     *
     * @param id ID
     * @param retryCount 重试次数
     * @param nextRetryTime 下次重试时间
     * @return 影响行数
     */
    @Update("UPDATE CLOUDWALK.RETRY_LOG " +
            "SET RETRY_COUNT = #{retryCount}, " +
            "NEXT_RETRY_TIME = #{nextRetryTime}, " +
            "UPDATE_TIME = SYSTIMESTAMP " +
            "WHERE ID = #{id}")
    int updateRetryInfo(@Param("id") Long id,
                       @Param("retryCount") Integer retryCount,
                       @Param("nextRetryTime") LocalDateTime nextRetryTime);
    
    /**
     * 更新状态
     *
     * @param id ID
     * @param status 状态
     * @param errorMessage 错误信息
     * @return 影响行数
     */
    @Update("UPDATE CLOUDWALK.RETRY_LOG " +
            "SET STATUS = #{status}, " +
            "ERROR_MESSAGE = #{errorMessage}, " +
            "UPDATE_TIME = SYSTIMESTAMP " +
            "WHERE ID = #{id}")
    int updateStatus(@Param("id") Long id,
                    @Param("status") String status,
                    @Param("errorMessage") String errorMessage);
    
    /**
     * 统计各业务类型重试次数
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    @Select("SELECT BUSINESS_TYPE as businessType, " +
            "COUNT(*) as count, " +
            "AVG(RETRY_COUNT) as avgRetryCount " +
            "FROM CLOUDWALK.RETRY_LOG " +
            "WHERE CREATE_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY BUSINESS_TYPE")
    List<Map<String, Object>> countByBusinessType(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计重试状态分布
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    @Select("SELECT STATUS as status, COUNT(*) as count " +
            "FROM CLOUDWALK.RETRY_LOG " +
            "WHERE CREATE_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY STATUS")
    List<Map<String, Object>> countByStatus(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    /**
     * 条件分页查询
     *
     * @param params 查询参数
     * @return 重试日志列表
     */
    List<RetryLog> findByConditions(@Param("params") Map<String, Object> params);
    
    /**
     * 查询重试日志总数
     *
     * @param params 查询参数
     * @return 记录总数
     */
    Long countRecords(@Param("params") Map<String, Object> params);
} 