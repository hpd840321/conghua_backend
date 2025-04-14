package com.scenic.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.Alert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警信息数据访问接口
 */
@Mapper
public interface AlertMapper extends BaseMapper<Alert> {

    /**
     * 查询时段分布
     */
    List<Map<String, Object>> selectTimeDistribution(
            @Param("tourismName") String tourismName,
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 查询类型分布
     */
    List<Map<String, Object>> selectTypeDistribution(
            @Param("tourismName") String tourismName,
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 查询告警概览
     */
    Map<String, Object> selectOverview(
            @Param("tourismName") String tourismName,
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 根据设备查询告警
     */
    List<Alert> selectByDevice(
            @Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 根据景区查询告警
     */
    List<Alert> selectByTourism(
            @Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 统计未处理告警数量
     */
    Long countUnhandledAlerts(
            @Param("deviceCode") String deviceCode,
            @Param("tourismName") String tourismName);

    /**
     * 批量更新告警状态
     */
    int batchUpdateStatus(
            @Param("ids") List<Long> ids,
            @Param("status") Integer status);

    /**
     * 获取设备最新告警
     */
    Alert selectLatestByDevice(@Param("deviceCode") String deviceCode);
} 