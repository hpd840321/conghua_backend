package com.scenic.ai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.AlertHandlingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 告警处理记录数据访问层
 * 
 * @author scenic
 */
@Mapper
public interface AlertHandlingRecordMapper extends BaseMapper<AlertHandlingRecord> {
    
    /**
     * 根据告警ID查询处理记录
     */
    List<AlertHandlingRecord> selectByAlertId(@Param("alertId") Long alertId);
} 