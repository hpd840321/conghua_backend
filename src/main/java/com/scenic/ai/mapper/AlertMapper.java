package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.Alert;
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
         * 
         * @param deviceCode 设备编码
         * @return 告警列表
         */
        @Select("SELECT * FROM ALERT WHERE DEVICE_CODE = #{deviceCode} ORDER BY RECORD_TIME DESC")
        List<Alert> selectByDeviceCode(@Param("deviceCode") String deviceCode);

        /**
         * 根据景区名称查询告警列表
         * 
         * @param tourismName 景区名称
         * @return 告警列表
         */
        @Select("SELECT * FROM ALERT WHERE TOURISM_NAME = #{tourismName} ORDER BY RECORD_TIME DESC")
        List<Alert> selectByTourismName(@Param("tourismName") String tourismName);

        /**
         * 根据时间范围查询告警列表
         * 
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 告警列表
         */
        @Select("SELECT * FROM ALERT WHERE RECORD_TIME BETWEEN #{startTime} AND #{endTime} ORDER BY RECORD_TIME DESC")
        List<Alert> selectByTimeRange(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据条件查询告警列表
         * 
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param alertType   告警类型
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警列表
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
         * 
         * @param page        分页对象
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param alertType   告警类型
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 分页结果
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
         * 
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param alertType   告警类型
         * @param alertLevel  告警级别
         * @param alertStatus 告警状态
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警数量
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
         * 
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 告警数量
         */
        int countByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

        /**
         * 批量更新告警状态
         * 
         * @param ids         告警ID列表
         * @param alertStatus 告警状态
         * @return 更新数量
         */
        int updateStatusBatch(@Param("ids") List<Long> ids, @Param("alertStatus") Integer alertStatus);

        /**
         * 获取告警类型分布
         * 
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警类型分布
         */
        List<Map<String, Object>> getTypeDistribution(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取告警级别分布
         * 
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警级别分布
         */
        List<Map<String, Object>> getLevelDistribution(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取告警状态分布
         * 
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警状态分布
         */
        List<Map<String, Object>> getStatusDistribution(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取告警时段分布
         * 
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 告警时段分布
         */
        List<Map<String, Object>> getTimeDistribution(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取设备最新告警
         * 
         * @param deviceCode 设备编码
         * @return 最新告警
         */
        Alert getLatestByDevice(@Param("deviceCode") String deviceCode);
}