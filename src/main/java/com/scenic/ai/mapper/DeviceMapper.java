package com.scenic.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scenic.ai.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备Mapper接口
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    /**
     * 根据设备编码查询设备
     */
    Device selectByDeviceCode(@Param("deviceCode") String deviceCode);

    /**
     * 根据景区名称查询设备列表
     */
    List<Device> selectByTourismName(@Param("tourismName") String tourismName);

    /**
     * 根据状态查询设备列表
     */
    List<Device> selectByStatus(@Param("status") Integer status);

    /**
     * 统计景区设备数量
     */
    long countByTourism(@Param("tourismName") String tourismName);

    /**
     * 统计状态设备数量
     */
    long countByStatus(@Param("status") Integer status);

    /**
     * 统计景区状态设备数量
     */
    long countByTourismAndStatus(@Param("tourismName") String tourismName, @Param("status") Integer status);
}