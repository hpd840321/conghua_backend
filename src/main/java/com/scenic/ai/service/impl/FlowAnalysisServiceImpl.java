package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.common.core.domain.PageQuery;
import com.scenic.ai.common.core.domain.PageResult;
import com.scenic.ai.common.enums.ErrorCode;
import com.scenic.ai.common.exception.BusinessException;
import com.scenic.ai.common.exception.SystemException;
import com.scenic.ai.common.util.QueryUtils;
import com.scenic.ai.common.util.ValidationUtils;
import com.scenic.ai.entity.FlowAnalysis;
import com.scenic.ai.mapper.FlowAnalysisMapper;
import com.scenic.ai.service.IFlowAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客流分析服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlowAnalysisServiceImpl extends ServiceImpl<FlowAnalysisMapper, FlowAnalysis>
        implements IFlowAnalysisService {

    private static final Logger log = LoggerFactory.getLogger(FlowAnalysisServiceImpl.class);

    private final FlowAnalysisMapper flowAnalysisMapper;

    @Autowired
    public FlowAnalysisServiceImpl(FlowAnalysisMapper flowAnalysisMapper) {
        this.flowAnalysisMapper = flowAnalysisMapper;
    }

    @Override
    public List<FlowAnalysis> selectFlowAnalysisList(FlowAnalysis flowAnalysis) {
        try {
            // 构建查询条件
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();

            // 设置查询条件
            if (flowAnalysis != null) {
                if (StringUtils.hasText(flowAnalysis.getDeviceCode())) {
                    wrapper.eq(FlowAnalysis::getDeviceCode, flowAnalysis.getDeviceCode());
                }
                if (StringUtils.hasText(flowAnalysis.getTourismName())) {
                    wrapper.eq(FlowAnalysis::getTourismName, flowAnalysis.getTourismName());
                }
                if (StringUtils.hasText(flowAnalysis.getFlowDirection())) {
                    wrapper.eq(FlowAnalysis::getFlowDirection, flowAnalysis.getFlowDirection());
                }
                if (flowAnalysis.getRecordTime() != null) {
                    wrapper.eq(FlowAnalysis::getRecordTime, flowAnalysis.getRecordTime());
                }
            }

            // 按记录时间倒序排序
            wrapper.orderByDesc(FlowAnalysis::getRecordTime);

            // 执行查询
            return flowAnalysisMapper.selectList(wrapper);
        } catch (Exception e) {
            log.error("查询客流分析列表失败", e);
            throw new BusinessException("查询客流分析列表失败");
        }
    }

    @Override
    public FlowAnalysis selectFlowAnalysisById(Long id) {
        try {
            // 验证ID
            ValidationUtils.validateId(id);

            // 执行查询
            return flowAnalysisMapper.selectById(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询客流分析详情失败, id: {}", id, e);
            throw new BusinessException("查询客流分析详情失败");
        }
    }

    @Override
    public int insertFlowAnalysis(FlowAnalysis flowAnalysis) {
        try {
            // 验证流量分析对象
            validateFlowAnalysis(flowAnalysis);

            // 执行插入
            return flowAnalysisMapper.insert(flowAnalysis);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("插入流量分析数据失败", e);
            throw new BusinessException("插入流量分析数据失败");
        }
    }

    @Override
    public int updateFlowAnalysis(FlowAnalysis flowAnalysis) {
        try {
            // 验证ID
            ValidationUtils.validateId(flowAnalysis.getId());

            // 验证流量分析对象
            validateFlowAnalysis(flowAnalysis);

            // 执行更新
            return flowAnalysisMapper.updateById(flowAnalysis);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("更新流量分析数据失败, id: {}", flowAnalysis.getId(), e);
            throw new BusinessException("更新流量分析数据失败");
        }
    }

    @Override
    public int deleteFlowAnalysisById(Long id) {
        try {
            // 验证ID
            ValidationUtils.validateId(id);

            // 执行删除
            return flowAnalysisMapper.deleteById(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除流量分析数据失败, id: {}", id, e);
            throw new BusinessException("删除流量分析数据失败");
        }
    }

    @Override
    public int deleteFlowAnalysisByIds(Long[] ids) {
        try {
            // 验证ID数组
            if (ids == null || ids.length == 0) {
                throw new BusinessException("ID数组不能为空");
            }

            // 执行批量删除
            return flowAnalysisMapper.deleteBatchIds(java.util.Arrays.asList(ids));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("批量删除流量分析数据失败", e);
            throw new BusinessException("批量删除流量分析数据失败");
        }
    }

    @Override
    public Integer selectTotalFlowCount(Date startTime, Date endTime) {
        try {
            // 转换日期类型
            LocalDateTime startDateTime = convertToLocalDateTime(startTime);
            LocalDateTime endDateTime = convertToLocalDateTime(endTime);

            // 验证时间范围
            ValidationUtils.validateTimeRange(startDateTime, endDateTime);

            // 执行查询
            return flowAnalysisMapper.countTotal(null, null, startDateTime, endDateTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("统计总流量失败", e);
            throw new BusinessException("统计总流量失败");
        }
    }

    @Override
    public List<FlowAnalysis> selectFlowDirectionStats(Date startTime, Date endTime) {
        try {
            // 转换日期类型
            LocalDateTime startDateTime = convertToLocalDateTime(startTime);
            LocalDateTime endDateTime = convertToLocalDateTime(endTime);

            // 验证时间范围
            ValidationUtils.validateTimeRange(startDateTime, endDateTime);

            // 执行查询
            return flowAnalysisMapper.selectFlowDirectionStats(startDateTime, endDateTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("统计流量方向分布失败", e);
            throw new BusinessException("统计流量方向分布失败");
        }
    }

    @Override
    public PageResult<FlowAnalysis> selectFlowAnalysisPage(PageQuery pageQuery) {
        try {
            // 验证分页参数
            ValidationUtils.validatePage(pageQuery.getPageNum(), pageQuery.getPageSize());

            // 构建查询条件
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();

            // 设置查询条件
            if (pageQuery.getParams() != null) {
                Map<String, Object> params = pageQuery.getParams();
                if (params.containsKey("deviceCode") && params.get("deviceCode") != null) {
                    wrapper.eq(FlowAnalysis::getDeviceCode, params.get("deviceCode"));
                }
                if (params.containsKey("tourismName") && params.get("tourismName") != null) {
                    wrapper.eq(FlowAnalysis::getTourismName, params.get("tourismName"));
                }
                if (params.containsKey("flowDirection") && params.get("flowDirection") != null) {
                    wrapper.eq(FlowAnalysis::getFlowDirection, params.get("flowDirection"));
                }
            }

            // 按记录时间倒序排序
            wrapper.orderByDesc(FlowAnalysis::getRecordTime);

            // 执行分页查询
            Page<FlowAnalysis> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
            IPage<FlowAnalysis> pageResult = flowAnalysisMapper.selectPage(page, wrapper);

            // 转换为PageResult
            PageResult<FlowAnalysis> result = new PageResult<>();
            result.setTotal(pageResult.getTotal());
            result.setRows(pageResult.getRecords());

            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("分页查询流量分析失败", e);
            throw new BusinessException("分页查询流量分析失败");
        }
    }

    /**
     * 验证流量分析对象
     */
    private void validateFlowAnalysis(FlowAnalysis flowAnalysis) {
        if (flowAnalysis == null) {
            throw new BusinessException("流量分析对象不能为空");
        }

        // 验证设备编码
        if (flowAnalysis.getDeviceCode() != null) {
            ValidationUtils.validateDeviceCode(flowAnalysis.getDeviceCode());
        }

        // 验证景区名称
        if (flowAnalysis.getTourismName() != null) {
            ValidationUtils.validateTourismName(flowAnalysis.getTourismName());
        }

        // 验证流量数量
        if (flowAnalysis.getFlowCount() != null && flowAnalysis.getFlowCount() < 0) {
            throw new BusinessException("流量数量不能为负数");
        }
    }

    /**
     * 将Date转换为LocalDateTime
     */
    private LocalDateTime convertToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(FlowAnalysis flowAnalysis) {
        if (flowAnalysis == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "客流分析记录不能为空");
        }
        try {
            return flowAnalysisMapper.insert(flowAnalysis) > 0;
        } catch (Exception e) {
            log.error("保存客流分析记录失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "保存客流分析记录失败");
        }
    }

    @Override
    public FlowAnalysis getById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "客流分析记录ID不能为空");
        }
        try {
            return flowAnalysisMapper.selectById(id);
        } catch (Exception e) {
            log.error("查询客流分析记录失败, id: {}", id, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询客流分析记录失败");
        }
    }

    @Override
    public List<FlowAnalysis> listByDevice(String deviceCode) {
        if (!StringUtils.hasText(deviceCode)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编码不能为空");
        }
        try {
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FlowAnalysis::getDeviceCode, deviceCode)
                    .orderByDesc(FlowAnalysis::getRecordTime);
            return flowAnalysisMapper.selectList(wrapper);
        } catch (Exception e) {
            log.error("查询设备客流分析记录失败, deviceCode: {}", deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询设备客流分析记录失败");
        }
    }

    @Override
    public List<FlowAnalysis> listByTourism(String tourismName) {
        if (!StringUtils.hasText(tourismName)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "景区名称不能为空");
        }
        try {
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FlowAnalysis::getTourismName, tourismName)
                    .orderByDesc(FlowAnalysis::getRecordTime);
            return flowAnalysisMapper.selectList(wrapper);
        } catch (Exception e) {
            log.error("查询景区客流分析记录失败, tourismName: {}", tourismName, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询景区客流分析记录失败");
        }
    }

    @Override
    public List<FlowAnalysis> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
            wrapper.between(FlowAnalysis::getRecordTime, startTime, endTime)
                    .orderByDesc(FlowAnalysis::getRecordTime);
            return flowAnalysisMapper.selectList(wrapper);
        } catch (Exception e) {
            log.error("查询时间范围内客流分析记录失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询时间范围内客流分析记录失败");
        }
    }

    @Override
    public IPage<FlowAnalysis> page(Page<FlowAnalysis> page, String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime) {
        if (page == null || page.getCurrent() <= 0 || page.getSize() <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分页参数错误");
        }
        if (startTime != null && endTime != null) {
            validateTimeRange(startTime, endTime);
        }
        try {
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(deviceCode)) {
                wrapper.eq(FlowAnalysis::getDeviceCode, deviceCode);
            }
            if (StringUtils.hasText(tourismName)) {
                wrapper.eq(FlowAnalysis::getTourismName, tourismName);
            }
            if (startTime != null) {
                wrapper.ge(FlowAnalysis::getRecordTime, startTime);
            }
            if (endTime != null) {
                wrapper.le(FlowAnalysis::getRecordTime, endTime);
            }
            wrapper.orderByDesc(FlowAnalysis::getRecordTime);
            return flowAnalysisMapper.selectPage(page, wrapper);
        } catch (Exception e) {
            log.error("分页查询客流分析记录失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "分页查询客流分析记录失败");
        }
    }

    @Override
    public Integer getMaxFlowCount(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return flowAnalysisMapper.selectMaxFlowCount(startTime, endTime);
        } catch (Exception e) {
            log.error("获取最大客流量失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取最大客流量失败");
        }
    }

    @Override
    public Integer getMinFlowCount(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return flowAnalysisMapper.selectMinFlowCount(startTime, endTime);
        } catch (Exception e) {
            log.error("获取最小客流量失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取最小客流量失败");
        }
    }

    @Override
    public List<Map<String, Object>> getTourismDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return flowAnalysisMapper.selectTourismDistribution(startTime, endTime);
        } catch (Exception e) {
            log.error("获取景区分布失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取景区分布失败");
        }
    }

    @Override
    public List<Map<String, Object>> getDeviceDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return flowAnalysisMapper.selectDeviceDistribution(startTime, endTime);
        } catch (Exception e) {
            log.error("获取设备分布失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取设备分布失败");
        }
    }

    @Override
    public List<Map<String, Object>> getDirectionDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return flowAnalysisMapper.selectDirectionDistribution(startTime, endTime);
        } catch (Exception e) {
            log.error("获取方向分布失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取方向分布失败");
        }
    }

    @Override
    public List<Map<String, Object>> getTimeTrend(LocalDateTime startTime, LocalDateTime endTime, Integer interval) {
        validateTimeRange(startTime, endTime);
        if (interval == null || interval <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "时间间隔必须大于0");
        }
        try {
            return flowAnalysisMapper.selectTimeTrend(startTime, endTime, interval);
        } catch (Exception e) {
            log.error("获取时间趋势失败, startTime: {}, endTime: {}, interval: {}", startTime, endTime, interval, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取时间趋势失败");
        }
    }

    @Override
    public Map<String, Object> getOverview(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return flowAnalysisMapper.selectOverview(startTime, endTime);
        } catch (Exception e) {
            log.error("获取概览数据失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取概览数据失败");
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

    @Override
    public List<FlowAnalysis> listByConditions(String tourismName, String deviceCode, String algName,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            return flowAnalysisMapper.listByConditions(tourismName, deviceCode, algName, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询客流分析列表失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询客流分析列表失败");
        }
    }

    @Override
    public IPage<FlowAnalysis> pageByConditions(Page<FlowAnalysis> page,
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
            return flowAnalysisMapper.pageByConditions(page, deviceCode, tourismName, startTime, endTime);
        } catch (Exception e) {
            log.error("分页查询客流分析记录失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "分页查询客流分析记录失败");
        }
    }

    @Override
    public Integer countTotalFlow(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeRange(startTime, endTime);
        try {
            return flowAnalysisMapper.countTotalFlow(startTime, endTime);
        } catch (Exception e) {
            log.error("统计总客流量失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计总客流量失败");
        }
    }

    @Override
    public Integer countByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            return flowAnalysisMapper.countByTimeRange(startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("统计时间范围内客流量失败, startTime: {}, endTime: {}", startTime, endTime, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "统计时间范围内客流量失败");
        }
    }

    @Override
    public List<Map<String, Object>> getDirectionDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            return flowAnalysisMapper.getDirectionDistribution(tourismName, deviceCode, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取客流方向分布失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取客流方向分布失败");
        }
    }

    @Override
    public List<Map<String, Object>> getTimeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            return flowAnalysisMapper.getTimeDistribution(tourismName, deviceCode, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取客流时段分布失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取客流时段分布失败");
        }
    }

    @Override
    public FlowAnalysis getLatestByDevice(String deviceCode) {
        try {
            if (StringUtils.hasText(deviceCode)) {
                LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(FlowAnalysis::getDeviceCode, deviceCode)
                        .orderByDesc(FlowAnalysis::getRecordTime)
                        .last("FETCH FIRST 1 ROWS ONLY");
                return getOne(wrapper);
            }
            throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编号不能为空");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取设备最新客流分析失败, deviceCode: {}", deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取设备最新客流分析失败");
        }
    }

    @Override
    public Map<String, Object> getOverview(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            validateTimeRange(startTime, endTime);
            Map<String, Object> result = new HashMap<>();

            // 获取总客流量
            int totalCount = count(tourismName, deviceCode, null, startTime, endTime);
            result.put("totalCount", totalCount);

            // 获取最大客流量
            Integer maxFlowCount = getMaxFlowCount(startTime, endTime);
            result.put("maxFlowCount", maxFlowCount);

            // 获取最小客流量
            Integer minFlowCount = getMinFlowCount(startTime, endTime);
            result.put("minFlowCount", minFlowCount);

            // 获取方向分布
            List<Map<String, Object>> directionDistribution = getDirectionDistribution(tourismName, deviceCode,
                    startTime,
                    endTime);
            result.put("directionDistribution", directionDistribution);

            // 获取时段分布
            List<Map<String, Object>> timeDistribution = getTimeDistribution(tourismName, deviceCode, startTime,
                    endTime);
            result.put("timeDistribution", timeDistribution);

            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取客流分析概览失败, tourismName: {}, deviceCode: {}", tourismName, deviceCode, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取客流分析概览失败");
        }
    }
}