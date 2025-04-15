package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.model.DensityAnalysis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 密度分析数据访问接口
 */
@Mapper
public interface DensityAnalysisMapper extends BaseMapper<DensityAnalysis> {

    /**
     * 根据设备编码查询密度分析数据
     *
     * @param deviceCode 设备编码
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 密度分析数据列表
     */
    List<DensityAnalysis> selectByDeviceCode(@Param("deviceCode") String deviceCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 根据景区名称查询密度分析数据
     *
     * @param tourismName 景区名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 密度分析数据列表
     */
    List<DensityAnalysis> selectByTourismName(@Param("tourismName") String tourismName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 获取指定设备的最新密度分析数据
     *
     * @param deviceCode 设备编码
     * @return 最新的密度分析数据
     */
    DensityAnalysis selectLatestByDeviceCode(@Param("deviceCode") String deviceCode);
}