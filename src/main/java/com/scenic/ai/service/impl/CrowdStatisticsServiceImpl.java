package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.common.exception.BusinessException;
import com.scenic.ai.common.enums.ErrorCode;
import com.scenic.ai.mapper.CrowdStatisticsMapper;
import com.scenic.ai.entity.CrowdStatistics;
import com.scenic.ai.service.ICrowdStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 人群统计服务实现类
 * 
 * @author AI
 * @date 2024-04-15
 */
@Service
public class CrowdStatisticsServiceImpl extends ServiceImpl<CrowdStatisticsMapper, CrowdStatistics>
        implements ICrowdStatisticsService {

    private static final Logger log = LoggerFactory.getLogger(CrowdStatisticsServiceImpl.class);

    @Autowired
    private CrowdStatisticsMapper crowdStatisticsMapper;

    @Override
    public boolean save(CrowdStatistics crowdStatistics) {
        if (crowdStatistics == null) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        if (crowdStatistics.getDeviceCode() == null || crowdStatistics.getDeviceCode().isEmpty()) {
            throw new BusinessException(ErrorCode.DEVICE_CODE_EMPTY);
        }
        if (crowdStatistics.getDeviceName() == null || crowdStatistics.getDeviceName().isEmpty()) {
            throw new BusinessException(ErrorCode.DEVICE_NAME_EMPTY);
        }
        if (crowdStatistics.getTourismName() == null || crowdStatistics.getTourismName().isEmpty()) {
            throw new BusinessException(ErrorCode.TOURISM_NAME_EMPTY);
        }
        if (crowdStatistics.getCount() == null) {
            throw new BusinessException(ErrorCode.CROWD_COUNT_EMPTY);
        }
        if (crowdStatistics.getDensity() == null) {
            throw new BusinessException(ErrorCode.CROWD_DENSITY_EMPTY);
        }
        if (crowdStatistics.getRecordTime() == null) {
            throw new BusinessException(ErrorCode.RECORD_TIME_EMPTY);
        }
        return baseMapper.insert(crowdStatistics) > 0;
    }

    @Override
    public CrowdStatistics getById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        CrowdStatistics crowdStatistics = baseMapper.selectById(id);
        if (crowdStatistics == null) {
            throw new BusinessException(ErrorCode.CROWD_STATISTICS_NOT_FOUND);
        }
        return crowdStatistics;
    }

    @Override
    public List<CrowdStatistics> listByDevice(String deviceCode) {
        if (deviceCode == null || deviceCode.isEmpty()) {
            throw new BusinessException(ErrorCode.DEVICE_CODE_EMPTY);
        }
        return baseMapper.listByDevice(deviceCode);
    }

    @Override
    public List<CrowdStatistics> listByTourism(String tourismName) {
        if (tourismName == null || tourismName.isEmpty()) {
            throw new BusinessException(ErrorCode.TOURISM_NAME_EMPTY);
        }
        return baseMapper.listByTourism(tourismName);
    }

    @Override
    public List<CrowdStatistics> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.START_TIME_AFTER_END_TIME);
        }
        return baseMapper.listByTimeRange(startTime, endTime);
    }

    @Override
    public IPage<CrowdStatistics> pageByConditions(Page<CrowdStatistics> page,
            String deviceCode,
            String tourismName,
            LocalDateTime startTime,
            LocalDateTime endTime) {
        if (page == null) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        if (startTime != null && endTime != null && startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.START_TIME_AFTER_END_TIME);
        }
        return baseMapper.pageByConditions(page, deviceCode, tourismName, startTime, endTime);
    }

    @Override
    public Integer countTotalCrowd(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.START_TIME_AFTER_END_TIME);
        }
        return baseMapper.countTotalCrowd(startTime, endTime);
    }

    @Override
    public Double getAverageDensity(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.START_TIME_AFTER_END_TIME);
        }
        return baseMapper.getAverageDensity(startTime, endTime);
    }

    @Override
    public Integer getMaxCrowdCount(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.START_TIME_AFTER_END_TIME);
        }
        return baseMapper.getMaxCrowdCount(startTime, endTime);
    }

    @Override
    public Integer getMinCrowdCount(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.START_TIME_AFTER_END_TIME);
        }
        return baseMapper.getMinCrowdCount(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getTourismDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.START_TIME_AFTER_END_TIME);
        }
        return baseMapper.getTourismDistribution(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getDeviceDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.START_TIME_AFTER_END_TIME);
        }
        return baseMapper.getDeviceDistribution(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getTimeTrend(LocalDateTime startTime, LocalDateTime endTime, Integer interval) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_INVALID);
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.START_TIME_AFTER_END_TIME);
        }
        if (interval == null || interval <= 0) {
            throw new BusinessException(ErrorCode.PARAM_INVALID, "时间间隔必须大于0");
        }
        return baseMapper.getTimeTrend(startTime, endTime, interval);
    }

    @Override
    public List<CrowdStatistics> listByConditions(String tourismName, String deviceCode, String algName,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            return crowdStatisticsMapper.listByConditions(tourismName, deviceCode, algName, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询人群统计列表失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询人群统计列表失败");
        }
    }

    @Override
    public IPage<CrowdStatistics> page(Integer pageNum, Integer pageSize, String tourismName, String deviceCode,
            String algName, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (pageNum == null || pageSize == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "分页参数不能为空");
            }
            validateTimeRange(startTime, endTime);

            Page<CrowdStatistics> page = new Page<>(pageNum, pageSize);
            QueryWrapper<CrowdStatistics> wrapper = buildQueryWrapper(tourismName, deviceCode, algName, startTime,
                    endTime);
            return page(page, wrapper);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("分页查询人群统计失败, pageNum: {}, pageSize: {}", pageNum, pageSize, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "分页查询人群统计失败");
        }
    }

    @Override
    public int count(String tourismName, String deviceCode, String algName,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            QueryWrapper<CrowdStatistics> wrapper = buildQueryWrapper(tourismName, deviceCode, algName, startTime,
                    endTime);
            return count(wrapper);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("统计人群数量失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计人群数量失败");
        }
    }

    @Override
    public int countByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            return crowdStatisticsMapper.countByTimeRange(startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("统计时间范围内人群数量失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计时间范围内人群数量失败");
        }
    }

    @Override
    public List<Map<String, Object>> getDensityDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            return crowdStatisticsMapper.getDensityDistribution(tourismName, deviceCode, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取人群密度分布失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取人群密度分布失败");
        }
    }

    @Override
    public List<Map<String, Object>> getCountDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            return crowdStatisticsMapper.getCountDistribution(tourismName, deviceCode, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取人群数量分布失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取人群数量分布失败");
        }
    }

    @Override
    public List<Map<String, Object>> getTimeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            return crowdStatisticsMapper.getTimeDistribution(tourismName, deviceCode, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取人群时段分布失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取人群时段分布失败");
        }
    }

    @Override
    public CrowdStatistics getLatestByDevice(String deviceCode) {
        try {
            if (StringUtils.hasText(deviceCode)) {
                LambdaQueryWrapper<CrowdStatistics> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CrowdStatistics::getDeviceCode, deviceCode)
                        .orderByDesc(CrowdStatistics::getRecordTime)
                        .last("FETCH FIRST 1 ROWS ONLY");
                return getOne(wrapper);
            }
            throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编号不能为空");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取设备最新人群统计失败, deviceCode: {}", deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取设备最新人群统计失败");
        }
    }

    @Override
    public Map<String, Object> getOverview(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            Map<String, Object> result = new HashMap<>();

            // 获取总人数
            int totalCount = count(tourismName, deviceCode, null, startTime, endTime);
            result.put("totalCount", totalCount);

            // 获取平均密度
            Double avgDensity = crowdStatisticsMapper.getAverageDensity(tourismName, deviceCode, startTime, endTime);
            result.put("avgDensity", avgDensity);

            // 获取最大密度
            Double maxDensity = crowdStatisticsMapper.getMaxDensity(tourismName, deviceCode, startTime, endTime);
            result.put("maxDensity", maxDensity);

            // 获取时段分布
            List<Map<String, Object>> timeDistribution = getTimeDistribution(tourismName, deviceCode, startTime,
                    endTime);
            result.put("timeDistribution", timeDistribution);

            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取人群统计概览失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取人群统计概览失败");
        }
    }

    /**
     * 验证时间范围
     */
    private void validateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
        }
        if (startTime.isAfter(endTime)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
        }
    }

    /**
     * 构建查询条件
     */
    private QueryWrapper<CrowdStatistics> buildQueryWrapper(String tourismName, String deviceCode, String algName,
            LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper<CrowdStatistics> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq("TOURISM_NAME", tourismName);
        }
        if (StringUtils.hasText(deviceCode)) {
            wrapper.eq("DEVICE_CODE", deviceCode);
        }
        if (StringUtils.hasText(algName)) {
            wrapper.eq("ALG_NAME", algName);
        }
        if (startTime != null) {
            wrapper.ge("RECORD_TIME", startTime);
        }
        if (endTime != null) {
            wrapper.le("RECORD_TIME", endTime);
        }
        wrapper.orderByDesc("RECORD_TIME");
        return wrapper;
    }
}