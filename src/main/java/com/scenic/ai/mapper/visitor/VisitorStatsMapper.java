package com.conghua.tourism.mapper.visitor;

import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

/**
 * 访客统计数据访问Mapper
 */
@Mapper
public interface VisitorStatsMapper {
    
    /**
     * 获取实时访客数量
     */
    @Select({
        "SELECT COUNT(DISTINCT visitor_id) as count",
        "FROM t_visitor_record",
        "WHERE enter_time >= #{startTime}",
        "AND area_id = #{areaId}"
    })
    int getRealTimeVisitorCount(
        @Param("startTime") LocalDateTime startTime,
        @Param("areaId") String areaId
    );
    
    /**
     * 获取区域访客趋势
     */
    @Select({
        "SELECT DATE_FORMAT(enter_time, #{interval}) as timestamp,",
        "COUNT(DISTINCT visitor_id) as visitor_count,",
        "AVG(stay_time) as avg_stay_time",
        "FROM t_visitor_record",
        "WHERE enter_time BETWEEN #{startTime} AND #{endTime}",
        "AND area_id = #{areaId}",
        "GROUP BY timestamp",
        "ORDER BY timestamp"
    })
    List<Map<String,Object>> getVisitorTrend(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("areaId") String areaId,
        @Param("interval") String interval
    );
    
    /**
     * 获取区域访客来源分布
     */
    @Select({
        "SELECT source_city as city,",
        "COUNT(DISTINCT visitor_id) as visitor_count",
        "FROM t_visitor_record",
        "WHERE enter_time BETWEEN #{startTime} AND #{endTime}",
        "AND area_id = #{areaId}",
        "GROUP BY source_city",
        "ORDER BY visitor_count DESC",
        "LIMIT #{limit}"
    })
    List<Map<String,Object>> getVisitorSourceDistribution(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("areaId") String areaId,
        @Param("limit") int limit
    );
    
    /**
     * 获取区域访客年龄分布
     */
    @Select({
        "SELECT",
        "CASE",
        "  WHEN age < 18 THEN '未成年'",
        "  WHEN age BETWEEN 18 AND 25 THEN '18-25岁'", 
        "  WHEN age BETWEEN 26 AND 35 THEN '26-35岁'",
        "  WHEN age BETWEEN 36 AND 50 THEN '36-50岁'",
        "  ELSE '50岁以上'",
        "END as age_group,",
        "COUNT(DISTINCT visitor_id) as visitor_count",
        "FROM t_visitor_record",
        "WHERE enter_time BETWEEN #{startTime} AND #{endTime}",
        "AND area_id = #{areaId}",
        "GROUP BY age_group",
        "ORDER BY visitor_count DESC"
    })
    List<Map<String,Object>> getVisitorAgeDistribution(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("areaId") String areaId
    );
    
    /**
     * 获取区域访客性别分布
     */
    @Select({
        "SELECT gender,",
        "COUNT(DISTINCT visitor_id) as visitor_count",
        "FROM t_visitor_record",
        "WHERE enter_time BETWEEN #{startTime} AND #{endTime}",
        "AND area_id = #{areaId}",
        "GROUP BY gender"
    })
    List<Map<String,Object>> getVisitorGenderDistribution(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("areaId") String areaId
    );
    
    /**
     * 获取热门景点排行
     */
    @Select({
        "SELECT r.spot_id, s.name as spot_name,",
        "COUNT(DISTINCT r.visitor_id) as visitor_count,",
        "AVG(r.stay_time) as avg_stay_time",
        "FROM t_visitor_record r",
        "JOIN t_scenic_spot s ON r.spot_id = s.id",
        "WHERE r.enter_time BETWEEN #{startTime} AND #{endTime}",
        "AND r.area_id = #{areaId}",
        "GROUP BY r.spot_id, s.name",
        "ORDER BY visitor_count DESC",
        "LIMIT #{limit}"
    })
    List<Map<String,Object>> getHotSpotRanking(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("areaId") String areaId,
        @Param("limit") int limit
    );
    
    /**
     * 获取游客停留时长分布
     */
    @Select({
        "SELECT",
        "CASE",
        "  WHEN stay_time <= 30 THEN '30分钟以内'",
        "  WHEN stay_time <= 60 THEN '30-60分钟'",
        "  WHEN stay_time <= 120 THEN '1-2小时'",
        "  WHEN stay_time <= 240 THEN '2-4小时'",
        "  ELSE '4小时以上'",
        "END as duration_group,",
        "COUNT(DISTINCT visitor_id) as visitor_count",
        "FROM t_visitor_record",
        "WHERE enter_time BETWEEN #{startTime} AND #{endTime}",
        "AND area_id = #{areaId}",
        "GROUP BY duration_group",
        "ORDER BY visitor_count DESC"
    })
    List<Map<String,Object>> getStayDurationDistribution(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("areaId") String areaId
    );
} 