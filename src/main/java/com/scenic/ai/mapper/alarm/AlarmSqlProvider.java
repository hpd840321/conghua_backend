package com.conghua.tourism.mapper.alarm;

import com.conghua.tourism.model.alarm.AlarmQueryParams;
import org.apache.ibatis.jdbc.SQL;
import java.util.List;

/**
 * 告警SQL构建器
 */
public class AlarmSqlProvider {
    
    /**
     * 构建查询告警列表的SQL
     */
    public String buildFindAlarmsSQL(AlarmQueryParams params) {
        return new SQL() {{
            SELECT("*");
            FROM("t_alarm");
            
            if (params.getTypes() != null && !params.getTypes().isEmpty()) {
                WHERE("type IN #{params.types}");
            }
            
            if (params.getLevels() != null && !params.getLevels().isEmpty()) {
                WHERE("level IN #{params.levels}");
            }
            
            if (params.getStatuses() != null && !params.getStatuses().isEmpty()) {
                WHERE("status IN #{params.statuses}");
            }
            
            if (params.getAreaIds() != null && !params.getAreaIds().isEmpty()) {
                WHERE("area_id IN #{params.areaIds}");
            }
            
            if (params.getDeviceIds() != null && !params.getDeviceIds().isEmpty()) {
                WHERE("device_id IN #{params.deviceIds}");
            }
            
            if (params.getStartTime() != null) {
                WHERE("occur_time >= #{params.startTime}");
            }
            
            if (params.getEndTime() != null) {
                WHERE("occur_time <= #{params.endTime}");
            }
            
            if (params.getHandler() != null) {
                WHERE("handler = #{params.handler}");
            }
            
            if (params.getKeyword() != null) {
                WHERE("(message LIKE CONCAT('%',#{params.keyword},'%') OR device_name LIKE CONCAT('%',#{params.keyword},'%'))");
            }
            
            if (params.getSortField() != null) {
                ORDER_BY(params.getSortField() + " " + (params.getSortOrder() != null ? params.getSortOrder() : "DESC"));
            } else {
                ORDER_BY("occur_time DESC");
            }
            
            LIMIT("#{params.pageSize}");
            OFFSET("#{params.pageNum} * #{params.pageSize}");
        }}.toString();
    }
    
    /**
     * 构建统计告警数量的SQL
     */
    public String buildCountAlarmsSQL(AlarmQueryParams params) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("t_alarm");
            
            if (params.getTypes() != null && !params.getTypes().isEmpty()) {
                WHERE("type IN #{params.types}");
            }
            
            if (params.getLevels() != null && !params.getLevels().isEmpty()) {
                WHERE("level IN #{params.levels}");
            }
            
            if (params.getStatuses() != null && !params.getStatuses().isEmpty()) {
                WHERE("status IN #{params.statuses}");
            }
            
            if (params.getAreaIds() != null && !params.getAreaIds().isEmpty()) {
                WHERE("area_id IN #{params.areaIds}");
            }
            
            if (params.getDeviceIds() != null && !params.getDeviceIds().isEmpty()) {
                WHERE("device_id IN #{params.deviceIds}");
            }
            
            if (params.getStartTime() != null) {
                WHERE("occur_time >= #{params.startTime}");
            }
            
            if (params.getEndTime() != null) {
                WHERE("occur_time <= #{params.endTime}");
            }
            
            if (params.getHandler() != null) {
                WHERE("handler = #{params.handler}");
            }
            
            if (params.getKeyword() != null) {
                WHERE("(message LIKE CONCAT('%',#{params.keyword},'%') OR device_name LIKE CONCAT('%',#{params.keyword},'%'))");
            }
        }}.toString();
    }
    
    /**
     * 构建获取告警趋势数据的SQL
     */
    public String buildGetAlarmTrendsSQL() {
        return new SQL() {{
            SELECT("DATE_FORMAT(occur_time, #{interval}) as timestamp");
            SELECT("COUNT(*) as total");
            SELECT("SUM(CASE WHEN status = 'PENDING' THEN 1 ELSE 0 END) as newCount");
            SELECT("SUM(CASE WHEN status IN ('RESOLVED','IGNORED','AUTO_RESOLVED') THEN 1 ELSE 0 END) as handledCount");
            SELECT("AVG(TIMESTAMPDIFF(MINUTE, occur_time, handle_time)) as avgHandleTime");
            FROM("t_alarm");
            WHERE("occur_time BETWEEN #{startTime} AND #{endTime}");
            GROUP_BY("timestamp");
            ORDER_BY("timestamp ASC");
        }}.toString();
    }
} 