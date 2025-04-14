package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.Alert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警数据访问接口
 */
@Mapper
public interface AlertMapper extends BaseMapper<Alert> {
    /**
     * 根据状态查询告警
     */
    @Select("SELECT * FROM CLOUDWALK.ALERT " +
            "WHERE (#{status} IS NULL OR ALERT_STATUS = #{status}) " +
            "ORDER BY RECORD_TIME DESC " +
            "OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY")
    List<Alert> findByStatus(@Param("status") Integer status);

    /**
     * 根据级别和状态查询告警
     */
    @Select("SELECT * FROM CLOUDWALK.ALERT " +
            "WHERE ALERT_LEVEL = #{level} " +
            "AND (#{status} IS NULL OR ALERT_STATUS = #{status}) " +
            "ORDER BY RECORD_TIME DESC " +
            "OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY")
    List<Alert> findByLevelAndStatus(@Param("level") Integer level, @Param("status") Integer status);

    /**
     * 统计时间范围内的告警数量
     */
    @Select("SELECT " +
            "COUNT(*) as total, " +
            "SUM(CASE WHEN ALERT_STATUS = 0 THEN 1 ELSE 0 END) as pending, " +
            "SUM(CASE WHEN ALERT_STATUS = 1 THEN 1 ELSE 0 END) as processed " +
            "FROM CLOUDWALK.ALERT " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime}")
    Map<String, Object> countByTimeRange(@Param("tourismName") String tourismName,
                                       @Param("startTime") LocalDateTime startTime, 
                                       @Param("endTime") LocalDateTime endTime);

    /**
     * 统计告警类型分布
     */
    @Select("SELECT ALERT_TYPE as type, COUNT(*) as count " +
            "FROM CLOUDWALK.ALERT " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY ALERT_TYPE")
    List<Map<String, Object>> getAlertTypeDistribution(@Param("tourismName") String tourismName,
                                                      @Param("startTime") LocalDateTime startTime,
                                                      @Param("endTime") LocalDateTime endTime);

    /**
     * 统计告警级别分布
     */
    @Select("SELECT ALERT_LEVEL as level, COUNT(*) as count " +
            "FROM CLOUDWALK.ALERT " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY ALERT_LEVEL")
    List<Map<String, Object>> getAlertLevelDistribution(@Param("tourismName") String tourismName,
                                                       @Param("startTime") LocalDateTime startTime,
                                                       @Param("endTime") LocalDateTime endTime);

    /**
     * 统计告警时段分布
     */
    @Select("SELECT TO_CHAR(RECORD_TIME, 'HH24') as hour, COUNT(*) as count " +
            "FROM CLOUDWALK.ALERT " +
            "WHERE (#{tourismName} IS NULL OR TOURISM_NAME = #{tourismName}) " +
            "AND RECORD_TIME BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY TO_CHAR(RECORD_TIME, 'HH24') " +
            "ORDER BY hour")
    List<Map<String, Object>> getAlertTimeDistribution(@Param("tourismName") String tourismName,
                                                      @Param("startTime") LocalDateTime startTime,
                                                      @Param("endTime") LocalDateTime endTime);

    /**
     * 批量更新告警状态
     */
    @Update("<script>" +
            "UPDATE CLOUDWALK.ALERT SET " +
            "ALERT_STATUS = #{status}, " +
            "UPDATE_TIME = SYSTIMESTAMP " +
            "WHERE ID IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int batchUpdateStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);

    /**
     * 条件分页查询
     */
    @Select("<script>" +
            "SELECT * FROM CLOUDWALK.ALERT " +
            "WHERE 1=1 " +
            "<if test='tourismName != null and tourismName != \"\"'>" +
            "AND TOURISM_NAME = #{tourismName} " +
            "</if>" +
            "<if test='type != null and type != \"\"'>" +
            "AND ALERT_TYPE = #{type} " +
            "</if>" +
            "<if test='level != null'>" +
            "AND ALERT_LEVEL = #{level} " +
            "</if>" +
            "<if test='status != null'>" +
            "AND ALERT_STATUS = #{status} " +
            "</if>" +
            "<if test='deviceCode != null and deviceCode != \"\"'>" +
            "AND DEVICE_CODE = #{deviceCode} " +
            "</if>" +
            "<if test='startTime != null'>" +
            "AND RECORD_TIME &gt;= #{startTime} " +
            "</if>" +
            "<if test='endTime != null'>" +
            "AND RECORD_TIME &lt;= #{endTime} " +
            "</if>" +
            "ORDER BY RECORD_TIME DESC " +
            "OFFSET #{offset} ROWS FETCH NEXT #{limit} ROWS ONLY" +
            "</script>")
    List<Alert> findByConditions(@Param("tourismName") String tourismName,
                                @Param("type") String type, 
                                @Param("level") Integer level,
                                @Param("status") Integer status, 
                                @Param("deviceCode") String deviceCode,
                                @Param("startTime") LocalDateTime startTime, 
                                @Param("endTime") LocalDateTime endTime,
                                @Param("offset") Integer offset, 
                                @Param("limit") Integer limit);
} 