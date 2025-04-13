package com.conghua.tourism.mapper.alarm;

import com.conghua.tourism.model.alarm.*;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

/**
 * 告警数据访问Mapper
 */
@Mapper
public interface AlarmMapper {
    
    /**
     * 查询告警列表
     */
    @SelectProvider(type = AlarmSqlProvider.class, method = "buildFindAlarmsSQL")
    List<AlarmData> findAlarms(@Param("params") AlarmQueryParams params);
    
    /**
     * 统计告警总数
     */
    @SelectProvider(type = AlarmSqlProvider.class, method = "buildCountAlarmsSQL") 
    long countAlarms(@Param("params") AlarmQueryParams params);
    
    /**
     * 获取告警统计数据
     */
    @Select({
        "SELECT COUNT(*) as total,",
        "SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) as pending,",
        "SUM(CASE WHEN status = 'PROCESSING' THEN 1 ELSE 0 END) as processing,", 
        "SUM(CASE WHEN status = 'RESOLVED' THEN 1 ELSE 0 END) as resolved,",
        "SUM(CASE WHEN status = 'IGNORED' THEN 1 ELSE 0 END) as ignored,",
        "SUM(CASE WHEN status = 'AUTO_RESOLVED' THEN 1 ELSE 0 END) as autoResolved",
        "FROM t_alarm"
    })
    AlarmStats getAlarmStats();
    
    /**
     * 按级别统计告警数量
     */
    @Select("SELECT level, COUNT(*) as count FROM t_alarm GROUP BY level")
    List<Map<String,Object>> getLevelStats();
    
    /**
     * 按类型统计告警数量
     */
    @Select("SELECT type, COUNT(*) as count FROM t_alarm GROUP BY type")
    List<Map<String,Object>> getTypeStats();
    
    /**
     * 按区域统计告警数量
     */
    @Select("SELECT area_id, COUNT(*) as count FROM t_alarm GROUP BY area_id")
    List<Map<String,Object>> getAreaStats();
    
    /**
     * 查询告警配置列表
     */
    @Select("SELECT * FROM t_alarm_config")
    List<AlarmConfig> findAllConfigs();
    
    /**
     * 更新告警配置
     */
    @Update({
        "UPDATE t_alarm_config",
        "SET type = #{config.type},",
        "    level = #{config.level},",
        "    area_id = #{config.areaId},",
        "    area_name = #{config.areaName},",
        "    device_ids = #{config.deviceIds},",
        "    threshold = #{config.threshold},",
        "    duration = #{config.duration},",
        "    interval = #{config.interval},",
        "    enabled = #{config.enabled},",
        "    notification_channels = #{config.notificationChannels},",
        "    notification_template = #{config.notificationTemplate},",
        "    update_time = NOW(),",
        "    updater = #{config.updater}",
        "WHERE id = #{id}"
    })
    void updateConfig(@Param("id") String id, @Param("config") AlarmConfig config);
    
    /**
     * 处理告警
     */
    @Update({
        "UPDATE t_alarm",
        "SET status = #{params.targetStatus},",
        "    handle_time = #{params.handleTime},",
        "    handler = #{params.handler},",
        "    handle_remark = #{params.remark},",
        "    update_time = NOW()",
        "WHERE id = #{id}"
    })
    void handleAlarm(@Param("id") String id, @Param("params") AlarmHandleParams params);
    
    /**
     * 删除告警
     */
    @Delete("DELETE FROM t_alarm WHERE id = #{id}")
    void deleteAlarm(@Param("id") String id);
    
    /**
     * 查询实时告警数据
     */
    @Select({
        "SELECT * FROM t_alarm",
        "WHERE area_id = #{areaId}",
        "AND status = 'PENDING'",
        "ORDER BY occur_time DESC"
    })
    List<AlarmData> findRealtimeAlarms(@Param("areaId") String areaId);
    
    /**
     * 获取告警趋势数据
     */
    @SelectProvider(type = AlarmSqlProvider.class, method = "buildGetAlarmTrendsSQL")
    List<AlarmTrendData> getAlarmTrends(
        @Param("startTime") String startTime,
        @Param("endTime") String endTime,
        @Param("interval") String interval
    );
} 