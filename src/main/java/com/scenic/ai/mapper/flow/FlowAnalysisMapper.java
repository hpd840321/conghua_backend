package com.conghua.tourism.mapper.flow;

import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

/**
 * 流量分析数据访问Mapper
 */
@Mapper
public interface FlowAnalysisMapper {
    
    /**
     * 获取区域实时流量
     */
    @Select({
        "SELECT COUNT(*) as current_flow",
        "FROM t_visitor_record",
        "WHERE enter_time <= #{currentTime}",
        "AND (leave_time IS NULL OR leave_time >= #{currentTime})",
        "AND area_id = #{areaId}"
    })
    int getCurrentFlow(
        @Param("currentTime") LocalDateTime currentTime,
        @Param("areaId") String areaId
    );
    
    /**
     * 获取区域流量趋势
     */
    @Select({
        "SELECT DATE_FORMAT(time_point, #{interval}) as timestamp,",
        "AVG(flow_count) as avg_flow,",
        "MAX(flow_count) as max_flow,",
        "MIN(flow_count) as min_flow",
        "FROM t_flow_record",
        "WHERE time_point BETWEEN #{startTime} AND #{endTime}",
        "AND area_id = #{areaId}",
        "GROUP BY timestamp",
        "ORDER BY timestamp"
    })
    List<Map<String,Object>> getFlowTrend(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("areaId") String areaId,
        @Param("interval") String interval
    );
    
    /**
     * 获取区域流量热力图数据
     */
    @Select({
        "SELECT location_x, location_y,",
        "COUNT(*) as heat_value",
        "FROM t_visitor_location",
        "WHERE record_time BETWEEN #{startTime} AND #{endTime}",
        "AND area_id = #{areaId}",
        "GROUP BY location_x, location_y"
    })
    List<Map<String,Object>> getHeatMapData(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("areaId") String areaId
    );
    
    /**
     * 获取区域路径分析数据
     */
    @Select({
        "SELECT",
        "from_spot_id,",
        "to_spot_id,",
        "COUNT(*) as path_count",
        "FROM (",
        "  SELECT",
        "    r1.spot_id as from_spot_id,",
        "    r2.spot_id as to_spot_id",
        "  FROM t_visitor_record r1",
        "  JOIN t_visitor_record r2 ON r1.visitor_id = r2.visitor_id",
        "  WHERE r1.enter_time BETWEEN #{startTime} AND #{endTime}",
        "  AND r2.enter_time BETWEEN #{startTime} AND #{endTime}",
        "  AND r1.area_id = #{areaId}",
        "  AND r2.area_id = #{areaId}",
        "  AND r2.enter_time > r1.enter_time",
        "  AND NOT EXISTS (",
        "    SELECT 1 FROM t_visitor_record r3",
        "    WHERE r3.visitor_id = r1.visitor_id",
        "    AND r3.enter_time > r1.enter_time",
        "    AND r3.enter_time < r2.enter_time",
        "  )",
        ") paths",
        "GROUP BY from_spot_id, to_spot_id",
        "HAVING path_count >= #{minCount}",
        "ORDER BY path_count DESC"
    })
    List<Map<String,Object>> getPathAnalysis(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("areaId") String areaId,
        @Param("minCount") int minCount
    );
    
    /**
     * 获取区域拥堵预警数据
     */
    @Select({
        "SELECT spot_id,",
        "current_flow,",
        "max_capacity,",
        "current_flow / max_capacity as congestion_rate,",
        "CASE",
        "  WHEN current_flow / max_capacity >= 0.9 THEN '严重拥堵'",
        "  WHEN current_flow / max_capacity >= 0.7 THEN '中度拥堵'",
        "  WHEN current_flow / max_capacity >= 0.5 THEN '轻度拥堵'",
        "  ELSE '通畅'",
        "END as congestion_level",
        "FROM t_spot_flow_monitor",
        "WHERE record_time = #{currentTime}",
        "AND area_id = #{areaId}",
        "AND current_flow / max_capacity >= #{threshold}",
        "ORDER BY congestion_rate DESC"
    })
    List<Map<String,Object>> getCongestionWarning(
        @Param("currentTime") LocalDateTime currentTime,
        @Param("areaId") String areaId,
        @Param("threshold") double threshold
    );
    
    /**
     * 获取景区容量预测数据
     */
    @Select({
        "SELECT forecast_time,",
        "predicted_flow,",
        "confidence_level",
        "FROM t_flow_forecast",
        "WHERE forecast_time > #{startTime}",
        "AND area_id = #{areaId}",
        "ORDER BY forecast_time"
    })
    List<Map<String,Object>> getFlowForecast(
        @Param("startTime") LocalDateTime startTime,
        @Param("areaId") String areaId
    );
} 