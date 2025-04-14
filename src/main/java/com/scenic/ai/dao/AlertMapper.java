package com.scenic.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.Alert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警信息数据访问层
 * 
 * @author scenic
 */
@Mapper
public interface AlertMapper extends BaseMapper<Alert> {
    
    /**
     * 获取告警时段分布
     */
    List<Map<String, Object>> selectHourDistribution(@Param("tourismName") String tourismName,
                                                   @Param("deviceCode") String deviceCode,
                                                   @Param("startTime") LocalDateTime startTime,
                                                   @Param("endTime") LocalDateTime endTime);
    
    /**
     * 获取告警类型分布
     */
    List<Map<String, Object>> selectTypeDistribution(@Param("tourismName") String tourismName,
                                                   @Param("deviceCode") String deviceCode,
                                                   @Param("startTime") LocalDateTime startTime,
                                                   @Param("endTime") LocalDateTime endTime);
    
    /**
     * 获取告警概览统计
     */
    Map<String, Object> selectOverview(@Param("tourismName") String tourismName,
                                     @Param("deviceCode") String deviceCode,
                                     @Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);

    /**
     * 根据设备编码查询告警信息
     */
    List<Alert> selectByDeviceCode(@Param("deviceCode") String deviceCode,
                                 @Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime);

    /**
     * 根据景区名称查询告警信息
     */
    List<Alert> selectByTourismName(@Param("tourismName") String tourismName,
                                  @Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);

    /**
     * 获取未处理的告警信息
     */
    List<Alert> selectUnhandled(@Param("deviceCode") String deviceCode,
                              @Param("tourismName") String tourismName);
} 