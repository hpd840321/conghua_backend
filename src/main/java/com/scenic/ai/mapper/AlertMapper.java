package com.scenic.ai.mapper;

import com.scenic.ai.domain.model.Alert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警数据访问接口
 */
@Mapper
public interface AlertMapper {
    /**
     * 插入告警记录
     */
    void insert(Alert alert);
    
    /**
     * 更新告警记录
     */
    void update(Alert alert);
    
    /**
     * 根据ID查询告警
     */
    Alert findById(@Param("id") String id);
    
    /**
     * 根据状态查询告警
     */
    List<Alert> findByStatus(@Param("status") String status);
    
    /**
     * 根据级别和状态查询告警
     */
    List<Alert> findByLevelAndStatus(@Param("level") String level, @Param("status") String status);
    
    /**
     * 根据设备和时间范围查询告警
     */
    List<Alert> findByDeviceAndTimeRange(@Param("deviceCode") String deviceCode,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计时间范围内的告警数量
     */
    Map<String, Integer> countByTimeRange(@Param("startTime") LocalDateTime startTime,
                                        @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计告警类型分布
     */
    Map<String, Integer> countByType(@Param("startTime") LocalDateTime startTime,
                                   @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计告警时段分布
     */
    Map<String, Integer> countByHour(@Param("startTime") LocalDateTime startTime,
                                   @Param("endTime") LocalDateTime endTime);
    
    /**
     * 批量更新告警状态
     */
    int batchUpdateStatus(@Param("ids") List<String> ids,
                         @Param("status") String status,
                         @Param("handler") String handler,
                         @Param("remark") String remark);
    
    /**
     * 删除历史数据
     */
    int deleteBeforeTime(@Param("time") LocalDateTime time);
    
    /**
     * 计算平均值
     */
    Double calculateAverageValue(@Param("deviceCode") String deviceCode,
                               @Param("startTime") LocalDateTime startTime,
                               @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询最大值
     */
    Integer findMaxValue(@Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

    List<Alert> findByConditions(@Param("type") String type,
                                @Param("level") String level,
                                @Param("status") String status,
                                @Param("deviceCode") String deviceCode,
                                @Param("startTime") LocalDateTime startTime,
                                @Param("endTime") LocalDateTime endTime,
                                @Param("offset") Integer offset,
                                @Param("limit") Integer limit);
} 