package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.entity.AlertHandlingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 告警处理记录Mapper接口
 */
@Mapper
public interface AlertHandlingRecordMapper extends BaseMapper<AlertHandlingRecord> {

    /**
     * 根据告警ID查询处理记录
     */
    List<AlertHandlingRecord> selectByAlertId(@Param("alertId") Long alertId);

    /**
     * 根据处理人查询处理记录
     */
    List<AlertHandlingRecord> selectByHandler(@Param("handler") String handler);

    /**
     * 统计处理方式数量
     */
    List<Object[]> countHandleMethods(@Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 统计处理结果数量
     */
    List<Object[]> countHandleResults(@Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 分页查询处理记录
     */
    List<AlertHandlingRecord> selectPage(@Param("offset") int offset,
            @Param("limit") int limit);
}