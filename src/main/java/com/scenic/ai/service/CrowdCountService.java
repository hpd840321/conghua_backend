package com.scenic.ai.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.domain.model.AlertDomain;
import com.scenic.ai.domain.model.CrowdCount;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 人群计数服务接口
 */
public interface CrowdCountService {
    /**
     * 分页查询人群统计数据
     *
     * @param page 分页参数
     * @param tourismName 景区名称
     * @param deviceCode 设备编码
     * @param algName 算法类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    Page<CrowdCount> getPage(Page<CrowdCount> page, String tourismName, String deviceCode,
                            String algName, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取详情分页数据
     *
     * @param page 分页参数
     * @param deviceCode 设备编码
     * @param algName 算法类型
     * @param recordBeginDate 记录开始时间
     * @param recordEndDate 记录结束时间
     * @return 分页结果
     */
    Page<CrowdCount> getDetailsPage(Page<CrowdCount> page, String deviceCode, String algName,
                                  LocalDateTime recordBeginDate, LocalDateTime recordEndDate);
    
    /**
     * 根据时间范围查询人群计数记录
     */
    List<CrowdCount> findByTimeRange(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 计算指定时间范围内的平均人数
     */
    Double calculateAverageCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查询指定时间范围内的最大人数
     */
    Integer findMaxCount(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 计算区域密度
     */
    Double calculateDensity(String deviceCode, LocalDateTime time);
    
    /**
     * 分析人群趋势
     */
    List<CrowdCount> analyzeTrend(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取实时计数
     */
    CrowdCount getRealTimeCount(String deviceCode);
    
    /**
     * 设置告警阈值
     */
    void setThreshold(String deviceCode, int threshold);
    
    /**
     * 获取告警阈值
     */
    Integer getThreshold(String deviceCode);

    /**
     * 保存人群计数记录
     */
    void save(CrowdCount crowdCount);

    /**
     * 批量保存人群计数记录
     */
    int batchSave(List<CrowdCount> crowdCounts);

    /**
     * 删除历史数据
     */
    int deleteHistoricalData(LocalDateTime time);

    /**
     * 查找超过阈值的计数记录
     */
    List<AlertDomain> findExceedThresholdCounts();

    /**
     * 查找高密度区域
     */
    List<CrowdCount> findHighDensityAreas(String deviceCode, int threshold,
                                         LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 计算平均密度
     */
    Double calculateAverageDensity(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 计算平均值
     */
    Double calculateAverage(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 计算增长率
     */
    Double calculateGrowthRate(String deviceCode, LocalDateTime time);

    /**
     * 计算环比增长率
     */
    Double calculateChainGrowthRate(String deviceCode, LocalDateTime time);

    /**
     * 查找最大值
     */
    Integer findMax(String deviceCode, LocalDateTime startTime, LocalDateTime endTime);
} 