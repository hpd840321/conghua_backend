package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.DeviceMapper;
import com.scenic.ai.model.Device;
import com.scenic.ai.service.DeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 设备服务实现类
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    @Override
    public Device getDeviceByCode(String deviceCode) {
        if (!StringUtils.hasText(deviceCode)) {
            return null;
        }
        return lambdaQuery().eq(Device::getDeviceCode, deviceCode).one();
    }

    @Override
    public List<Device> getDevicesByTourismName(String tourismName) {
        if (!StringUtils.hasText(tourismName)) {
            return null;
        }
        return lambdaQuery().eq(Device::getTourismName, tourismName).list();
    }

    @Override
    public List<Device> getDevicesByStatus(String status) {
        if (!StringUtils.hasText(status)) {
            return null;
        }
        return lambdaQuery().eq(Device::getStatus, status).list();
    }

    @Override
    public List<Map<String, Object>> countByTourism() {
        return baseMapper.countByTourism();
    }

    @Override
    public List<Map<String, Object>> countByStatus() {
        return baseMapper.countByStatus();
    }

    @Override
    public IPage<Device> pageDevices(IPage<Device> page, String status) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(status)) {
            wrapper.eq(Device::getStatus, status);
        }
        return page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateStatus(String[] deviceCodes, String status) {
        if (deviceCodes == null || deviceCodes.length == 0 || !StringUtils.hasText(status)) {
            return false;
        }
        return lambdaUpdate()
                .in(Device::getDeviceCode, Arrays.asList(deviceCodes))
                .set(Device::getStatus, status)
                .update();
    }

    @Override
    public boolean updateDeviceName(String deviceCode, String deviceName) {
        if (!StringUtils.hasText(deviceCode) || !StringUtils.hasText(deviceName)) {
            return false;
        }
        return lambdaUpdate()
                .eq(Device::getDeviceCode, deviceCode)
                .set(Device::getDeviceName, deviceName)
                .update();
    }

    @Override
    public boolean updateTourismName(String deviceCode, String tourismName) {
        if (!StringUtils.hasText(deviceCode) || !StringUtils.hasText(tourismName)) {
            return false;
        }
        return lambdaUpdate()
                .eq(Device::getDeviceCode, deviceCode)
                .set(Device::getTourismName, tourismName)
                .update();
    }

    @Override
    public Long countDevicesByStatus(String status) {
        if (!StringUtils.hasText(status)) {
            return 0L;
        }
        return lambdaQuery()
                .eq(Device::getStatus, status)
                .count();
    }

    @Override
    public boolean updateDeviceStatus(Long deviceId, String status) {
        if (deviceId == null || !StringUtils.hasText(status)) {
            return false;
        }
        return lambdaUpdate()
                .eq(Device::getId, deviceId)
                .set(Device::getStatus, status)
                .update();
    }
}