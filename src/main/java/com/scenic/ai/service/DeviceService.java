package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.Device;

import java.util.List;
import java.util.Map;

/**
 * 设备服务接口
 */
public interface DeviceService extends IService<Device> {
    
    /**
     * 根据设备编码查询设备
     * @param deviceCode 设备编码
     * @return 设备信息
     */
    Device getDeviceByCode(String deviceCode);

    /**
     * 根据景区名称查询设备列表
     * @param tourismName 景区名称
     * @return 设备列表
     */
    List<Device> getDevicesByTourismName(String tourismName);

    /**
     * 根据状态查询设备列表
     * @param status 设备状态
     * @return 设备列表
     */
    List<Device> getDevicesByStatus(String status);

    /**
     * 统计各景区设备数量
     * @return 各景区设备数量
     */
    List<Map<String, Object>> countByTourism();

    /**
     * 统计设备状态分布
     * @return 设备状态分布
     */
    List<Map<String, Object>> countByStatus();

    /**
     * 分页查询设备列表
     * @param page 分页参数
     * @param status 设备状态
     * @return 分页结果
     */
    IPage<Device> pageDevices(IPage<Device> page, String status);

    /**
     * 批量更新设备状态
     * @param deviceCodes 设备编码数组
     * @param status 新状态
     * @return 是否更新成功
     */
    boolean batchUpdateStatus(String[] deviceCodes, String status);

    /**
     * 更新设备名称
     * @param deviceCode 设备编码
     * @param deviceName 新设备名称
     * @return 是否更新成功
     */
    boolean updateDeviceName(String deviceCode, String deviceName);

    /**
     * 更新景区名称
     * @param deviceCode 设备编码
     * @param tourismName 新景区名称
     * @return 是否更新成功
     */
    boolean updateTourismName(String deviceCode, String tourismName);

    /**
     * 根据状态统计设备数量
     * @param status 设备状态
     * @return 设备数量
     */
    Long countDevicesByStatus(String status);

    /**
     * 更新设备状态
     * @param deviceId 设备ID
     * @param status 新状态
     * @return 是否更新成功
     */
    boolean updateDeviceStatus(Long deviceId, String status);
} 