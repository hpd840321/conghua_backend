package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.CrowdStatisticsMapper;
import com.scenic.ai.model.CrowdStatistics;
import com.scenic.ai.service.CrowdStatisticsService;
import com.scenic.ai.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    @Override
    public IPage<CrowdStatistics> page(Integer pageNum, Integer pageSize, String tourismName,
                                      String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询人群统计数据: pageNum={}, pageSize={}, tourismName={}, deviceCode={}, startTime={}, endTime={}",
                pageNum, pageSize, tourismName, deviceCode, startTime, endTime);
        
        // 参数校验
        Assert.notNull(pageNum, "页码不能为空");
        Assert.notNull(pageSize, "每页大小不能为空");
        Assert.isTrue(pageNum > 0, "页码必须大于0");
        Assert.isTrue(pageSize > 0, "每页大小必须大于0");
                
        Page<CrowdStatistics> page = new Page<>(pageNum, pageSize);
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
            IPage<CrowdStatistics> result = page(page, wrapper);
            log.info("查询成功, 总记录数: {}, 总页数: {}", result.getTotal(), result.getPages());
            return result;
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
        return crowdStatisticsMapper.selectByDeviceCode(deviceCode, startTime, endTime);
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
        return crowdStatisticsMapper.selectByTimeRangeAndTourism(tourismName, startTime, endTime);
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
        return crowdStatisticsMapper.getHourDistribution(deviceCode, tourismName, startTime, endTime);
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
        return crowdStatisticsMapper.getDensityDistribution(deviceCode, tourismName, startTime, endTime);
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
        return crowdStatisticsMapper.getTrend(deviceCode, tourismName, startTime, endTime);
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
        return crowdStatisticsMapper.getOverview(tourismName, startTime, endTime);
    }

    /**
     * 获取设备最新人群统计数据
     *
     * @param deviceCode 设备编码
     * @return 最新统计数据
     */
    @Override
    public CrowdStatistics getLatest(String deviceCode) {
        log.info("获取设备最新人群统计数据: deviceCode={}", deviceCode);
        Assert.hasText(deviceCode, "设备编码不能为空");
        return crowdStatisticsMapper.selectLatestByDeviceCode(deviceCode);
    }

    /**
     * 获取高密度区域统计
     *
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param threshold 密度阈值
     * @return 高密度区域统计数据
     */
    @Override
    public List<Map<String, Object>> getHighDensityAreas(String deviceCode, String tourismName,
                                                        LocalDateTime startTime, LocalDateTime endTime,
                                                        BigDecimal threshold) {
        log.info("获取高密度区域统计: deviceCode={}, tourismName={}, startTime={}, endTime={}, threshold={}",
                deviceCode, tourismName, startTime, endTime, threshold);
        validateTimeRange(startTime, endTime);
        Assert.notNull(threshold, "密度阈值不能为空");
        Assert.isTrue(threshold.compareTo(BigDecimal.ZERO) > 0 && threshold.compareTo(BigDecimal.ONE) <= 0,
                "密度阈值必须在0到1之间");
        return crowdStatisticsMapper.getHighDensityAreas(deviceCode, tourismName, startTime, endTime, threshold);
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