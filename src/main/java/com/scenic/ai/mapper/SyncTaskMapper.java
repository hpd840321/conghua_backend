package com.scenic.ai.mapper;

import com.scenic.ai.domain.model.sync.SyncTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 同步任务数据访问接口
 */
@Mapper
public interface SyncTaskMapper {
    
    /**
     * 根据ID查询任务
     */
    SyncTask findById(@Param("id") Long id);
    
    /**
     * 条件查询任务列表
     */
    List<SyncTask> findByCondition(@Param("taskType") String taskType,
                                  @Param("status") Integer status,
                                  @Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);
    
    /**
     * 插入任务
     */
    int insert(SyncTask task);
    
    /**
     * 更新任务状态
     */
    int updateStatus(@Param("id") Long id,
                    @Param("status") Integer status,
                    @Param("errorMessage") String errorMessage,
                    @Param("updateTime") LocalDateTime updateTime);
} 