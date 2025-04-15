package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.entity.FlowAnalysis;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客流分析数据访问层
 */
@Mapper
public interface FlowAnalysisMapper extends BaseMapper<FlowAnalysis> {
}