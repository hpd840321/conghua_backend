package com.conghua.tourism.service.visitor;

import com.conghua.tourism.model.visitor.VisitorStats;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 访客统计服务接口
 */
public interface VisitorStatsService {
    
    /**
     * 获取区域实时访客统计
     *
     * @param areaId 区域ID
     * @return 访客统计数据
     */
    VisitorStats getRealTimeStats(String areaId);
    
    /**
     * 获取区域访客趋势
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param interval 时间间隔(如 %Y-%m-%d, %Y-%m-%d %H:00:00)
     * @return 趋势数据
     */
    List<Map<String,Object>> getVisitorTrend(String areaId, LocalDateTime startTime, LocalDateTime endTime, String interval);
    
    /**
     * 获取区域访客来源分布
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param limit 返回记录数限制
     * @return 来源分布数据
     */
    List<Map<String,Object>> getSourceDistribution(String areaId, LocalDateTime startTime, LocalDateTime endTime, int limit);
    
    /**
     * 获取区域访客年龄分布
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 年龄分布数据
     */
    List<Map<String,Object>> getAgeDistribution(String areaId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取区域访客性别分布
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 性别分布数据
     */
    List<Map<String,Object>> getGenderDistribution(String areaId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取热门景点排行
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param limit 返回记录数限制
     * @return 热门景点数据
     */
    List<Map<String,Object>> getHotSpotRanking(String areaId, LocalDateTime startTime, LocalDateTime endTime, int limit);
    
    /**
     * 获取游客停留时长分布
     *
     * @param areaId 区域ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 停留时长分布数据
     */
    List<Map<String,Object>> getStayDurationDistribution(String areaId, LocalDateTime startTime, LocalDateTime endTime);
} 