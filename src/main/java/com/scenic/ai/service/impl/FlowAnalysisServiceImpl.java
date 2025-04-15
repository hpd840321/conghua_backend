package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.common.core.domain.PageQuery;
import com.scenic.ai.common.core.domain.PageResult;
import com.scenic.ai.exception.BusinessException;
import com.scenic.ai.exception.ErrorCode;
import com.scenic.ai.exception.SystemException;
import com.scenic.ai.mapper.FlowAnalysisMapper;
import com.scenic.ai.model.FlowAnalysis;
import com.scenic.ai.service.FlowAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客流分析服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlowAnalysisServiceImpl extends ServiceImpl<FlowAnalysisMapper, FlowAnalysis> implements FlowAnalysisService {

    private static final Logger log = LoggerFactory.getLogger(FlowAnalysisServiceImpl.class);

    private static final String FIELD_TOURISM_NAME = "tourism_name";
    private static final String FIELD_DEVICE_CODE = "device_code";
    private static final String FIELD_FLOW_DIRECTION = "flow_direction";
    private static final String FIELD_RECORD_TIME = "record_time";
    private static final String FIELD_FLOW_COUNT = "flow_count";
    private static final String FIELD_CREATE_TIME = "create_time";
    private static final String FIELD_UPDATE_TIME = "update_time";
    private static final String FIELD_ALG_NAME = "alg_name";
    private static final String FIELD_TASK_CODE = "task_code";
    private static final String FIELD_IMAGE_URL = "image_url";
    private static final String FIELD_DEVICE_NAME = "device_name";

    private final FlowAnalysisMapper flowAnalysisMapper;

    public FlowAnalysisServiceImpl(FlowAnalysisMapper flowAnalysisMapper) {
        this.flowAnalysisMapper = flowAnalysisMapper;
    }

    @Override
    public IPage<FlowAnalysis> pageFlowAnalysis(Page<FlowAnalysis> page, String tourismName, String deviceCode,
            String flowDirection, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
            
            if (StringUtils.hasText(tourismName)) {
                wrapper.eq(FlowAnalysis::getTourismName, tourismName);
            }
            if (StringUtils.hasText(deviceCode)) {
                wrapper.eq(FlowAnalysis::getDeviceCode, deviceCode);
            }
            if (StringUtils.hasText(flowDirection)) {
                wrapper.eq(FlowAnalysis::getFlowDirection, flowDirection);
            }
            if (startTime != null) {
                wrapper.ge(FlowAnalysis::getRecordTime, startTime);
            }
            if (endTime != null) {
                wrapper.le(FlowAnalysis::getRecordTime, endTime);
            }
            
            wrapper.orderByDesc(FlowAnalysis::getRecordTime);
            return page(page, wrapper);
        } catch (Exception e) {
            log.error("分页查询客流分析数据失败", e);
            throw new BusinessException(ErrorCode.QUERY_ERROR, "分页查询客流分析数据失败");
        }
    }

    @Override
    public List<FlowAnalysis> getByDeviceCode(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (!StringUtils.hasText(deviceCode)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编码不能为空");
            }
            return flowAnalysisMapper.selectByDeviceCode(deviceCode, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("根据设备编码查询客流数据失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "根据设备编码查询客流数据失败", e);
        }
    }

    @Override
    public List<FlowAnalysis> getByTourismName(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (!StringUtils.hasText(tourismName)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "景区名称不能为空");
            }
            return flowAnalysisMapper.selectByTourismName(tourismName, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("根据景区名称查询客流数据失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "根据景区名称查询客流数据失败", e);
        }
    }

    @Override
    public List<Map<String, Object>> getHourlyDistribution(String deviceCode, String tourismName,
                                                         LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            return flowAnalysisMapper.countByHour(deviceCode, tourismName, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取时段客流分布失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "获取时段客流分布失败", e);
        }
    }

    @Override
    public List<Map<String, Object>> getFlowDirectionDistribution(String deviceCode, String tourismName,
                                                            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            return flowAnalysisMapper.countByDirection(deviceCode, tourismName, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取客流方向分布失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "获取客流方向分布失败", e);
        }
    }

    @Override
    public List<Map<String, Object>> getFlowDirectionDistributionV2(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            return baseMapper.countByDirection(deviceCode, tourismName, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取客流方向分布失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "获取客流方向分布失败", e);
        }
    }

    @Override
    public List<Map<String, Object>> getFlowTrend(String deviceCode, String tourismName,
                                                 LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            return flowAnalysisMapper.getFlowTrend(deviceCode, tourismName, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取客流趋势失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "获取客流趋势失败", e);
        }
    }

    @Override
    public List<Map<String, Object>> getPeakHours(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            return flowAnalysisMapper.getPeakHours(deviceCode, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取高峰时段失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "获取高峰时段失败", e);
        }
    }

    @Override
    public List<FlowAnalysis> findByConditions(Map<String, Object> params) {
        try {
            if (params == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "查询参数不能为空");
            }
            return flowAnalysisMapper.findByConditions(params);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("根据条件查询客流分析数据失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "根据条件查询客流分析数据失败", e);
        }
    }

    @Override
    public Long countRecords(Map<String, Object> params) {
        try {
            if (params == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "查询参数不能为空");
            }
            return flowAnalysisMapper.countRecords(params);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("统计客流分析记录数失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "统计客流分析记录数失败", e);
        }
    }

    @Override
    public List<FlowAnalysis> getFlowByTimeRangeAndTourism(LocalDateTime startTime, LocalDateTime endTime, String tourismName) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            if (!StringUtils.hasText(tourismName)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "景区名称不能为空");
            }

            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FlowAnalysis::getTourismName, tourismName)
                  .ge(FlowAnalysis::getRecordTime, startTime)
                  .le(FlowAnalysis::getRecordTime, endTime)
                  .orderByDesc(FlowAnalysis::getRecordTime);

            return list(wrapper);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("根据时间范围和景区名称查询客流数据失败", e);
            throw new BusinessException(ErrorCode.QUERY_ERROR, "根据时间范围和景区名称查询客流数据失败");
        }
    }

    @Override
    public int getTotalFlowCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            return this.baseMapper.getTotalFlowCount(deviceCode, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取总客流量失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "获取总客流量失败", e);
        }
    }

    @Override
    public FlowAnalysis getPeakFlowRecord(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FlowAnalysis::getDeviceCode, deviceCode)
                   .ge(FlowAnalysis::getRecordTime, startTime)
                   .le(FlowAnalysis::getRecordTime, endTime)
                   .orderByDesc(FlowAnalysis::getFlowCount)
                   .last("LIMIT 1");
            return this.getOne(wrapper);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取高峰客流记录失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "获取高峰客流记录失败", e);
        }
    }

    @Override
    public List<FlowAnalysis> getFlowByDevice(String deviceCode, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (!StringUtils.hasText(deviceCode)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编码不能为空");
            }
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FlowAnalysis::getDeviceCode, deviceCode)
                  .ge(FlowAnalysis::getRecordTime, startTime)
                  .le(FlowAnalysis::getRecordTime, endTime)
                  .orderByDesc(FlowAnalysis::getRecordTime);
            return list(wrapper);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("根据设备编码查询客流数据失败", e);
            throw new BusinessException(ErrorCode.QUERY_ERROR, "根据设备编码查询客流数据失败");
        }
    }

    @Override
    public List<FlowAnalysis> getFlowByTourism(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (!StringUtils.hasText(tourismName)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "景区名称不能为空");
            }
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FlowAnalysis::getTourismName, tourismName)
                  .ge(FlowAnalysis::getRecordTime, startTime)
                  .le(FlowAnalysis::getRecordTime, endTime)
                  .orderByDesc(FlowAnalysis::getRecordTime);
            return list(wrapper);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("根据景区名称查询客流数据失败", e);
            throw new BusinessException(ErrorCode.QUERY_ERROR, "根据景区名称查询客流数据失败");
        }
    }

    @Override
    public List<Map<String, Object>> getFlowHourDistribution(String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            return baseMapper.countByHour(deviceCode, tourismName, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取时段客流分布失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "获取时段客流分布失败", e);
        }
    }

    @Override
    public FlowAnalysis getLatestFlowByDevice(String deviceCode) {
        try {
            if (!StringUtils.hasText(deviceCode)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编码不能为空");
            }
            return baseMapper.selectLatestByDeviceCode(deviceCode);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取设备最新客流数据失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "获取设备最新客流数据失败", e);
        }
    }

    @Override
    public Map<String, Object> getFlowStatistics(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            LambdaQueryWrapper<FlowAnalysis> wrapper = buildBaseWrapper(tourismName, startTime, endTime);
            List<FlowAnalysis> flowList = list(wrapper);
            
            Map<String, Object> result = new HashMap<>();
            
            // 总客流量
            int totalFlow = flowList.stream()
                    .mapToInt(flow -> flow.getFlowCount())
                    .sum();
            result.put("totalFlow", totalFlow);
            
            // 平均客流量
            double avgFlow = flowList.isEmpty() ? 0 : 
                    flowList.stream()
                            .mapToInt(flow -> flow.getFlowCount())
                            .average()
                            .orElse(0);
            result.put("avgFlow", avgFlow);
            
            // 最高客流量
            int maxFlow = flowList.stream()
                    .mapToInt(flow -> flow.getFlowCount())
                    .max()
                    .orElse(0);
            result.put("maxFlow", maxFlow);
            
            // 方向分布
            Map<String, Long> directionDistribution = flowList.stream()
                    .collect(Collectors.groupingBy(
                            flow -> flow.getFlowDirection(),
                            Collectors.counting()
                    ));
            result.put("directionDistribution", directionDistribution);
            
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取客流统计概览失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "获取客流统计概览失败", e);
        }
    }

    private LambdaQueryWrapper<FlowAnalysis> buildBaseWrapper(String tourismName, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(tourismName)) {
            wrapper.eq(FlowAnalysis::getTourismName, tourismName);
        }
        if (startTime != null) {
            wrapper.ge(FlowAnalysis::getRecordTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(FlowAnalysis::getRecordTime, endTime);
        }
        return wrapper;
    }

    /**
     * 查询客流分析列表
     * 
     * @param flowAnalysis 查询条件
     * @return 客流分析列表
     */
    @Override
    public List<FlowAnalysis> selectFlowAnalysisList(FlowAnalysis flowAnalysis) {
        try {
            if (flowAnalysis == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "查询条件不能为空");
            }
            
            String deviceCode = flowAnalysis.getDeviceCode();
            String tourismName = flowAnalysis.getTourismName();
            Date startTime = flowAnalysis.getStartTime() != null ? 
                    Date.from(flowAnalysis.getStartTime().atZone(java.time.ZoneId.systemDefault()).toInstant()) : null;
            Date endTime = flowAnalysis.getEndTime() != null ? 
                    Date.from(flowAnalysis.getEndTime().atZone(java.time.ZoneId.systemDefault()).toInstant()) : null;
            
            return flowAnalysisMapper.selectFlowAnalysisList(deviceCode, tourismName, startTime, endTime);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询客流分析列表失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "查询客流分析列表失败", e);
        }
    }

    /**
     * 查询客流分析详情
     * 
     * @param id 客流分析ID
     * @return 客流分析详情
     */
    @Override
    public FlowAnalysis selectFlowAnalysisById(Long id) {
        try {
            if (id == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "ID不能为空");
            }
            return flowAnalysisMapper.selectFlowAnalysisById(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询客流分析详情失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "查询客流分析详情失败", e);
        }
    }

    /**
     * 新增客流分析
     * 
     * @param flowAnalysis 客流分析信息
     * @return 结果
     */
    @Override
    public int insertFlowAnalysis(FlowAnalysis flowAnalysis) {
        try {
            if (flowAnalysis == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "客流分析信息不能为空");
            }
            if (!StringUtils.hasText(flowAnalysis.getDeviceCode())) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "设备编码不能为空");
            }
            return flowAnalysisMapper.insertFlowAnalysis(flowAnalysis);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("新增客流分析失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "新增客流分析失败", e);
        }
    }

    /**
     * 修改客流分析
     * 
     * @param flowAnalysis 客流分析信息
     * @return 结果
     */
    @Override
    public int updateFlowAnalysis(FlowAnalysis flowAnalysis) {
        try {
            if (flowAnalysis == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "客流分析信息不能为空");
            }
            if (flowAnalysis.getId() == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "ID不能为空");
            }
            return flowAnalysisMapper.updateFlowAnalysis(flowAnalysis);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("修改客流分析失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "修改客流分析失败", e);
        }
    }

    /**
     * 删除客流分析
     * 
     * @param id 客流分析ID
     * @return 结果
     */
    @Override
    public int deleteFlowAnalysisById(Long id) {
        try {
            if (id == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "ID不能为空");
            }
            return flowAnalysisMapper.deleteFlowAnalysisById(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除客流分析失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "删除客流分析失败", e);
        }
    }

    /**
     * 批量删除客流分析
     * 
     * @param ids 需要删除的客流分析ID数组
     * @return 结果
     */
    @Override
    public int deleteFlowAnalysisByIds(Long[] ids) {
        try {
            if (ids == null || ids.length == 0) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "ID数组不能为空");
            }
            return flowAnalysisMapper.deleteFlowAnalysisByIds(ids);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("批量删除客流分析失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "批量删除客流分析失败", e);
        }
    }

    /**
     * 统计指定时间范围内的总客流量
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 总客流量
     */
    @Override
    public Integer selectTotalFlowCount(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            
            Date startDate = Date.from(startTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
            
            return flowAnalysisMapper.selectTotalFlowCount(startDate, endDate);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("统计总客流量失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "统计总客流量失败", e);
        }
    }

    /**
     * 统计指定时间范围内的各方向客流数量
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 各方向客流数量统计
     */
    @Override
    public List<FlowAnalysis> selectFlowDirectionStats(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (startTime == null || endTime == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间和结束时间不能为空");
            }
            if (startTime.isAfter(endTime)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "开始时间不能晚于结束时间");
            }
            
            Date startDate = Date.from(startTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
            
            return flowAnalysisMapper.selectFlowDirectionStats(startDate, endDate);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("统计各方向客流数量失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "统计各方向客流数量失败", e);
        }
    }
    
    /**
     * 分页查询客流分析数据
     * 
     * @param page 分页参数
     * @param flowAnalysis 查询条件
     * @return 分页结果
     */
    @Override
    public IPage<FlowAnalysis> selectFlowAnalysisPage(Page<FlowAnalysis> page, FlowAnalysis flowAnalysis) {
        try {
            if (page == null) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "分页参数不能为空");
            }
            
            LambdaQueryWrapper<FlowAnalysis> wrapper = new LambdaQueryWrapper<>();
            
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
            
            wrapper.orderByDesc(FlowAnalysis::getRecordTime);
            
            return page(page, wrapper);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("分页查询客流分析数据失败", e);
            throw new SystemException(ErrorCode.DATABASE_ERROR, "分页查询客流分析数据失败", e);
        }
    }
} 