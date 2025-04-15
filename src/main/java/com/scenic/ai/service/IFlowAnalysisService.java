package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.common.core.domain.PageQuery;
import com.scenic.ai.common.core.domain.PageResult;
import com.scenic.ai.entity.FlowAnalysis;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客流分析服务接口
 */
public interface IFlowAnalysisService extends IService<FlowAnalysis> {

    /**
     * 查询客流分析列表
     *
     * @param flowAnalysis 查询条件
     * @return 客流分析列表
     */
    List<FlowAnalysis> selectFlowAnalysisList(FlowAnalysis flowAnalysis);

    /**
     * 根据ID查询客流分析
     *
     * @param id 客流分析ID
     * @return 客流分析
     */
    FlowAnalysis selectFlowAnalysisById(Long id);

    /**
     * 新增客流分析
     *
     * @param flowAnalysis 客流分析
     * @return 影响行数
     */
    int insertFlowAnalysis(FlowAnalysis flowAnalysis);

    /**
     * 修改客流分析
     *
     * @param flowAnalysis 客流分析
     * @return 影响行数
     */
    int updateFlowAnalysis(FlowAnalysis flowAnalysis);

    /**
     * 删除客流分析
     *
     * @param id 客流分析ID
     * @return 影响行数
     */
    int deleteFlowAnalysisById(Long id);

    /**
     * 批量删除客流分析
     *
     * @param ids 客流分析ID数组
     * @return 影响行数
     */
    int deleteFlowAnalysisByIds(Long[] ids);

    /**
     * 统计总流量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 总流量
     */
    Integer selectTotalFlowCount(Date startTime, Date endTime);

    /**
     * 统计流量方向分布
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 流量方向分布
     */
    List<FlowAnalysis> selectFlowDirectionStats(Date startTime, Date endTime);

    /**
     * 分页查询客流分析
     *
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    PageResult<FlowAnalysis> selectFlowAnalysisPage(PageQuery pageQuery);

    /**
     * 根据设备编码查询客流分析列表
     *
     * @param deviceCode 设备编码
     * @return 客流分析列表
     */
    List<FlowAnalysis> listByDevice(String deviceCode);

    /**
     * 根据景区名称查询客流分析列表
     *
     * @param tourismName 景区名称
     * @return 客流分析列表
     */
    List<FlowAnalysis> listByTourism(String tourismName);

    /**
     * 根据时间范围查询客流分析列表
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 客流分析列表
     */
    List<FlowAnalysis> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 分页查询客流分析
     *
     * @param page        分页参数
     * @param deviceCode  设备编码
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 分页结果
     */
    IPage<FlowAnalysis> page(Page<FlowAnalysis> page, String deviceCode, String tourismName,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取最大流量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 最大流量
     */
    Integer getMaxFlowCount(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取最小流量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 最小流量
     */
    Integer getMinFlowCount(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取景区分布
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 景区分布
     */
    List<Map<String, Object>> getTourismDistribution(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取设备分布
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 设备分布
     */
    List<Map<String, Object>> getDeviceDistribution(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取方向分布
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 方向分布
     */
    List<Map<String, Object>> getDirectionDistribution(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取时间趋势
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param interval  时间间隔（分钟）
     * @return 时间趋势
     */
    List<Map<String, Object>> getTimeTrend(LocalDateTime startTime, LocalDateTime endTime, Integer interval);

    /**
     * 获取概览数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 概览数据
     */
    Map<String, Object> getOverview(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据条件查询客流分析列表
     *
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param algName     算法名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 客流分析列表
     */
    List<FlowAnalysis> listByConditions(String tourismName, String deviceCode, String algName,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 分页查询客流分析
     *
     * @param page        分页参数
     * @param deviceCode  设备编码
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 分页结果
     */
    IPage<FlowAnalysis> pageByConditions(Page<FlowAnalysis> page,
            String deviceCode,
            String tourismName,
            LocalDateTime startTime,
            LocalDateTime endTime);

    /**
     * 统计总流量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 总流量
     */
    Integer countTotalFlow(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计时间范围内的记录数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 记录数
     */
    Integer countByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取方向分布
     *
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 方向分布
     */
    List<Map<String, Object>> getDirectionDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取时间分布
     *
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 时间分布
     */
    List<Map<String, Object>> getTimeDistribution(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取设备最新记录
     *
     * @param deviceCode 设备编码
     * @return 最新记录
     */
    FlowAnalysis getLatestByDevice(String deviceCode);

    /**
     * 获取概览数据
     *
     * @param tourismName 景区名称
     * @param deviceCode  设备编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 概览数据
     */
    Map<String, Object> getOverview(String tourismName, String deviceCode,
            LocalDateTime startTime, LocalDateTime endTime);
}