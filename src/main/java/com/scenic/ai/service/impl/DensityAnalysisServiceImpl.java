package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.dao.DensityAnalysisMapper;
import com.scenic.ai.model.DensityAnalysis;
import com.scenic.ai.service.IDensityAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 密度分析服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DensityAnalysisServiceImpl extends ServiceImpl<DensityAnalysisMapper, DensityAnalysis> implements IDensityAnalysisService {

    private static final Logger log = LoggerFactory.getLogger(DensityAnalysisServiceImpl.class);
    
    private static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("HH:00");

    /**
     * 分页查询密度分析数据
     *
     * @param page 分页参数
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param algName 算法名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    @Override
    public Page<DensityAnalysis> pageDensityAnalysis(Page<DensityAnalysis> page, String tourismName, String deviceCode,
                                                    String algName, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<DensityAnalysis> wrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(DensityAnalysis::getTourismName, tourismName);
        }
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(DensityAnalysis::getDeviceCode, deviceCode);
        }
        if (StringUtils.hasText(algName)) {
            wrapper.eq(DensityAnalysis::getAlgName, algName);
        }
        if (startTime != null) {
            wrapper.ge(DensityAnalysis::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(DensityAnalysis::getRecordTime, endTime);
        }
        
        // 按记录时间降序排序
        wrapper.orderByDesc(DensityAnalysis::getRecordTime);
        
        return baseMapper.selectPage(page, wrapper);
    }

    /**
     * 根据设备编码查询密度分析数据
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 密度分析数据列表
     */
    @Override
    public List<DensityAnalysis> getByDeviceCode(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        if (StringUtils.isEmpty(deviceCode)) {
            throw new IllegalArgumentException("设备编码不能为空");
        }
        return baseMapper.selectByDeviceCode(deviceCode, startTime, endTime);
    }

    /**
     * 根据景区名称查询密度分析数据
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 密度分析数据列表
     */
    @Override
    public List<DensityAnalysis> getByTourismName(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        if (StringUtils.isEmpty(tourismName)) {
            throw new IllegalArgumentException("景区名称不能为空");
        }
        return baseMapper.selectByTourismName(tourismName, startTime, endTime);
    }

    /**
     * 获取密度趋势数据
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 密度趋势数据
     */
    @Override
    public List<Map<String, Object>> getDensityTrend(String deviceCode, String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        List<DensityAnalysis> densityList;
        
        if (StringUtils.hasText(deviceCode)) {
            densityList = baseMapper.selectByDeviceCode(deviceCode, startTime, endTime);
        } else if (StringUtils.hasText(tourismName)) {
            densityList = baseMapper.selectByTourismName(tourismName, startTime, endTime);
        } else {
            throw new IllegalArgumentException("设备编码和景区名称不能同时为空");
        }
        
        // 按小时分组统计
        Map<String, List<DensityAnalysis>> hourGroups = densityList.stream()
                .collect(Collectors.groupingBy(d -> d.getRecordTime().format(HOUR_FORMATTER)));
        
        return hourGroups.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    List<DensityAnalysis> hourStats = entry.getValue();
                    
                    result.put("hour", entry.getKey());
                    result.put("avgDensity", hourStats.stream()
                            .mapToInt(DensityAnalysis::getDensityCount)
                            .average()
                            .orElse(0));
                    result.put("maxDensity", hourStats.stream()
                            .mapToInt(DensityAnalysis::getDensityCount)
                            .max()
                            .orElse(0));
                    result.put("minDensity", hourStats.stream()
                            .mapToInt(DensityAnalysis::getDensityCount)
                            .min()
                            .orElse(0));
                    
                    return result;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取密度级别分布
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 密度级别分布数据
     */
    @Override
    public List<Map<String, Object>> getDensityLevelDistribution(String deviceCode, String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        List<DensityAnalysis> densityList;
        
        if (StringUtils.hasText(deviceCode)) {
            densityList = baseMapper.selectByDeviceCode(deviceCode, startTime, endTime);
        } else if (StringUtils.hasText(tourismName)) {
            densityList = baseMapper.selectByTourismName(tourismName, startTime, endTime);
        } else {
            throw new IllegalArgumentException("设备编码和景区名称不能同时为空");
        }
        
        // 按密度级别分组统计
        Map<String, List<DensityAnalysis>> levelGroups = densityList.stream()
                .collect(Collectors.groupingBy(DensityAnalysis::getDensityLevel));
        
        return levelGroups.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    List<DensityAnalysis> levelStats = entry.getValue();
                    
                    result.put("level", entry.getKey());
                    result.put("count", levelStats.size());
                    result.put("avgDensity", levelStats.stream()
                            .mapToInt(DensityAnalysis::getDensityCount)
                            .average()
                            .orElse(0));
                    
                    return result;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取高峰时段
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 高峰时段数据
     */
    @Override
    public List<Map<String, Object>> getPeakHours(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        if (StringUtils.isEmpty(deviceCode)) {
            throw new IllegalArgumentException("设备编码不能为空");
        }
        
        List<DensityAnalysis> densityList = baseMapper.selectByDeviceCode(deviceCode, startTime, endTime);
        
        // 按小时分组统计
        Map<String, List<DensityAnalysis>> hourGroups = densityList.stream()
                .collect(Collectors.groupingBy(d -> d.getRecordTime().format(HOUR_FORMATTER)));
        
        return hourGroups.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    List<DensityAnalysis> hourStats = entry.getValue();
                    
                    result.put("hour", entry.getKey());
                    result.put("avgDensity", hourStats.stream()
                            .mapToInt(DensityAnalysis::getDensityCount)
                            .average()
                            .orElse(0));
                    result.put("maxDensity", hourStats.stream()
                            .mapToInt(DensityAnalysis::getDensityCount)
                            .max()
                            .orElse(0));
                    result.put("count", hourStats.size());
                    
                    return result;
                })
                .sorted((a, b) -> Double.compare((Double) b.get("avgDensity"), (Double) a.get("avgDensity")))
                .collect(Collectors.toList());
    }

    /**
     * 获取总密度计数
     *
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 总密度计数
     */
    @Override
    public int getTotalDensityCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        if (StringUtils.isEmpty(deviceCode)) {
            throw new IllegalArgumentException("设备编码不能为空");
        }
        
        List<DensityAnalysis> densityList = baseMapper.selectByDeviceCode(deviceCode, startTime, endTime);
        
        return densityList.stream()
                .mapToInt(DensityAnalysis::getDensityCount)
                .sum();
    }

    /**
     * 获取设备最新的密度分析数据
     *
     * @param deviceCode 设备编码
     * @return 最新的密度分析数据
     */
    @Override
    public DensityAnalysis getLatestDensityByDevice(String deviceCode) {
        if (StringUtils.isEmpty(deviceCode)) {
            throw new IllegalArgumentException("设备编码不能为空");
        }
        
        return baseMapper.selectLatestByDeviceCode(deviceCode);
    }

    /**
     * 根据时间范围和景区名称获取密度分析数据
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param tourismName 景区名称
     * @return 密度分析数据列表
     */
    @Override
    public List<DensityAnalysis> getDensityByTimeRangeAndTourism(LocalDateTime startTime, LocalDateTime endTime, String tourismName) {
        if (StringUtils.isEmpty(tourismName)) {
            throw new IllegalArgumentException("景区名称不能为空");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("开始时间和结束时间不能为空");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }
        
        return baseMapper.selectByTourismName(tourismName, startTime, endTime);
    }

    /**
     * 获取密度统计概览
     *
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 密度统计概览
     */
    @Override
    public Map<String, Object> getDensityStatistics(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        if (StringUtils.isEmpty(tourismName)) {
            throw new IllegalArgumentException("景区名称不能为空");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("开始时间和结束时间不能为空");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        try {
            // 获取指定景区和时间范围内的密度分析数据
            List<DensityAnalysis> densityList = baseMapper.selectByTourismName(tourismName, startTime, endTime);
            
            // 初始化统计结果
            Map<String, Object> result = new HashMap<>();
            result.put("tourismName", tourismName);
            result.put("startTime", startTime);
            result.put("endTime", endTime);
            result.put("totalRecords", densityList.size());
            
            if (densityList.isEmpty()) {
                result.put("avgDensity", 0);
                result.put("maxDensity", 0);
                result.put("minDensity", 0);
                result.put("highDensityCount", 0);
                result.put("mediumDensityCount", 0);
                result.put("lowDensityCount", 0);
                return result;
            }
            
            // 计算密度统计
            int totalDensity = 0;
            int maxDensity = Integer.MIN_VALUE;
            int minDensity = Integer.MAX_VALUE;
            int highDensityCount = 0;
            int mediumDensityCount = 0;
            int lowDensityCount = 0;
            
            for (DensityAnalysis density : densityList) {
                int densityValue = density.getDensityCount();
                totalDensity += densityValue;
                
                if (densityValue > maxDensity) {
                    maxDensity = densityValue;
                }
                if (densityValue < minDensity) {
                    minDensity = densityValue;
                }
                
                // 根据密度级别统计
                String level = density.getDensityLevel();
                if ("高".equals(level)) {
                    highDensityCount++;
                } else if ("中".equals(level)) {
                    mediumDensityCount++;
                } else if ("低".equals(level)) {
                    lowDensityCount++;
                }
            }
            
            // 计算平均密度
            double avgDensity = densityList.isEmpty() ? 0 : (double) totalDensity / densityList.size();
            
            // 设置统计结果
            result.put("avgDensity", avgDensity);
            result.put("maxDensity", maxDensity);
            result.put("minDensity", minDensity);
            result.put("highDensityCount", highDensityCount);
            result.put("mediumDensityCount", mediumDensityCount);
            result.put("lowDensityCount", lowDensityCount);
            
            return result;
        } catch (Exception e) {
            log.error("获取景区 {} 密度统计概览失败", tourismName, e);
            throw new RuntimeException("获取密度统计概览失败", e);
        }
    }
} 