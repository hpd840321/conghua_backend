package com.scenic.ai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 同步记录Mapper接口
 */
@Mapper
public interface SyncRecordMapper {
    
    /**
     * 插入同步记录
     */
    int insert(Map<String, Object> record);
    
    /**
     * 批量插入同步记录
     */
    int batchInsert(List<Map<String, Object>> records);
    
    /**
     * 根据ID查询
     */
    Map<String, Object> selectById(@Param("id") Long id);
    
    /**
     * 查询所有同步记录
     */
    List<Map<String, Object>> selectAll();
    
    /**
     * 根据同步类型查询
     */
    List<Map<String, Object>> selectBySyncType(@Param("syncType") String syncType);
    
    /**
     * 查询指定同步类型的最近同步记录
     */
    Map<String, Object> findFirstBySyncTypeOrderBySyncTimeDesc(@Param("syncType") String syncType);
    
    /**
     * 根据同步状态查询
     */
    List<Map<String, Object>> selectByStatus(@Param("status") Integer status);
    
    /**
     * 根据时间范围查询
     */
    List<Map<String, Object>> findBySyncTimeBetween(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
    
    /**
     * 根据同步类型和状态查询
     */
    List<Map<String, Object>> findBySyncTypeAndStatus(
        @Param("syncType") String syncType,
        @Param("status") String status
    );
    
    /**
     * 更新同步记录
     */
    int update(Map<String, Object> record);
    
    /**
     * 删除同步记录
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 删除所有同步记录
     */
    int deleteAll();
    
    /**
     * 获取最新的同步记录
     */
    Map<String, Object> findLatestByType(@Param("syncType") String syncType);
} 