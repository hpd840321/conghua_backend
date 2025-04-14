package com.scenic.ai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scenic.ai.model.Device;

import java.util.List;

/**
 * 设备服务接口
 */
public interface DeviceService extends IService<Device> {
    
    /**
     * 获取设备列表
     */
    List<Device> list();

    /**
     * 获取设备详情
     */
    Device get(Long id);

    /**
     * 添加设备
     */
    boolean add(Device device);

    /**
     * 更新设备
     */
    boolean update(Device device);

    /**
     * 删除设备
     */
    boolean delete(Long id);

    /**
     * 更新设备状态
     */
    boolean updateStatus(String deviceCode, Integer status);

    /**
     * 更新设备名称
     */
    boolean updateName(String deviceCode, String deviceName);

    /**
     * 更新景区名称
     */
    boolean updateTourismName(String deviceCode, String tourismName);

    /**
     * 批量更新设备状态
     */
    boolean batchUpdateStatus(List<String> deviceCodes, Integer status);

    /**
     * 分页查询设备列表
     */
    IPage<Device> pageDevices(Integer pageNum, Integer pageSize, String tourismName, Integer status);

    /**
     * 根据设备编码查询设备
     */
    Device getDeviceByCode(String deviceCode);

    /**
     * 根据景区名称查询设备列表
     */
    List<Device> getDevicesByTourismName(String tourismName);

    /**
     * 根据状态查询设备列表
     */
    List<Device> getDevicesByStatus(Integer status);

    /**
     * 统计各景区设备数量
     */
    long countByTourism(String tourismName);

    /**
     * 统计设备状态分布
     */
    long countByStatus(Integer status);

    /**
     * 根据状态统计设备数量
     */
    long countDevicesByStatus(String tourismName, Integer status);
} 