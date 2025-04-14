package com.scenic.ai.domain.mapper;

import com.scenic.ai.domain.model.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMapper {
    
    /**
     * 插入设备
     */
    int insert(Device device);
    
    /**
     * 更新设备
     */
    int update(Device device);
    
    /**
     * 删除设备
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID查询
     */
    Device selectById(@Param("id") Long id);
    
    /**
     * 根据编码查询
     */
    Device selectByCode(@Param("code") String code);
    
    /**
     * 查询区域内的设备
     */
    List<Device> selectByAreaId(@Param("areaId") String areaId);
    
    /**
     * 查询在线设备
     */
    List<Device> selectOnlineDevices();
    
    /**
     * 更新设备状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    
    /**
     * 批量更新设备状态
     */
    int batchUpdateStatus(@Param("ids") List<Long> ids, @Param("status") String status);
    
    /**
     * 条件查询
     */
    List<Device> selectByCondition(@Param("type") String type,
                                 @Param("status") String status,
                                 @Param("areaId") String areaId);
} 