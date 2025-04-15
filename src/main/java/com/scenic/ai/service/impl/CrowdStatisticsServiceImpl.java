package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.dao.CrowdStatisticsMapper;
import com.scenic.ai.model.CrowdStatistics;
import com.scenic.ai.service.ICrowdStatisticsService;
import com.scenic.ai.exception.BusinessException;
import com.scenic.ai.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 人群统计服务实现类
 * 提供人群密度统计、分布分析、趋势分析等功能
 *
 * @author scenic
 * @date 2024-03-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CrowdStatisticsServiceImpl extends ServiceImpl<CrowdStatisticsMapper, CrowdStatistics> implements ICrowdStatisticsService {

    private static final Logger log = LoggerFactory.getLogger(CrowdStatisticsServiceImpl.class);
    
    @Autowired
    private CrowdStatisticsMapper crowdStatisticsMapper;
    
    private static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("HH:00");
    
    // 密度阈值常量
    private static final BigDecimal HIGH_DENSITY_THRESHOLD = new BigDecimal("0.8");
    private static final BigDecimal MEDIUM_DENSITY_THRESHOLD = new BigDecimal("0.5");

    /**
     * 构造函数，注入依赖
     */
    public CrowdStatisticsServiceImpl(CrowdStatisticsMapper crowdStatisticsMapper) {
        this.crowdStatisticsMapper = crowdStatisticsMapper;
    }

    /**
     * 分页查询人群统计数据
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    @Override
    public IPage<CrowdStatistics> getPage(Integer pageNum, Integer pageSize, String tourismName,
                                        String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        Page<CrowdStatistics> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CrowdStatistics> wrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(CrowdStatistics::getTourismName, tourismName);
        }
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(CrowdStatistics::getDeviceCode, deviceCode);
        }
        if (startTime != null) {
            wrapper.ge(CrowdStatistics::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(CrowdStatistics::getRecordTime, endTime);
        }
        
        // 按记录时间降序排序
        wrapper.orderByDesc(CrowdStatistics::getRecordTime);
        
        return crowdStatisticsMapper.selectPage(page, wrapper);
    }

    /**
     * 根据设备编码查询统计数据
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据列表
     */
    @Override
    public List<CrowdStatistics> getByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrowdStatistics::getDeviceCode, deviceCode);
        if (startTime != null) {
            wrapper.ge(CrowdStatistics::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(CrowdStatistics::getRecordTime, endTime);
        }
        wrapper.orderByDesc(CrowdStatistics::getRecordTime);
        return crowdStatisticsMapper.selectList(wrapper);
    }

    /**
     * 根据景区名称查询统计数据
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据列表
     */
    @Override
    public List<CrowdStatistics> getByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrowdStatistics::getTourismName, tourismName);
        if (startTime != null) {
            wrapper.ge(CrowdStatistics::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(CrowdStatistics::getRecordTime, endTime);
        }
        wrapper.orderByDesc(CrowdStatistics::getRecordTime);
        return crowdStatisticsMapper.selectList(wrapper);
    }

    /**
     * 获取时段人群分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 时段分布数据
     */
    @Override
    public List<Map<String, Object>> getHourDistribution(String deviceCode, String tourismName,
                                                        LocalDateTime startTime, LocalDateTime endTime) {
        List<CrowdStatistics> statistics = getStatisticsList(deviceCode, tourismName, startTime, endTime);
        
        // 按小时分组统计
        Map<String, List<CrowdStatistics>> hourGroups = statistics.stream()
                .collect(Collectors.groupingBy(s -> s.getRecordTime().format(HOUR_FORMATTER)));
        
        return hourGroups.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    List<CrowdStatistics> hourStats = entry.getValue();
                    
                    result.put("hour", entry.getKey());
                    result.put("total_count", hourStats.stream().mapToInt(CrowdStatistics::getCount).sum());
                    result.put("record_count", hourStats.size());
                    result.put("avg_count", hourStats.stream().mapToInt(CrowdStatistics::getCount).average().orElse(0));
                    result.put("avg_density", hourStats.stream()
                            .map(CrowdStatistics::getDensity)
                            .mapToDouble(BigDecimal::doubleValue)
                            .average()
                            .orElse(0));
                    
                    return result;
                })
                .sorted(Comparator.comparing(m -> (String) m.get("hour")))
                .collect(Collectors.toList());
    }

    /**
     * 获取密度分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 密度分布数据
     */
    @Override
    public List<Map<String, Object>> getDensityDistribution(String deviceCode, String tourismName,
                                                           LocalDateTime startTime, LocalDateTime endTime) {
        List<CrowdStatistics> statistics = getStatisticsList(deviceCode, tourismName, startTime, endTime);
        
        // 定义密度等级
        Map<String, List<CrowdStatistics>> densityGroups = statistics.stream()
                .collect(Collectors.groupingBy(s -> getDensityLevel(s.getDensity())));
        
        return densityGroups.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    List<CrowdStatistics> densityStats = entry.getValue();
                    
                    result.put("density_level", entry.getKey());
                    result.put("count", densityStats.size());
                    result.put("avg_count", densityStats.stream().mapToInt(CrowdStatistics::getCount).average().orElse(0));
                    result.put("avg_density", densityStats.stream()
                            .map(CrowdStatistics::getDensity)
                            .mapToDouble(BigDecimal::doubleValue)
                            .average()
                            .orElse(0));
                    
                    return result;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取人群趋势
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 趋势数据
     */
    @Override
    public List<Map<String, Object>> getTrend(String deviceCode, String tourismName,
                                             LocalDateTime startTime, LocalDateTime endTime) {
        List<CrowdStatistics> statistics = getStatisticsList(deviceCode, tourismName, startTime, endTime);
        
        return statistics.stream()
                .map(s -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("time", s.getRecordTime());
                    result.put("total_count", s.getCount());
                    result.put("avg_density", s.getDensity());
                    return result;
                })
                .sorted(Comparator.comparing(m -> (LocalDateTime) m.get("time")))
                .collect(Collectors.toList());
    }

    /**
     * 获取统计概览
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 概览数据
     */
    @Override
    public Map<String, Object> getOverview(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        List<CrowdStatistics> statistics = getStatisticsList(null, tourismName, startTime, endTime);
        
        Map<String, Object> result = new HashMap<>();
        result.put("totalCount", statistics.stream().mapToInt(CrowdStatistics::getCount).sum());
        result.put("avgDensity", statistics.stream()
                .map(CrowdStatistics::getDensity)
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0));
        result.put("maxCount", statistics.stream().mapToInt(CrowdStatistics::getCount).max().orElse(0));
        result.put("maxDensity", statistics.stream()
                .map(CrowdStatistics::getDensity)
                .mapToDouble(BigDecimal::doubleValue)
                .max()
                .orElse(0));
        result.put("recordCount", statistics.size());
        
        return result;
    }

    /**
     * 获取设备最新人群统计数据
     *
     * @param deviceCode 设备编码
     * @return 最新统计数据
     */
    @Override
    public CrowdStatistics getLatest(String deviceCode) {
        if (StringUtils.isEmpty(deviceCode)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编码不能为空");
        }
        try {
            CrowdStatistics statistics = crowdStatisticsMapper.selectLatestByDevice(deviceCode);
            if (statistics == null) {
                throw new BusinessException(ErrorCode.DATA_NOT_FOUND, "未找到设备编码为 " + deviceCode + " 的统计数据");
            }
            return statistics;
        } catch (Exception e) {
            log.error("获取设备 {} 最新统计数据失败", deviceCode, e);
            throw new BusinessException(ErrorCode.QUERY_ERROR, "获取最新统计数据失败");
        }
    }

    /**
     * 获取高密度区域统计
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param densityThreshold 密度阈值
     * @return 高密度区域统计数据
     */
    @Override
    public List<Map<String, Object>> getHighDensity(String deviceCode, String tourismName,
                                                   LocalDateTime startTime, LocalDateTime endTime,
                                                   Double densityThreshold) {
        List<CrowdStatistics> statistics = getStatisticsList(deviceCode, tourismName, startTime, endTime);
        
        // 按设备分组统计高密度区域
        Map<String, List<CrowdStatistics>> deviceGroups = statistics.stream()
                .filter(s -> s.getDensity().doubleValue() >= densityThreshold)
                .collect(Collectors.groupingBy(CrowdStatistics::getDeviceCode));
        
        return deviceGroups.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    List<CrowdStatistics> deviceStats = entry.getValue();
                    CrowdStatistics first = deviceStats.get(0);
                    
                    result.put("deviceCode", entry.getKey());
                    result.put("deviceName", first.getDeviceName());
                    result.put("occurrences", deviceStats.size());
                    result.put("avgDensity", deviceStats.stream()
                            .map(CrowdStatistics::getDensity)
                            .mapToDouble(BigDecimal::doubleValue)
                            .average()
                            .orElse(0));
                    result.put("maxDensity", deviceStats.stream()
                            .map(CrowdStatistics::getDensity)
                            .mapToDouble(BigDecimal::doubleValue)
                            .max()
                            .orElse(0));
                    result.put("minDensity", deviceStats.stream()
                            .map(CrowdStatistics::getDensity)
                            .mapToDouble(BigDecimal::doubleValue)
                            .min()
                            .orElse(0));
                    
                    return result;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取统计数据列表
     */
    private List<CrowdStatistics> getStatisticsList(String deviceCode, String tourismName,
                                                   LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<CrowdStatistics> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(CrowdStatistics::getDeviceCode, deviceCode);
        }
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(CrowdStatistics::getTourismName, tourismName);
        }
        if (startTime != null) {
            wrapper.ge(CrowdStatistics::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(CrowdStatistics::getRecordTime, endTime);
        }
        wrapper.orderByAsc(CrowdStatistics::getRecordTime);
        return crowdStatisticsMapper.selectList(wrapper);
    }

    /**
     * 获取密度等级
     */
    private String getDensityLevel(BigDecimal density) {
        double value = density.doubleValue();
        if (value < 0.3) {
            return "低密度";
        } else if (value < 0.7) {
            return "中密度";
        } else {
            return "高密度";
        }
    }
}