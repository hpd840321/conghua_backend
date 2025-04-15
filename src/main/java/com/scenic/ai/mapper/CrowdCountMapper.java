package com.scenic.ai.mapper;

import com.scenic.ai.domain.model.CrowdCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群计数数据访问接口
 */
@Mapper
public interface CrowdCountMapper {
        /**
         * 插入记录
         *
         * @param entity 人群计数记录
         * @return 影响行数
         */
        int insert(CrowdCount entity);

        /**
         * 批量插入记录
         *
         * @param list 人群计数记录列表
         * @return 影响行数
         */
        int batchInsert(@Param("list") List<CrowdCount> list);

        /**
         * 根据时间范围查询记录
         *
         * @param deviceCode 设备编码
         * @param startTime  开始时间
         * @param endTime    结束时间
         * @return 人群计数记录列表
         */
        List<CrowdCount> listByTimeRange(@Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取平均人数
         *
         * @param deviceCode 设备编码
         * @param startTime  开始时间
         * @param endTime    结束时间
         * @return 平均人数
         */
        Double getAverageCount(@Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取最大人数
         *
         * @param deviceCode 设备编码
         * @param startTime  开始时间
         * @param endTime    结束时间
         * @return 最大人数
         */
        Integer getMaxCount(@Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 删除历史数据
         *
         * @param time 时间点
         * @return 影响行数
         */
        int deleteHistoricalData(@Param("time") LocalDateTime time);

        /**
         * 获取高密度区域
         *
         * @param deviceCode 设备编码
         * @param threshold  阈值
         * @param startTime  开始时间
         * @param endTime    结束时间
         * @return 高密度区域记录列表
         */
        List<CrowdCount> listHighDensityAreas(@Param("deviceCode") String deviceCode,
                        @Param("threshold") int threshold,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取区域密度
         *
         * @param deviceCode 设备编码
         * @param time       时间点
         * @return 区域密度
         */
        Double getDensity(@Param("deviceCode") String deviceCode,
                        @Param("time") LocalDateTime time);

        /**
         * 获取人群趋势
         *
         * @param deviceCode 设备编码
         * @param startTime  开始时间
         * @param endTime    结束时间
         * @return 人群趋势记录列表
         */
        List<CrowdCount> listTrend(@Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取最新计数
         *
         * @param deviceCode 设备编码
         * @return 最新人群计数记录
         */
        CrowdCount getLatestCount(@Param("deviceCode") String deviceCode);

        /**
         * 获取所有区域最新计数
         *
         * @return 所有区域最新人群计数记录列表
         */
        List<CrowdCount> listLatestCounts();

        /**
         * 获取平均密度
         *
         * @param deviceCode 设备编码
         * @param startTime  开始时间
         * @param endTime    结束时间
         * @return 平均密度
         */
        Double getAverageDensity(@Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 分页查询
         *
         * @param params 查询参数
         * @return 人群计数记录列表
         */
        List<CrowdCount> pageByConditions(@Param("params") Map<String, Object> params);

        /**
         * 获取详情分页
         *
         * @param deviceCode      设备编码
         * @param algName         算法名称
         * @param recordBeginDate 记录开始日期
         * @param recordEndDate   记录结束日期
         * @return 人群计数记录列表
         */
        List<CrowdCount> pageDetails(@Param("deviceCode") String deviceCode,
                        @Param("algName") String algName,
                        @Param("recordBeginDate") LocalDateTime recordBeginDate,
                        @Param("recordEndDate") LocalDateTime recordEndDate);
}