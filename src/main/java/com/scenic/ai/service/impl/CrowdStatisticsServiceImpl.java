package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.common.exception.BusinessException;
import com.scenic.ai.common.enums.ErrorCode;
import com.scenic.ai.mapper.CrowdStatisticsMapper;
import com.scenic.ai.model.CrowdStatistics;
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
    public boolean save(CrowdStatistics statistics) {
        if (statistics == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "统计记录不能为空");
        }
        try {
            return crowdStatisticsMapper.insert(statistics) > 0;
        } catch (Exception e) {
            log.error("保存人群统计记录失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "保存人群统计记录失败");
        }
    }

    @Override
    public CrowdStatistics getById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "统计记录ID不能为空");
        }
        try {
            return crowdStatisticsMapper.getById(id);
        } catch (Exception e) {
            log.error("查询人群统计记录失败, id: {}", id, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询人群统计记录失败");
        }
    }

    @Override
    public List<CrowdStatistics> listByDevice(String deviceCode) {
        if (!StringUtils.hasText(deviceCode)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编号不能为空");
        }
        try {
            return crowdStatisticsMapper.listByDevice(deviceCode);
        } catch (Exception e) {
            log.error("查询设备人群统计记录失败, deviceCode: {}", deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询设备人群统计记录失败");
        }
    }

    @Override
    public List<CrowdStatistics> listByTourism(String tourismName) {
        if (!StringUtils.hasText(tourismName)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "景区名称不能为空");
        }
        try {
            return crowdStatisticsMapper.listByTourism(tourismName);
        } catch (Exception e) {
            log.error("查询景区人群统计记录失败, tourismName: {}", tourismName, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询景区人群统计记录失败");
        }
    }

    @Override
    public List<CrowdStatistics> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return crowdStatisticsMapper.listByTimeRange(startTime, endTime);
        } catch (Exception e) {
            log.error("查询时间范围内人群统计记录失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询时间范围内人群统计记录失败");
        }
    }

    @Override
    public IPage<CrowdStatistics> pageByConditions(Page<CrowdStatistics> page,
            String deviceCode,
            String tourismName,
            LocalDateTime startTime,
            LocalDateTime endTime) {
        if (page == null || page.getCurrent() <= 0 || page.getSize() <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分页参数错误");
        }
        if (startTime != null && endTime != null) {
            validateTimeRange(startTime, endTime);
        }
        try {
            return crowdStatisticsMapper.pageByConditions(page, deviceCode, tourismName, startTime, endTime);
        } catch (Exception e) {
            log.error("分页查询人群统计记录失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "分页查询人群统计记录失败");
        }
    }

    @Override
    public Integer countTotalCrowd(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return crowdStatisticsMapper.countTotalCrowd(startTime, endTime);
        } catch (Exception e) {
            log.error("统计总人数失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计总人数失败");
        }
    }

    @Override
    public Double getAverageDensity(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return crowdStatisticsMapper.getAverageDensity(startTime, endTime);
        } catch (Exception e) {
            log.error("计算平均密度失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "计算平均密度失败");
        }
    }

    @Override
    public Integer getMaxCrowdCount(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return crowdStatisticsMapper.getMaxCrowdCount(startTime, endTime);
        } catch (Exception e) {
            log.error("获取最大人数失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取最大人数失败");
        }
    }

    @Override
    public Integer getMinCrowdCount(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return crowdStatisticsMapper.getMinCrowdCount(startTime, endTime);
        } catch (Exception e) {
            log.error("获取最小人数失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取最小人数失败");
        }
    }

    @Override
    public List<Map<String, Object>> getTourismDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return crowdStatisticsMapper.getTourismDistribution(startTime, endTime);
        } catch (Exception e) {
            log.error("获取景区人数分布失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取景区人数分布失败");
        }
    }

    @Override
    public List<Map<String, Object>> getDeviceDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return crowdStatisticsMapper.getDeviceDistribution(startTime, endTime);
        } catch (Exception e) {
            log.error("获取设备人数分布失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取设备人数分布失败");
        }
    }

    @Override
    public List<Map<String, Object>> getTimeTrend(LocalDateTime startTime, LocalDateTime endTime, Integer interval) {
        validateTimeRange(startTime, endTime);
        if (interval == null || interval <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "时间间隔必须大于0");
        }
        try {
            return crowdStatisticsMapper.getTimeTrend(startTime, endTime, interval);
        } catch (Exception e) {
            log.error("获取时间趋势数据失败, startTime: {}, endTime: {}, interval: {}", startTime, endTime, interval, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取时间趋势数据失败");
        }
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