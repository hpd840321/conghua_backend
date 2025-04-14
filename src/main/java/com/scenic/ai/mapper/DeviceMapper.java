package com.scenic.ai.mapper;

import com.scenic.ai.dto.DeviceDTO;
import com.scenic.ai.dto.DeviceQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备数据访问接口
 */
@Mapper
public interface DeviceMapper {
    
    /**
     * 查询设备总数
     *
     * @param params 查询参数
     * @return 设备总数
     */
    Long countDevices(@Param("params") DeviceQueryParams params);
    
    /**
     * 查询设备列表
     *
     * @param params 查询参数
     * @return 设备列表
     */
    List<DeviceDTO> selectDevices(@Param("params") DeviceQueryParams params);
    
    /**
     * 根据ID查询设备
     *
     * @param id 设备ID
     * @return 设备信息
     */
    DeviceDTO selectById(@Param("id") Long id);
} 