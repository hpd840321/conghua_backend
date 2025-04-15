package com.scenic.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scenic.ai.mapper.DeviceMapper;
import com.scenic.ai.entity.Device;
import com.scenic.ai.service.DeviceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备服务实现类
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Override
    public List<Device> list() {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Device::getCreateTime);
        return list(wrapper);
    }

    @Override
    public Device get(Long id) {
        if (id == null) {
            return null;
        }
        return getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(Device device) {
        if (device == null) {
            return false;
        }
        return save(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Device device) {
        if (device == null || device.getId() == null) {
            return false;
        }
        return updateById(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        if (id == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(String deviceCode, Integer status) {
        if (StringUtils.isEmpty(deviceCode) || status == null) {
            return false;
        }
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceCode, deviceCode);
        Device device = new Device();
        device.setDeviceStatus(status);
        return update(device, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateName(String deviceCode, String deviceName) {
        if (StringUtils.isEmpty(deviceCode) || StringUtils.isEmpty(deviceName)) {
            return false;
        }
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceCode, deviceCode);
        Device device = new Device();
        device.setDeviceName(deviceName);
        return update(device, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTourismName(String deviceCode, String tourismName) {
        if (StringUtils.isEmpty(deviceCode) || StringUtils.isEmpty(tourismName)) {
            return false;
        }
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceCode, deviceCode);
        Device device = new Device();
        device.setTourismName(tourismName);
        return update(device, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateStatus(List<String> deviceCodes, Integer status) {
        if (deviceCodes == null || deviceCodes.isEmpty() || status == null) {
            return false;
        }
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Device::getDeviceCode, deviceCodes);
        Device device = new Device();
        device.setDeviceStatus(status);
        return update(device, wrapper);
    }

    @Override
    public IPage<Device> pageDevices(Integer pageNum, Integer pageSize, String tourismName, Integer status) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(tourismName)) {
            wrapper.eq(Device::getTourismName, tourismName);
        }
        if (status != null) {
            wrapper.eq(Device::getDeviceStatus, status);
        }
        wrapper.orderByDesc(Device::getCreateTime);
        Page<Device> page = new Page<>(pageNum, pageSize);
        return page(page, wrapper);
    }

    @Override
    public Device getDeviceByCode(String deviceCode) {
        if (StringUtils.isEmpty(deviceCode)) {
            return null;
        }
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceCode, deviceCode);
        return getOne(wrapper);
    }

    @Override
    public List<Device> getDevicesByTourismName(String tourismName) {
        if (StringUtils.isEmpty(tourismName)) {
            return null;
        }
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getTourismName, tourismName);
        wrapper.orderByDesc(Device::getCreateTime);
        return list(wrapper);
    }

    @Override
    public List<Device> getDevicesByStatus(Integer status) {
        if (status == null) {
            return null;
        }
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceStatus, status);
        wrapper.orderByDesc(Device::getCreateTime);
        return list(wrapper);
    }

    @Override
    public long countByTourism(String tourismName) {
        if (StringUtils.isEmpty(tourismName)) {
            return 0;
        }
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getTourismName, tourismName);
        return count(wrapper);
    }

    @Override
    public long countByStatus(Integer status) {
        if (status == null) {
            return 0;
        }
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceStatus, status);
        return count(wrapper);
    }

    @Override
    public long countDevicesByStatus(String tourismName, Integer status) {
        if (StringUtils.isEmpty(tourismName) || status == null) {
            return 0;
        }
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getTourismName, tourismName);
        wrapper.eq(Device::getDeviceStatus, status);
        return count(wrapper);
    }
}