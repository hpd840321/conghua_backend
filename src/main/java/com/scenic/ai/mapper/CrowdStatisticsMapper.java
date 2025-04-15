package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.CrowdStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人群统计Mapper接口
 * 提供人群密度统计、分布分析、趋势分析等功能的数据访问
 *
 * @author scenic
 * @date 2024-03-19
 */
@Mapper
public interface CrowdStatisticsMapper extends BaseMapper<CrowdStatistics> {

        /**
         * 根据ID查询人群统计数据
         *
         * @param id 记录ID
         * @return 人群统计数据
         */
        CrowdStatistics selectById(@Param("id") Long id);

        /**
         * 分页查询人群统计数据
         *
         * @param offset      偏移量
         * @param limit       限制数量
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 分页结果
         */
        List<CrowdStatistics> selectPage(@Param("offset") Integer offset,
                        @Param("limit") Integer limit,
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 统计符合条件的记录数量
         *
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 记录数量
         */
        Integer selectCount(@Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询密度分布数据
         *
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 密度分布数据列表
         */
        List<Map<String, Object>> selectDensityDistribution(@Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询人数分布数据
         *
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 人数分布数据列表
         */
        List<Map<String, Object>> selectCountDistribution(@Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询时间分布数据
         *
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 时间分布数据列表
         */
        List<Map<String, Object>> selectTimeDistribution(@Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询统计概览数据
         *
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 统计概览数据
         */
        Map<String, Object> selectOverview(@Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询高密度区域数据
         *
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @param limit       限制数量
         * @return 高密度区域数据列表
         */
        List<Map<String, Object>> selectHighDensityAreas(@Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime,
                        @Param("limit") Integer limit);

        /**
         * 查询设备最新数据
         *
         * @param deviceCode 设备编码
         * @return 最新人群统计数据
         */
        CrowdStatistics selectLatestByDevice(@Param("deviceCode") String deviceCode);

        /**
         * 查询密度统计数据
         *
         * @param tourismName 景区名称
         * @param deviceCode  设备编码
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 密度统计数据
         */
        Map<String, Object> selectDensityStats(@Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 分页查询
         */
        IPage<CrowdStatistics> pageByConditions(Page<CrowdStatistics> page,
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

        /**
         * 根据设备编码查询
         */
        List<CrowdStatistics> listByDevice(@Param("deviceCode") String deviceCode,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

        /**
         * 根据景区名称查询
         */
        List<CrowdStatistics> listByTourism(@Param("tourismName") String tourismName,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

        /**
         * 统计时间范围内的平均密度和人数
         */
        Map<String, Object> calculateStatistics(@Param("deviceCode") String deviceCode,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

        /**
         * 统计各景区人群密度分布
         */
        List<Map<String, Object>> countByDensityRange(@Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

        /**
         * 获取高密度时段分布
         */
        List<Map<String, Object>> getHighDensityHours(@Param("threshold") BigDecimal threshold,
                        @Param("deviceCode") String deviceCode,
                        @Param("tourismName") String tourismName,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

        /**
         * 获取高密度统计数据
         */
        List<Map<String, Object>> getHighDensityStats(@Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("densityThreshold") BigDecimal densityThreshold,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

        /**
         * 根据时间范围和景区名称查询人群统计数据
         */
        List<CrowdStatistics> listByTimeRangeAndTourism(@Param("startTime") Date startTime,
                        @Param("endTime") Date endTime,
                        @Param("tourismName") String tourismName);

        /**
         * 获取时段人群分布
         */
        List<Map<String, Object>> getHourDistribution(@Param("deviceCode") String deviceCode,
                        @Param("tourismName") String tourismName,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime);

        /**
         * 查询高密度区域
         */
        List<Map<String, Object>> getHighDensity(@Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("densityThreshold") BigDecimal densityThreshold,
                        @Param("startTime") Date startTime,
                        @Param("endTime") Date endTime,
                        @Param("limit") Integer limit);

        /**
         * 查询平均密度
         */
        Double selectAvgDensity(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询最大密度
         */
        Double selectMaxDensity(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 查询最小密度
         */
        Double selectMinDensity(
                        @Param("tourismName") String tourismName,
                        @Param("deviceCode") String deviceCode,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 根据ID查询统计记录
         *
         * @param id 记录ID
         * @return 统计记录
         */
        CrowdStatistics getById(@Param("id") Long id);

        /**
         * 根据设备编号查询统计记录
         *
         * @param deviceCode 设备编号
         * @return 统计记录列表
         */
        List<CrowdStatistics> listByDevice(@Param("deviceCode") String deviceCode);

        /**
         * 根据景区名称查询统计记录
         *
         * @param tourismName 景区名称
         * @return 统计记录列表
         */
        List<CrowdStatistics> listByTourism(@Param("tourismName") String tourismName);

        /**
         * 根据时间范围查询统计记录
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 统计记录列表
         */
        List<CrowdStatistics> listByTimeRange(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 分页查询统计记录
         *
         * @param page        分页参数
         * @param deviceCode  设备编号
         * @param tourismName 景区名称
         * @param startTime   开始时间
         * @param endTime     结束时间
         * @return 分页结果
         */
        IPage<CrowdStatistics> pageByConditions(Page<CrowdStatistics> page,
                        @Param("deviceCode") String deviceCode,
                        @Param("tourismName") String tourismName,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 统计指定时间范围内的总人数
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 总人数
         */
        Integer countTotalCrowd(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 统计指定时间范围内的平均密度
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 平均密度
         */
        Double getAverageDensity(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取指定时间范围内的最大人数
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 最大人数
         */
        Integer getMaxCrowdCount(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取指定时间范围内的最小人数
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 最小人数
         */
        Integer getMinCrowdCount(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取各景区人数分布
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 景区人数分布列表
         */
        List<Map<String, Object>> getTourismDistribution(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取各设备人数分布
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 设备人数分布列表
         */
        List<Map<String, Object>> getDeviceDistribution(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime);

        /**
         * 获取时间趋势数据
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @param interval  时间间隔（分钟）
         * @return 时间趋势数据列表
         */
        List<Map<String, Object>> getTimeTrend(@Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime,
                        @Param("interval") Integer interval);
}