package com.scenic.ai.domain.mapper;

import com.scenic.ai.domain.model.Alert;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertMapper {
    
    int insert(Alert alert);
    
    Alert selectById(@Param("id") Long id);
    
    List<Alert> selectByStatus(@Param("status") String status);
    
    List<Alert> selectByLevel(@Param("level") String level);
    
    int countByTimeRange(@Param("startTime") LocalDateTime startTime, 
                        @Param("endTime") LocalDateTime endTime);
                        
    int countByType(@Param("type") String type,
                    @Param("startTime") LocalDateTime startTime,
                    @Param("endTime") LocalDateTime endTime);
                    
    int batchUpdateStatus(@Param("ids") List<Long> ids,
                         @Param("status") String status,
                         @Param("handler") String handler);
                         
    List<Alert> selectByCondition(@Param("type") String type,
                                 @Param("level") String level,
                                 @Param("status") String status,
                                 @Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime);
} 