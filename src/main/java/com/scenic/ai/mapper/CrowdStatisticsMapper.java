package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.domain.model.CrowdStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群统计数据访问接口
 */
@Mapper
public interface CrowdStatisticsMapper extends BaseMapper<CrowdStatistics> {
    /**
     * 根据设备编码和时间范围查询统计数据
     */
    List<CrowdStatistics> findByDeviceAndTime(@Param("deviceCode") String deviceCode,
                                             @Param("startTime") LocalDateTime startTime,
                                             @Param("endTime") LocalDateTime endTime);

    /**
     * 统计时间范围内的平均密度
     */
    @Select("SELECT " +
            "ROUND(AVG(DENSITY), 2) as avgDensity, " +
            "MAX(DENSITY) as maxDensity, " +
            "MIN(DENSITY) as minDensity " +
            "FROM CLOUDWALK.CROWD_STATISTICS " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime}")
    Map<String, Object> calculateAverageDensity(@Param("deviceCode") String deviceCode,
                                               @Param("tourismName") String tourismName,
                                               @Param("startTime") LocalDateTime startTime,
                                               @Param("endTime") LocalDateTime endTime);

    /**
     * 统计时段分布
     */
    @Select("SELECT TO_CHAR(RECORD_TIME, 'HH24') as hour, " +
            "ROUND(AVG(COUNT), 2) as count, " +
            "ROUND(AVG(DENSITY), 2) as density " +
            "FROM CLOUDWALK.CROWD_STATISTICS " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY TO_CHAR(RECORD_TIME, 'HH24') " +
            "ORDER BY hour")
    List<Map<String, Object>> countByHour(@Param("deviceCode") String deviceCode,
                                         @Param("tourismName") String tourismName,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);

    /**
     * 查询最高人数记录
     */
    @Select("SELECT * FROM CLOUDWALK.CROWD_STATISTICS " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "AND COUNT = (SELECT MAX(COUNT) FROM CLOUDWALK.CROWD_STATISTICS " +
            "            WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "            AND (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "            AND RECORD_TIME BETWEEN #{startTime} AND #{endTime})")
    CrowdStatistics findMaxCount(@Param("deviceCode") String deviceCode,
                                @Param("tourismName") String tourismName,
                                @Param("startTime") LocalDateTime startTime,
                                @Param("endTime") LocalDateTime endTime);

    /**
     * 条件分页查询
     */
    @Select("SELECT * FROM CLOUDWALK.CROWD_STATISTICS " +
            "WHERE (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "AND (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND (#{algName} IS NULL OR ALG_NAME = #{algName}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "ORDER BY RECORD_TIME DESC " +
            "OFFSET #{offset} ROWS FETCH NEXT #{limit} ROWS ONLY")
    List<CrowdStatistics> findByConditions(@Param("deviceCode") String deviceCode,
                                          @Param("tourismName") String tourismName,
                                          @Param("algName") String algName,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime,
                                          @Param("offset") Integer offset,
                                          @Param("limit") Integer limit);

    /**
     * 获取人群密度趋势数据
     */
    @Select("SELECT TO_CHAR(RECORD_TIME, 'HH24:MI') as time, " +
            "ROUND(AVG(DENSITY), 2) as density " +
            "FROM CLOUDWALK.CROWD_DENSITY " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY TO_CHAR(RECORD_TIME, 'HH24:MI') " +
            "ORDER BY time")
    List<Map<String, Object>> getDensityTrend(@Param("deviceCode") String deviceCode,
                                             @Param("startTime") LocalDateTime startTime,
                                             @Param("endTime") LocalDateTime endTime);

    /**
     * 获取人群数量趋势数据
     */
    @Select("SELECT TO_CHAR(RECORD_TIME, 'HH24:MI') as time, " +
            "ROUND(AVG(COUNT), 2) as count " +
            "FROM CLOUDWALK.CROWD_COUNT " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND (#{deviceCode} IS NULL OR DEVICE_CODE = #{deviceCode}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY TO_CHAR(RECORD_TIME, 'HH24:MI') " +
            "ORDER BY time")
    List<Map<String, Object>> getCountTrend(@Param("deviceCode") String deviceCode,
                                           @Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime);

    /**
     * 获取热力图数据
     */
    @Select("SELECT cs.DEVICE_CODE as x, cs.DEVICE_NAME as y, cs.COUNT as value " +
            "FROM CLOUDWALK.CROWD_STATISTICS cs " +
            "WHERE cs.TOURISM_NAME = #{tourismName} " +
            "AND cs.RECORD_TIME = #{recordTime}")
    List<Map<String, Object>> getHeatMapData(@Param("tourismName") String tourismName,
                                            @Param("recordTime") LocalDateTime recordTime);

    /**
     * 获取平均人数
     */
    @Select("SELECT AVG(COUNT) " +
            "FROM CLOUDWALK.CROWD_STATISTICS " +
            "WHERE DEVICE_CODE = #{deviceCode} " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime}")
    double getAverageCount(@Param("deviceCode") String deviceCode,
                          @Param("startTime") LocalDateTime startTime,
                          @Param("endTime") LocalDateTime endTime);
} 