package com.scenic.ai.mapper;

import com.scenic.ai.domain.model.DensityRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 密度记录数据访问接口
 */
@Mapper
public interface DensityMapper {
    
    /**
     * 插入密度记录
     *
     * @param record 密度记录
     * @return 影响行数
     */
    int insert(DensityRecord record);
    
    /**
     * 批量插入密度记录
     *
     * @param records 密度记录列表
     * @return 影响行数
     */
    int batchInsert(@Param("records") List<DensityRecord> records);
    
    /**
     * 根据ID查询密度记录
     *
     * @param id 记录ID
     * @return 密度记录
     */
    DensityRecord selectById(@Param("id") Long id);
    
    /**
     * 查询指定区域的最新密度记录
     *
     * @param areaId 区域ID
     * @return 密度记录
     */
    DensityRecord selectLatestByAreaId(@Param("areaId") String areaId);
    
    /**
     * 查询指定区域和时间范围的密度记录
     *
     * @param areaId    区域ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 密度记录列表
     */
    List<DensityRecord> selectByAreaIdAndTimeRange(
            @Param("areaId") String areaId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
    
    /**
     * 更新密度记录
     *
     * @param record 密度记录
     * @return 影响行数
     */
    int update(DensityRecord record);
    
    /**
     * 删除密度记录
     *
     * @param id 记录ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
} 