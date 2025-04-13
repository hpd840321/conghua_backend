package com.conghua.tourism.service.visitor.impl;

import com.conghua.tourism.service.visitor.VisitorStatsService;
import com.conghua.tourism.model.visitor.VisitorStats;
import com.conghua.tourism.mapper.visitor.VisitorStatsMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 访客统计服务实现类
 */
@Service
public class VisitorStatsServiceImpl implements VisitorStatsService {

    @Autowired
    private VisitorStatsMapper visitorStatsMapper;
    
    @Override
    public VisitorStats getRealTimeStats(String areaId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
        
        // 构建统计数据
        return VisitorStats.builder()
            .areaId(areaId)
            .currentVisitorCount(visitorStatsMapper.getRealTimeVisitorCount(now, areaId))
            .todayTotalVisitors(visitorStatsMapper.getRealTimeVisitorCount(todayStart, areaId))
            .sourceDistribution(convertToMap(visitorStatsMapper.getVisitorSourceDistribution(todayStart, now, areaId, 10)))
            .ageDistribution(convertToMap(visitorStatsMapper.getVisitorAgeDistribution(todayStart, now, areaId)))
            .genderDistribution(convertToMap(visitorStatsMapper.getVisitorGenderDistribution(todayStart, now, areaId)))
            .stayDurationDistribution(convertToMap(visitorStatsMapper.getStayDurationDistribution(todayStart, now, areaId)))
            .hotSpotRanking(convertToMap(visitorStatsMapper.getHotSpotRanking(todayStart, now, areaId, 10)))
            .statsTime(now)
            .build();
    }
    
    @Override
    public List<Map<String,Object>> getVisitorTrend(String areaId, LocalDateTime startTime, LocalDateTime endTime, String interval) {
        return visitorStatsMapper.getVisitorTrend(startTime, endTime, areaId, interval);
    }
    
    @Override
    public List<Map<String,Object>> getSourceDistribution(String areaId, LocalDateTime startTime, LocalDateTime endTime, int limit) {
        return visitorStatsMapper.getVisitorSourceDistribution(startTime, endTime, areaId, limit);
    }
    
    @Override
    public List<Map<String,Object>> getAgeDistribution(String areaId, LocalDateTime startTime, LocalDateTime endTime) {
        return visitorStatsMapper.getVisitorAgeDistribution(startTime, endTime, areaId);
    }
    
    @Override
    public List<Map<String,Object>> getGenderDistribution(String areaId, LocalDateTime startTime, LocalDateTime endTime) {
        return visitorStatsMapper.getVisitorGenderDistribution(startTime, endTime, areaId);
    }
    
    @Override
    public List<Map<String,Object>> getHotSpotRanking(String areaId, LocalDateTime startTime, LocalDateTime endTime, int limit) {
        return visitorStatsMapper.getHotSpotRanking(startTime, endTime, areaId, limit);
    }
    
    @Override
    public List<Map<String,Object>> getStayDurationDistribution(String areaId, LocalDateTime startTime, LocalDateTime endTime) {
        return visitorStatsMapper.getStayDurationDistribution(startTime, endTime, areaId);
    }
    
    /**
     * 将List<Map>转换为Map<String,Integer>
     */
    private Map<String,Integer> convertToMap(List<Map<String,Object>> list) {
        return list.stream()
            .collect(java.util.stream.Collectors.toMap(
                m -> String.valueOf(m.get("key")),
                m -> ((Number)m.get("value")).intValue()
            ));
    }
} 