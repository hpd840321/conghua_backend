package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.scenic.ai.entity.Alert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警Mapper接口
 * 
 * @author AI
 * @date 2024-04-15
 */
@Mapper
public interface AlertMapper extends BaseMapper<Alert> {

        /**
         * 根据设备编码查询告警列表
         */
        @Select("SELECT * FROM ALERT WHERE DEVICE_CODE = #{deviceCode} ORDER BY RECORD_TIME DESC")
        List<Alert> listByDevice(@Param("deviceCode") String deviceCode);

        /**
         * 根据景区名称查询告警列表
         */
        @Select("SELECT * FROM ALERT WHERE TOURISM_NAME = #{tourismName} ORDER BY RECORD_TIME DESC")
        List<Alert> listByTourism(@Param("tourismName") String tourismName);

        /**
         * 根据时间范围查询告警列表
         */
        @Select("SELECT * FROM ALERT WHERE RECORD_TIME BETWEEN #{startTime} AND #{endTime} ORDER BY RECORD_TIME DESC")
        List<Alert> listByTimeRange(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据条件查询告警列表
         */
        List<Alert> listByConditions(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("alertType") String alertType,
                        @Param("alertLevel") Integer alertLevel,
                        @Param("alertStatus") Integer alertStatus,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据条件分页查询告警
         */
        IPage<Alert> pageByConditions(
                        IPage<Alert> page,
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("alertType") String alertType,
                        @Param("alertLevel") Integer alertLevel,
                        @Param("alertStatus") Integer alertStatus,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据条件统计告警数量
         */
        int countByConditions(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("alertType") String alertType,
                        @Param("alertLevel") Integer alertLevel,
                        @Param("alertStatus") Integer alertStatus,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据时间范围统计告警数量
         */
        int countByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

        /**
         * 批量更新告警状态
         */
        int handleBatchStatus(@Param("ids") List<Long> ids, @Param("alertStatus") Integer alertStatus);

        /**
         * 获取告警类型分布统计
         */
        List<Map<String, Object>> countTypeDistribution(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取告警级别分布统计
         */
        List<Map<String, Object>> countLevelDistribution(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取告警状态分布统计
         */
        List<Map<String, Object>> countStatusDistribution(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取告警时段分布统计
         */
        List<Map<String, Object>> countTimeDistribution(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取设备最新告警
         */
        Alert getLatestByDevice(@Param("deviceCode") String deviceCode);

        /**
         * 根据设备编码查询告警列表
         */
        List<Alert> listByDevice(@Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据景区名称查询告警列表
         */
        List<Alert> listByTourism(@Param("tourismName") String tourismName,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取告警趋势统计
         */
        List<Map<String, Object>> countTrend(@Param("deviceCode") String deviceCode,
                        @Param("tourismName") String tourismName,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据ID查询告警
         */
        Alert getById(@Param("id") Long id);

        /**
         * 根据告警级别和状态统计数量
         */
        int countByLevelAndStatus(@Param("alertLevel") Integer alertLevel,
                        @Param("alertStatus") Integer alertStatus);

        /**
         * 根据告警类型和时间范围统计数量
         */
        List<Map<String, Object>> countByTypeAndTimeRange(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据告警状态和时间范围统计数量
         */
        List<Map<String, Object>> countByStatusAndTimeRange(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据设备编码和时间范围统计数量
         */
        List<Map<String, Object>> countByDeviceAndTimeRange(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询所有告警类型列表
         */
        List<String> listAlertTypes();

        /**
         * 获取设备分布统计
         */
        List<Map<String, Object>> countDeviceDistribution(@Param("tourismName") String tourismName,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取景区分布统计
         */
        List<Map<String, Object>> countTourismDistribution(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 批量更新告警
         */
        int handleBatchUpdate(@Param("list") List<Alert> list);

        /**
         * 根据告警级别和时间范围统计数量
         */
        List<Map<String, Object>> countByLevelAndTimeRange(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据景区名称和时间范围统计数量
         */
        List<Map<String, Object>> countByTourismAndTimeRange(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);
}