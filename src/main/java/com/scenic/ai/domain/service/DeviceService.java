package com.scenic.ai.domain.service;

import com.scenic.ai.domain.model.Device;

import java.util.List;

public interface DeviceService {
    
    /**
     * 创建设备
     */
    void createDevice(Device device);
    
    /**
     * 更新设备
     */
    void updateDevice(Device device);
    
    /**
     * 删除设备
     */
    void deleteDevice(Long id);
    
    /**
     * 获取设备详情
     */
    Device getDeviceById(Long id);
    
    /**
     * 根据编码获取设备
     */
    Device getDeviceByCode(String code);
    
    /**
     * 获取区域内的设备列表
     */
    List<Device> getDevicesByArea(String areaId);
    
    /**
     * 获取在线设备列表
     */
    List<Device> getOnlineDevices();
    
    /**
     * 更新设备状态
     */
    void updateDeviceStatus(Long id, String status);
    
    /**
     * 批量更新设备状态
     */
    void batchUpdateDeviceStatus(List<Long> ids, String status);
    
    /**
     * 条件查询设备
     */
    List<Device> searchDevices(String type, String status, String areaId);
    
    /**
     * 设备心跳更新
     */
    void updateDeviceHeartbeat(String code);
} 