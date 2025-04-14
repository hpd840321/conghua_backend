package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.FlowAnalysis;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 客流分析服务接口
 */
public interface FlowAnalysisService extends IService<FlowAnalysis> {
    
    /**
     * 分页查询客流分析数据
     * @param page 分页参数
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param algName 算法类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    IPage<FlowAnalysis> pageFlowAnalysis(IPage<FlowAnalysis> page, String tourismName,
                                        String deviceCode, String algName,
                                        LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取客流量趋势数据
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 时间-数量映射列表
     */
    List<Map<String, Object>> getFlowTrend(String deviceCode, 
                                         String tourismName,
                                         LocalDateTime startTime, 
                                         LocalDateTime endTime);

    /**
     * 获取指定时间范围内的客流方向分布
     * @param deviceCode 设备编码
     * @param tourismName 景区名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 方向-数量映射
     */
    List<Map<String, Object>> getFlowDirectionDistribution(String deviceCode, String tourismName,
                                                         LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取指定时间范围内的客流高峰时段
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 时段-客流量映射
     */
    List<Map<String, Object>> getPeakHours(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计指定时间范围内的总客流量
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 总客流量
     */
    int getTotalFlowCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取客流量最高的记录
     * @param deviceCode 设备编码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分析记录
     */
    FlowAnalysis getPeakFlowRecord(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);
} 