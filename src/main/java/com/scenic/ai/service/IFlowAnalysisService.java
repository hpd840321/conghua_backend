package com.scenic.ai.service;

import com.scenic.ai.common.core.domain.PageQuery;
import com.scenic.ai.common.core.domain.PageResult;
import com.scenic.ai.model.FlowAnalysis;

import java.util.Date;
import java.util.List;

/**
 * 客流分析服务接口
 * 
 * @author AI
 * @date 2023-05-20
 */
public interface IFlowAnalysisService {
    
    /**
     * 查询客流分析列表
     * 
     * @param flowAnalysis 查询条件
     * @return 客流分析列表
     */
    List<FlowAnalysis> selectFlowAnalysisList(FlowAnalysis flowAnalysis);
    
    /**
     * 查询客流分析详情
     * 
     * @param id 客流分析ID
     * @return 客流分析详情
     */
    FlowAnalysis selectFlowAnalysisById(Long id);
    
    /**
     * 新增客流分析
     * 
     * @param flowAnalysis 客流分析信息
     * @return 结果
     */
    int insertFlowAnalysis(FlowAnalysis flowAnalysis);
    
    /**
     * 修改客流分析
     * 
     * @param flowAnalysis 客流分析信息
     * @return 结果
     */
    int updateFlowAnalysis(FlowAnalysis flowAnalysis);
    
    /**
     * 删除客流分析
     * 
     * @param id 客流分析ID
     * @return 结果
     */
    int deleteFlowAnalysisById(Long id);
    
    /**
     * 批量删除客流分析
     * 
     * @param ids 需要删除的客流分析ID数组
     * @return 结果
     */
    int deleteFlowAnalysisByIds(Long[] ids);
    
    /**
     * 统计指定时间范围内的客流总量
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 客流总量
     */
    Integer selectTotalFlowCount(Date startTime, Date endTime);
    
    /**
     * 统计指定时间范围内的各方向客流数量
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 各方向客流数量统计
     */
    List<FlowAnalysis> selectFlowDirectionStats(Date startTime, Date endTime);
    
    /**
     * 分页查询客流分析数据
     * 
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    PageResult<FlowAnalysis> selectFlowAnalysisPage(PageQuery pageQuery);
} 