package com.scenic.ai.mapper;

import com.scenic.ai.domain.model.AlertHandleRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 告警处理记录Mapper接口
 */
@Mapper
public interface AlertHandleRecordMapper {

    /**
     * 根据告警ID查询处理记录
     *
     * @param alertId 告警ID
     * @return 处理记录列表
     */
    List<AlertHandleRecord> listByAlertId(@Param("alertId") Long alertId);

    /**
     * 根据处理人查询处理记录
     *
     * @param handler 处理人
     * @return 处理记录列表
     */
    List<AlertHandleRecord> listByHandler(@Param("handler") String handler);

    /**
     * 统计处理方式分布
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 处理方式统计结果
     */
    List<Map<String, Object>> countByHandleMethod(@Param("startTime") String startTime,
            @Param("endTime") String endTime);

    /**
     * 统计处理结果分布
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 处理结果统计结果
     */
    List<Map<String, Object>> countByHandleResult(@Param("startTime") String startTime,
            @Param("endTime") String endTime);

    /**
     * 分页查询处理记录
     *
     * @param params 查询参数
     * @return 处理记录列表
     */
    List<AlertHandleRecord> pageByConditions(@Param("params") Map<String, Object> params);

    /**
     * 新增处理记录
     *
     * @param record 处理记录
     * @return 影响行数
     */
    int insert(AlertHandleRecord record);

    /**
     * 更新处理记录
     *
     * @param record 处理记录
     * @return 影响行数
     */
    int update(AlertHandleRecord record);

    /**
     * 删除处理记录
     *
     * @param id 记录ID
     * @return 影响行数
     */
    int delete(@Param("id") Long id);
}