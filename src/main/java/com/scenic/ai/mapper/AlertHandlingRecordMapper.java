package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.AlertHandlingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 告警处理记录Mapper接口
 */
@Mapper
public interface AlertHandlingRecordMapper extends BaseMapper<AlertHandlingRecord> {

        /**
         * 插入处理记录
         *
         * @param record 处理记录
         * @return 影响行数
         */
        int insert(AlertHandlingRecord record);

        /**
         * 更新处理记录
         *
         * @param record 处理记录
         * @return 影响行数
         */
        int update(AlertHandlingRecord record);

        /**
         * 根据ID删除处理记录
         *
         * @param id 记录ID
         * @return 影响行数
         */
        int deleteById(@Param("id") Long id);

        /**
         * 根据告警ID查询处理记录列表
         *
         * @param alertId 告警ID
         * @return 处理记录列表
         */
        List<AlertHandlingRecord> listByAlertId(@Param("alertId") Long alertId);

        /**
         * 根据处理人查询处理记录列表
         *
         * @param handler 处理人
         * @return 处理记录列表
         */
        List<AlertHandlingRecord> listByHandler(@Param("handler") String handler);

        /**
         * 统计处理方式分布
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 处理方式分布统计
         */
        List<Map<String, Object>> countByHandlingMethod(@Param("startTime") String startTime,
                        @Param("endTime") String endTime);

        /**
         * 统计处理结果分布
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 处理结果分布统计
         */
        List<Map<String, Object>> countByHandlingResult(@Param("startTime") String startTime,
                        @Param("endTime") String endTime);

        /**
         * 统计处理人处理数量
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 统计结果
         */
        List<Map<String, Object>> countByHandler(
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 分页查询处理记录
         *
         * @param params 查询参数
         * @return 处理记录列表
         */
        List<AlertHandlingRecord> pageByConditions(@Param("params") Map<String, Object> params);

        /**
         * 查询处理记录总数
         *
         * @param params 查询参数
         * @return 记录总数
         */
        Long countRecords(@Param("params") Map<String, Object> params);
}