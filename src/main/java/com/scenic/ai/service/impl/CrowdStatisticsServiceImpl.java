package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.dao.CrowdStatisticsMapper;
import com.scenic.ai.model.CrowdStatistics;
import com.scenic.ai.service.CrowdStatisticsService;
import com.scenic.ai.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 人群统计服务实现类
 * 提供人群密度统计、分布分析、趋势分析等功能
 *
 * @author scenic
 * @date 2024-03-19
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CrowdStatisticsServiceImpl extends ServiceImpl<CrowdStatisticsMapper, CrowdStatistics> 
        implements CrowdStatisticsService {

    private final CrowdStatisticsMapper crowdStatisticsMapper;
    
    // 密度阈值常量
    private static final BigDecimal HIGH_DENSITY_THRESHOLD = new BigDecimal("0.8");
    private static final BigDecimal MEDIUM_DENSITY_THRESHOLD = new BigDecimal("0.5");

    /**
     * 分页查询人群统计数据
     *
     * @param page 分页参数
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    @Override
    public IPage<CrowdStatistics> page(Page<CrowdStatistics> page, String deviceCode, String tourismName,
                                      LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询人群统计数据: page={}, deviceCode={}, tourismName={}, startTime={}, endTime={}",
                page, deviceCode, tourismName, startTime, endTime);
        
        // 参数校验
        Assert.notNull(page, "分页参数不能为空");
        Assert.isTrue(page.getCurrent() > 0, "页码必须大于0");
        Assert.isTrue(page.getSize() > 0, "每页大小必须大于0");
                
        LambdaQueryWrapper<CrowdStatistics> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(CrowdStatistics::getTourismName, tourismName);
        }
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq(CrowdStatistics::getDeviceCode, deviceCode);
        }
        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new BusinessException("开始时间不能晚于结束时间");
            }
            wrapper.between(CrowdStatistics::getRecordTime, startTime, endTime);
        }
        
        wrapper.orderByDesc(CrowdStatistics::getRecordTime);
        
        try {
            return page(page, wrapper);
        } catch (Exception e) {
            log.error("分页查询人群统计数据失败: {}", e.getMessage());
            throw new BusinessException("查询失败");
        }
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
        log.info("根据设备编码查询统计数据: deviceCode={}, startTime={}, endTime={}", deviceCode, startTime, endTime);
        Assert.hasText(deviceCode, "设备编码不能为空");
        validateTimeRange(startTime, endTime);
        return crowdStatisticsMapper.selectByDevice(deviceCode, startTime, endTime);
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
        log.info("根据景区名称查询统计数据: tourismName={}, startTime={}, endTime={}", tourismName, startTime, endTime);
        Assert.hasText(tourismName, "景区名称不能为空");
        validateTimeRange(startTime, endTime);
        return crowdStatisticsMapper.selectByTourism(tourismName, startTime, endTime);
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
        log.info("获取时段人群分布: deviceCode={}, tourismName={}, startTime={}, endTime={}",
                deviceCode, tourismName, startTime, endTime);
        validateTimeRange(startTime, endTime);
        return crowdStatisticsMapper.selectHourDistribution(deviceCode, tourismName, startTime, endTime);
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
        log.info("获取密度分布: deviceCode={}, tourismName={}, startTime={}, endTime={}",
                deviceCode, tourismName, startTime, endTime);
        validateTimeRange(startTime, endTime);
        return crowdStatisticsMapper.selectDensityDistribution(deviceCode, tourismName, startTime, endTime);
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
        log.info("获取人群趋势: deviceCode={}, tourismName={}, startTime={}, endTime={}",
                deviceCode, tourismName, startTime, endTime);
        validateTimeRange(startTime, endTime);
        return crowdStatisticsMapper.selectTrend(deviceCode, tourismName, startTime, endTime);
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
        log.info("获取统计概览: tourismName={}, startTime={}, endTime={}", tourismName, startTime, endTime);
        Assert.hasText(tourismName, "景区名称不能为空");
        validateTimeRange(startTime, endTime);
        return crowdStatisticsMapper.selectOverview(tourismName, startTime, endTime);
    }

    /**
     * 获取设备最新人群统计数据
     *
     * @param deviceCode 设备编码
     * @return 最新统计数据
     */
    @Override
    public CrowdStatistics getLatestByDevice(String deviceCode) {
        log.info("获取设备最新人群统计数据: deviceCode={}", deviceCode);
        Assert.hasText(deviceCode, "设备编码不能为空");
        return crowdStatisticsMapper.selectLatestByDevice(deviceCode);
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
    public List<Map<String, Object>> getHighDensityAreas(String deviceCode, String tourismName,
                                                        LocalDateTime startTime, LocalDateTime endTime,
                                                        Double densityThreshold) {
        log.info("获取高密度区域统计: deviceCode={}, tourismName={}, startTime={}, endTime={}, densityThreshold={}",
                deviceCode, tourismName, startTime, endTime, densityThreshold);
        validateTimeRange(startTime, endTime);
        Assert.notNull(densityThreshold, "密度阈值不能为空");
        Assert.isTrue(densityThreshold > 0 && densityThreshold <= 1.0, "密度阈值必须在0到1之间");
        return crowdStatisticsMapper.selectHighDensity(deviceCode, tourismName, startTime, endTime, densityThreshold);
    }

    /**
     * 验证时间范围的有效性
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    private void validateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        Assert.notNull(startTime, "开始时间不能为空");
        Assert.notNull(endTime, "结束时间不能为空");
        if (startTime.isAfter(endTime)) {
            throw new BusinessException("开始时间不能晚于结束时间");
        }
    }
}