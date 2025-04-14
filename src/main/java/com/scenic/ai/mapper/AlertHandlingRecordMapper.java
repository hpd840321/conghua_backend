package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.AlertHandlingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 告警处理记录Mapper接口
 */
@Mapper
public interface AlertHandlingRecordMapper extends BaseMapper<AlertHandlingRecord> {

    /**
     * 根据告警ID查询处理记录列表
     */
    @Select("SELECT * FROM CLOUDWALK.ALERT_HANDLING_RECORD WHERE ALERT_ID = #{alertId} ORDER BY HANDLING_TIME DESC")
    List<AlertHandlingRecord> selectByAlertId(Long alertId);

} 