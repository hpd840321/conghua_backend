package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scenic.ai.model.CrowdStatistics;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 人群统计服务接口
 * 
 * @author AI
 * @date 2024-04-15
 */
public interface ICrowdStatisticsService {

        /**
         * 保存人群统计记录
         *
         * @param statistics 统计记录
         * @return 是否保存成功
         */
        boolean save(CrowdStatistics statistics);

        /**
         * 根据ID查询统计记录
         *
         * @param id 记录ID
         * @return 统计记录
         */
        CrowdStatistics getById(Long id);

        /**
         * 根据设备编号查询统计记录
         *
         * @param deviceCode 设备编号
         * @return 统计记录列表
         */
        List<CrowdStatistics> listByDevice(String deviceCode);

        /**
         * 根据景区名称查询统计记录
         *
         * @param tourismName 景区名称
         * @return 统计记录列表
         */
        List<CrowdStatistics> listByTourism(String tourismName);

        /**
         * 根据时间范围查询统计记录
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 统计记录列表
         */
        List<CrowdStatistics> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

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
                        String deviceCode,
                        String tourismName,
                        LocalDateTime startTime,
                        LocalDateTime endTime);

        /**
         * 统计指定时间范围内的总人数
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 总人数
         */
        Integer countTotalCrowd(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 统计指定时间范围内的平均密度
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 平均密度
         */
        Double getAverageDensity(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取指定时间范围内的最大人数
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 最大人数
         */
        Integer getMaxCrowdCount(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取指定时间范围内的最小人数
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 最小人数
         */
        Integer getMinCrowdCount(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取各景区人数分布
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 景区人数分布列表
         */
        List<Map<String, Object>> getTourismDistribution(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取各设备人数分布
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @return 设备人数分布列表
         */
        List<Map<String, Object>> getDeviceDistribution(LocalDateTime startTime, LocalDateTime endTime);

        /**
         * 获取时间趋势数据
         *
         * @param startTime 开始时间
         * @param endTime   结束时间
         * @param interval  时间间隔（分钟）
         * @return 时间趋势数据列表
         */
        List<Map<String, Object>> getTimeTrend(LocalDateTime startTime, LocalDateTime endTime, Integer interval);
}